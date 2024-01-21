package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.currency.CurrencyOnlineEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.UtxoTransactionService;
import cc.newex.wallet.service.WithdrawRecordService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import cc.newex.wallet.wallet.WalletContext;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cc.newex.wallet.currency.CurrencyEnum.BTM;
import static cc.newex.wallet.currency.CurrencyEnum.DASH;
import static cc.newex.wallet.currency.CurrencyEnum.DOGE;
import static cc.newex.wallet.currency.CurrencyEnum.GAS;
import static cc.newex.wallet.currency.CurrencyEnum.IOTA;
import static cc.newex.wallet.currency.CurrencyEnum.LGA;
import static cc.newex.wallet.currency.CurrencyEnum.NEO;
import static cc.newex.wallet.currency.CurrencyEnum.ZEC;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Slf4j
abstract public class AbstractBatchWithdrawJob {
    //一次处理10笔交易
    public final int COUNT = 10;
    public CurrencyEnum currency;
    @Autowired
    WithdrawRecordService recordService;
    @Autowired
    UtxoTransactionService utxoService;
    @Autowired
    AddressService addressService;
    @Autowired
    WalletContext walletContext;
    @Autowired
    WithdrawTransactionService transactionService;

    private static final Set<CurrencyEnum> SINGLE_SIG_CURRENCY = Sets.immutableEnumSet(IOTA, NEO, GAS, BTM, DOGE, DASH, ZEC, LGA);

    public void execute(final ShardingContext shardingContext) {
        if (currency.getOnline() == CurrencyOnlineEnum.OFFLINE.getCode()) {
            log.info("offline currency has no withdraw: {}", currency.getName());

            return;
        }

        log.info("Batch {} WithdrawJob begin", this.currency.getName());

        try {
            final ShardTable table = ShardTable.builder().prefix(this.currency.getName()).build();
            final WithdrawRecordExample example = new WithdrawRecordExample();
            example.createCriteria().andStatusEqualTo((byte) Constants.WAITING);
            final PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(this.COUNT);
            pageInfo.setStartIndex(0);
            pageInfo.setSortItem("id");
            pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);

            while (true) {
                final List<WithdrawRecord> records = this.recordService.getByPage(pageInfo, example, table);
                if (CollectionUtils.isEmpty(records)) {
                    break;
                }
                final WithdrawTransaction transaction = this.buildTransaction(records);
                if (transaction == null) {
                    log.error("Batch {} WithdrawJob error, buildTransaction failed", this.currency.getName());
                    break;
                }
                //把交易推送到待签名队列
                final String val = JSONObject.toJSONString(transaction);

                if (SINGLE_SIG_CURRENCY.contains(this.currency)) {
                    REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
                } else {
                    REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_FIRST_KEY, val);
                }
                log.info("buildTransaction success, id:{}", transaction.getId());

                //说明数据库中没有等待签名的交易了
                if (records.size() < this.COUNT) {
                    break;
                }
            }
        } catch (final Throwable e) {
            log.error("Batch {} WithdrawJob error", this.currency.getName(), e);
        }

        log.info("Batch {} WithdrawJob end", this.currency.getName());

    }

    protected WithdrawTransaction buildTransaction(final List<WithdrawRecord> records) {
        log.info("buildTransaction begin");
        final int size = 1;
        WithdrawTransaction transaction = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (final WithdrawRecord record : records) {
            totalAmount = totalAmount.add(record.getBalance()).add(record.getFee());
        }

        final ShardTable table = ShardTable.builder().prefix(this.currency.getName()).build();

        final UtxoTransactionExample example = new UtxoTransactionExample();
        example.createCriteria().andStatusEqualTo((byte) Constants.WAITING).andConfirmNumGreaterThanOrEqualTo(this.currency.getDepositConfirmNum());

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(size);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);

        //选取utxo
        LinkedList<UtxoTransaction> utxos = new LinkedList<>();

        BigDecimal walletAmount = BigDecimal.ZERO;
        while (true) {
            final List<UtxoTransaction> tmps = this.utxoService.getByPage(pageInfo, example, table);
            if (CollectionUtils.isEmpty(tmps)) {
                log.error("buildTransaction failed, wallet balance is not enough, currency: {}, status: {}, depositConfirmNum: {}",
                        this.currency.getName(), Constants.WAITING, this.currency.getDepositConfirmNum());

                return null;
            }

            utxos.addAll(tmps);
            //因为page size 为 1，所以查询结果中只有一条数据
            final UtxoTransaction utxo = tmps.get(0);
            walletAmount = walletAmount.add(utxo.getBalance());
            if (walletAmount.compareTo(totalAmount) > 0) {
                break;
            }
            pageInfo.setStartIndex(pageInfo.getStartIndex() + size);
        }

        //反向过滤一遍，或许后续加入的utxo金额较大，能减少使用的utxo数量
        final Iterator<UtxoTransaction> descendingIterator = utxos.descendingIterator();
        final List<Address> addresses = new LinkedList<>();
        utxos = new LinkedList<>();
        walletAmount = BigDecimal.ZERO;

        while (descendingIterator.hasNext()) {
            final UtxoTransaction utxo = descendingIterator.next();
            utxos.add(utxo);
            walletAmount = walletAmount.add(utxo.getBalance());
            final Address address = this.addressService.getAddress(utxo.getAddress(), table);
            addresses.add(address);
            if (walletAmount.compareTo(totalAmount) > 0) {
                break;
            }
        }

        //初始化交易
        final int feePerKb = REDIS.getInt(Constants.WALLET_FEE + this.currency.getIndex());
        final JSONObject signature = new JSONObject();
        final IWallet wallet = this.walletContext.getWallet(this.currency);
        final Address changeAddress = wallet.genNewAddress(Constants.USER_ID, Constants.BIZ);
        signature.put("utxos", utxos);
        signature.put("addresses", addresses);
        signature.put("withdraw", records);
        signature.put("changeAddress", changeAddress.getAddress());
        signature.put("feePerKb", feePerKb);
        signature.put("totalAmount", totalAmount.toPlainString());

        transaction = WithdrawTransaction.builder()
                .balance(walletAmount)
                .currency(this.currency.getIndex())
                .status(Constants.SIGNING)
                .txId("singing")
                .signature(signature.toJSONString())
                .build();
        this.transactionService.add(transaction, table);

        final String transactionId = transaction.getId().toString();

        //更新utxo和WithdrawRecord的status
        final List<UtxoTransaction> spents = utxos.parallelStream().map((utxo) -> {
            UtxoTransaction tmp = UtxoTransaction.builder()
                    .id(utxo.getId())
                    .spentTxId(transactionId)
                    .spent((byte) 1)
                    .status((byte) Constants.SIGNING)
                    .updateDate(Date.from(Instant.now()))
                    .build();
            return tmp;
        }).collect(Collectors.toList());
        this.utxoService.batchEdit(spents, table);

        records.parallelStream().forEach((record) -> {
            record.setStatus((byte) Constants.SIGNING);
            record.setTxId(transactionId);
            record.setUpdateDate(Date.from(Instant.now()));
        });
        this.recordService.batchEdit(records, table);

        log.info("buildTransaction end");
        return transaction;

    }
}
