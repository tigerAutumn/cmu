package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import cc.newex.wallet.wallet.WalletContext;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static cc.newex.wallet.utils.Constants.WALLET_DEPOSIT_KEY;

/**
 * @author newex-team
 * @data 31/03/2018
 */
@Slf4j
@Component
public class TransactionService {
    @Autowired
    UtxoTransactionService utxoService;

    @Autowired
    AccountTransactionService accountTransactionService;

    @Autowired
    WithdrawRecordService withdrawRecordService;

    @Autowired
    WalletContext walletContext;

    @Autowired
    WithdrawTransactionService transactionService;

    @Autowired
    OmniTransactionService omniTransactionService;

    //充值，把充值交易推送到各自的业务线队列
    public void saveTransaction(final List<TransactionDTO> dtos) {
        log.info("saveTransactions dto begin");
        dtos.parallelStream().forEach((tx) -> this.saveTransaction(tx));
        log.info("saveTransactions dto end");
    }

    public void saveTransaction(final TransactionDTO dto) {
        log.info("saveTransaction dto: {} begin", dto.getTxId());

        // INTERNAL 一般代表是内部找零地址的默认业务线，不需要通知到账
        if (BizEnum.INTERNAL.getIndex() == dto.getBiz().intValue()) {
            return;
        }

        // String depositKey = WALLET_DEPOSIT_KEY + dto.getBiz();
        final String depositKey = WALLET_DEPOSIT_KEY;
        final String val = JSONObject.toJSONString(dto);
        REDIS.rPush(depositKey, val);
        log.info("saveTransaction dto: {} end", dto.getTxId());

    }

    /**
     * 把提现记录入库，账户类型的币直接发出提现请求，但是utxo类型的币在 {@link cc.newex.wallet.jobs.withdraw}中批量汇出
     *
     * @param record
     */
    @Transactional(rollbackFor = {Throwable.class}, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        log.info("withdraw id:{} begin", record.getWithdrawId());

        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        //在交易未构建成功之前，用withdrawId占位
        record.setTxId(record.getWithdrawId());
        record.setStatus((byte) Constants.WAITING);
//        /**
//         * luckyWin 地址特殊处理
//         */
//        if (currency == CurrencyEnum.LUCKYWIN) {
//            String newLuckyAddress = record.getAddress().replace(LUCKWIN_ADDRESS_PREFIX, ETH_ADDRESS_PREFIX);
//            record.setAddress(newLuckyAddress);
//        }

        this.withdrawRecordService.add(record, table);

        final WithdrawRecordExample example = new WithdrawRecordExample();
        example.createCriteria().andWithdrawIdEqualTo(record.getWithdrawId());

        final List<WithdrawRecord> exist = this.withdrawRecordService.getByExample(example, table);
        if (exist.size() > 1) {
            //record.setStatus((byte) Constants.CONFIRM);
            this.withdrawRecordService.removeById(record.getId(), table);
            return true;
            //throw new DataExistError("WithId:" + record.getWithdrawId() + " has exist");
        }

        final IWallet wallet = this.walletContext.getWallet(currency);
        final boolean success = wallet.withdraw(record);
        if (!success) {
            log.error("currency: {}, withdraw failure: {}", currency.getName(), record.getWithdrawId());

            this.withdrawRecordService.removeById(record.getId(), table);
        }

        log.info("currency: {}, withdraw id:{} end", currency.getName(), record.getWithdrawId());

        return success;
    }

    /**
     * 把已经签好的交易发送出去
     * 更新withdrawrecord表和utxo表中的txid
     * 把数据推送到 {@Link Constants.WALLET_WITHDRAW_TX_BIZ_KEY}各个业务线的队列，回写txid
     *
     * @param transaction 已经签好的交易
     */
    public void sendWithdrawTransaction(final WithdrawTransaction transaction) {
        log.info("send Withdraw Transaction currency={}, txid:{}", transaction.getCurrency(), transaction.getId());
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());

        //签名是否成功
        if (!signature.containsKey("valid") || !signature.getBoolean("valid")) {
            log.error("WithdrawTransaction valid error currency={}, id:{}", transaction.getCurrency(), transaction.getId());
            return;
        }

        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final IWallet wallet = this.walletContext.getWallet(currency);

        final String txId = wallet.sendRawTransaction(transaction);

        if (!StringUtils.hasText(txId)) {
            log.error("{} send tx fail, withdraw transaction:{}", currency.getName(), transaction);
            return;
        }

        // String txId = wallet.getTxId(transaction);
        transaction.setTxId(txId);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        //2 代表交易已发送
        final short status = Constants.SENT;
        transaction.setStatus(status);
        transaction.setUpdateDate(Date.from(Instant.now()));
        this.transactionService.editById(transaction, table);

        //更新withdrawrecord表中的txid
        final WithdrawRecordExample example = new WithdrawRecordExample();
        example.createCriteria().andTxIdEqualTo(transaction.getId().toString());
        final List<WithdrawRecord> records = this.withdrawRecordService.getByExample(example, table);
        records.parallelStream().forEach((record) -> {
            record.setTxId(txId);
            record.setStatus((byte) status);
            record.setUpdateDate(Date.from(Instant.now()));
            this.withdrawRecordService.editById(record, table);
            // String key = Constants.WALLET_WITHDRAW_TX_BIZ_KEY + record.getBiz();
            final String key = Constants.WALLET_WITHDRAW_TX_BIZ_KEY;
            final String val = JSONObject.toJSONString(record);
            REDIS.lPush(key, val);
        });
        log.info("send Withdraw Transaction success,currency:{} txid:{} end", currency.getName(), transaction.getTxId());
    }

}
