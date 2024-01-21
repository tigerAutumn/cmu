package cc.newex.wallet.wallet;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/5/16
 */
@Slf4j
public abstract class AbstractAccountWallet extends AbstractWallet {

    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support genNewAddress() method");
    }

    @Override
    public BigDecimal getBalance() {
        throw new RuntimeException(this.getClass().getName() + "{} does not support getBalance() method");
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support sendRawTransaction() method");
    }

    @Override
    public int getConfirm(final String txId) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support getConfirm() method");

    }

    @Override
    public boolean checkAddress(final String addressStr) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support checkAddress() method");
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support findRelatedTxs() method");

    }

    /**
     * 获取余额
     *
     * @param address
     * @param currency
     * @return
     */
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support getBalance( String address,  CurrencyEnum currency) method");
    }

    /**
     * 构建交易
     *
     * @param record
     * @return
     */
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        throw new RuntimeException(this.getClass().getName() + "{} does not support buildTransaction method");

    }

    /**
     * 提现方法
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final BigDecimal balance = this.getBalance(this.getWithdrawAddress(), currency);

        if (balance.compareTo(record.getBalance()) < 0) {
            log.error("The {} balance is not enough, wallet balance: {}, record balance: {}",
                    currency.getName(), balance, record.getBalance());

            return false;
        }

        final WithdrawTransaction transaction = this.buildTransaction(record);
        if (transaction == null) {
            log.error(" {} withdraw error, buildTransaction failed", currency.getName());
            return false;
        }
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final String transactionId = transaction.getId().toString();

        record.setStatus((byte) Constants.SIGNING);
        record.setTxId(transactionId);
        record.setUpdateDate(Date.from(Instant.now()));
        this.recordService.editById(record, table);
        //把交易推送到待签名队列
        final String val = JSONObject.toJSONString(transaction);
        //只支持单签，所以直接用第二台机器签名
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
        log.info("{} buildTransaction success, id:{}", currency.getName(), transaction.getId());

        return true;
    }

    //当币种需要从多个地址划转到一个统一地址时，需要更新划转状态
    protected void updateAccountTransaction(final String txId, final CurrencyEnum currency) {
        try {
            final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

            final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getByTxId(txId, currency);
            if (!ObjectUtils.isEmpty(withdrawTransaction)) {
                final JSONObject sigJson = JSONObject.parseObject(withdrawTransaction.getSignature());
                final String fromAddr = sigJson.getString("from");
                final AccountTransactionExample example = new AccountTransactionExample();
                example.createCriteria().andAddressEqualTo(fromAddr).andStatusEqualTo((byte) Constants.SIGNING);
                final AccountTransaction transaction = new AccountTransaction();
                transaction.setStatus((byte) Constants.CONFIRM);
                this.accountTransactionService.editByExample(transaction, example, table);
            }
        } catch (final Throwable e) {
            log.error("updateWithdrawTXId error ,txid:{}", txId);
        }

    }

    protected boolean checkInternalTransferTx(final String from, final String txId) {
        if (StringUtils.hasText(from) && from.equals(this.getWithdrawAddress())) {
            final WithdrawRecordExample recordExample = new WithdrawRecordExample();
            recordExample.createCriteria().andTxIdEqualTo(txId);
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final List<WithdrawRecord> withdrawRecords = this.recordService.getByExample(recordExample, table);
            //如果发送者是内部的提现地址，但是找不到提现记录 @WithdrawRecord, 说明是往内转转账（比如往erc20地址上转0.03eth），不是用户充值
            if (CollectionUtils.isEmpty(withdrawRecords)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        log.info("update {} transaction confirmation begin", currency.getName());
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final PageInfo page = new PageInfo();
        final int size = 500;
        page.setPageSize(size);
        page.setSortItem("id");
        page.setSortType(PageInfo.SORT_TYPE_ASC);
        page.setStartIndex(0);

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andConfirmNumLessThan(currency.getConfirmNum());
        while (true) {
            final List<AccountTransaction> acTxs = this.accountTransactionService.getByPage(page, example, table);
            acTxs.parallelStream().forEach((tx) -> {
                tx.setCurrency(currency.getIndex());
                tx.setUpdateDate(Date.from(Instant.now()));

                final Integer confirm = this.getConfirm(tx.getTxId());
                tx.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);
                this.accountTransactionService.editById(tx, table);
                final TransactionDTO dto = this.convertAccountTxToDto(tx);
                this.transactionService.saveTransaction(dto);

            });

            if (acTxs.size() < size) {
                break;
            }
            page.setStartIndex(page.getStartIndex() + size);
        }

        log.info("update {} transaction confirmation end", currency.getName());
    }
}
