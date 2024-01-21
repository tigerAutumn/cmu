package cc.newex.wallet.wallet;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.criteria.CurrencyBalanceExample;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.CurrencyBalance;
import cc.newex.wallet.pojo.OmniTransaction;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.CurrencyBalanceService;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.service.UtxoTransactionService;
import cc.newex.wallet.service.WithdrawRecordService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @data 26/04/2018
 */
@Slf4j
abstract public class AbstractWallet implements IWallet {

    @Autowired
    protected CurrencyBalanceService balanceService;

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected WithdrawTransactionService withdrawTransactionService;
    @Autowired
    protected WithdrawRecordService recordService;

    @Autowired
    protected AccountTransactionService accountTransactionService;

    @Autowired
    protected UtxoTransactionService utxoTransactionService;
    @Autowired
    protected AddressService addressService;

    @Autowired
    protected TransactionService transactionService;

    protected void updateTotalCurrencyBalance(final CurrencyEnum currency, final BigDecimal balance) {
        final CurrencyBalanceExample example = new CurrencyBalanceExample();
        example.createCriteria().andCurrencyIndexEqualTo(currency.getIndex());
        CurrencyBalance currencyBalance = this.balanceService.getOneByExample(example);
        if (ObjectUtils.isEmpty(currencyBalance)) {
            currencyBalance = CurrencyBalance.builder()
                    .currencyIndex(currency.getIndex())
                    .balance(balance)
                    .createDate(Date.from(Instant.now()))
                    .build();
            this.balanceService.add(currencyBalance);
        } else {
            currencyBalance.setBalance(balance);
            currencyBalance.setUpdateDate(Date.from(Instant.now()));
            this.balanceService.editById(currencyBalance);
        }
    }

    /**
     * 更新差额
     *
     * @param currency
     * @param deltaBalance
     */
    protected void updateCurrencyDeltaBalance(final CurrencyEnum currency, final BigDecimal deltaBalance) {
        final CurrencyBalanceExample example = new CurrencyBalanceExample();
        example.createCriteria().andCurrencyIndexEqualTo(currency.getIndex());
        final CurrencyBalance currencyBalance = this.balanceService.getOneByExample(example);
        if (!ObjectUtils.isEmpty(currencyBalance)) {
            currencyBalance.setBalance(currencyBalance.getBalance().add(deltaBalance));
            currencyBalance.setUpdateDate(Date.from(Instant.now()));
            this.balanceService.editById(currencyBalance);
        }
    }

    /**
     * 更新钱包中的币余额
     */
    @Override
    public void updateTotalCurrencyBalance() {
        final CurrencyEnum currency = this.getCurrency();

        log.info("update total balance begin: {}", currency.getName());

        if (this.shouldUpdateTotalBalance()) {
            final BigDecimal balance = this.getBalance();
            this.updateTotalCurrencyBalance(currency, balance);

            log.info("update total balance, currency: {}, balance: {}", currency.getName(), balance);
        }

        log.info("update total balance end: {}", currency.getName());
    }

    protected boolean shouldUpdateTotalBalance() {
        return true;
    }

    public TransactionDTO convertUtxoToDto(final UtxoTransaction utxo) {
        final StringBuilder txidBuilder = new StringBuilder();
        //utxo 的交易txid不能唯一标示一笔充值, 所以把`txid-seq`设置为唯一标识。方便统一处理
        txidBuilder.append(utxo.getTxId()).append("-").append(utxo.getSeq());
        return TransactionDTO.builder()
                .address(utxo.getAddress())
                .balance(utxo.getBalance())
                .blockHeight(utxo.getBlockHeight())
                .confirmNum(utxo.getConfirmNum())
                .txId(txidBuilder.toString())
                .currency(utxo.getCurrency())
                .biz(utxo.getBiz())
                .build();
    }

    public TransactionDTO convertAccountTxToDto(final AccountTransaction accountTx) {
        return TransactionDTO.builder()
                .address(accountTx.getAddress())
                .balance(accountTx.getBalance())
                .blockHeight(accountTx.getBlockHeight())
                .confirmNum(accountTx.getConfirmNum())
                .txId(accountTx.getTxId())
                .biz(accountTx.getBiz())
                .currency((accountTx.getCurrency()))
                .build();
    }

    public TransactionDTO convertOmniTXToDto(final OmniTransaction omni) {

        final StringBuilder txidBuilder = new StringBuilder();
        //utxo 的交易txid不能唯一标示一笔充值, 所以把`txid-seq`设置为唯一标识。方便统一处理
        txidBuilder.append(omni.getTxId()).append("-").append(omni.getSeq());
        return TransactionDTO.builder()
                .address(omni.getAddress())
                .balance(omni.getOmniBalance())
                .blockHeight(omni.getBlockHeight())
                .confirmNum(omni.getConfirmNum())
                .txId(txidBuilder.toString())
                .biz(omni.getBiz())
                .currency(omni.getCurrency())
                .build();
    }

    /**
     * 检测txid 是否是我们自己发出的交易，如果是，更新交易状态为已确认
     *
     * @param txId
     * @param currency
     */
    protected void updateWithdrawTXId(final String txId, final CurrencyEnum currency) {
        //先检测是不是我们发出的交易
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        final WithdrawTransactionExample withExam = new WithdrawTransactionExample();
        withExam.createCriteria().andTxIdEqualTo(txId);

        final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getOneByExample(withExam, table);

        if (!ObjectUtils.isEmpty(withdrawTransaction)) {
            withdrawTransaction.setStatus(Constants.CONFIRM);
            withdrawTransaction.setUpdateDate(Date.from(Instant.now()));

            this.withdrawTransactionService.editById(withdrawTransaction, table);

            final WithdrawRecordExample recordExample = new WithdrawRecordExample();
            recordExample.createCriteria().andTxIdEqualTo(txId);

            final List<WithdrawRecord> withdrawRecords = this.recordService.getByExample(recordExample, table);

            if (!CollectionUtils.isEmpty(withdrawRecords)) {
                withdrawRecords.parallelStream().forEach((record -> record.setStatus((byte) Constants.CONFIRM)));

                this.recordService.batchEdit(withdrawRecords, table);
            }
        }
    }

    public String getWithdrawAddress() {
        throw new RuntimeException(this.getCurrency().getName() + " does not support getWithdrawAddress method");
    }

    public void transfer(final String address, final CurrencyEnum currency, final Date deadline) {
        throw new RuntimeException(this.getCurrency().getName() + " does not support transfer method");

    }

    @Override
    public String getTxId(final WithdrawTransaction transaction) {
        throw new RuntimeException(this.getCurrency().getName() + " does not support getTxId method");

    }

    @Override
    public String getBlockHash(final Long height) {

        throw new RuntimeException(this.getCurrency().getName() + " has not impl getBlockHash method");
    }

    //主要是用来启动内部事务
    protected <T> T getSelf(final Class<T> clazz) {
        return this.applicationContext.getBean(clazz);
    }

}
