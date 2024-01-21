package cc.newex.wallet.wallet.impl;

import NEO.Helper;
import NEO.UInt160;
import NEO.Wallets.Wallet;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.NeoCommand;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.UtxoTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cc.newex.wallet.currency.CurrencyEnum.GAS;
import static cc.newex.wallet.currency.CurrencyEnum.NEP5;
import static cc.newex.wallet.utils.Constants.TRANSFER;
import static cc.newex.wallet.utils.Constants.WITHDRAW;

/**
 * @author newex-team
 * @data 2018/7/18
 */
@Slf4j
@Component
public class Nep5Wallet extends AbstractAccountWallet {

    @Value("${newex.neo.withdraw.address}")
    private String withdrawAddress;

    @Autowired
    private NeoCommand command;

    @Autowired
    private UtxoTransactionService utxoTransactionService;

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return NEP5;
    }

    @Override
    public BigDecimal getDecimal() {
        throw new RuntimeException("Nep5 does not support getDecimal method");
    }

    public BigDecimal getBalance(final CurrencyEnum currency) {
        final String currencyName = currency.getName();
        log.info("get {} Balance begin", currencyName);

        try {
            final AccountTransactionExample example = new AccountTransactionExample();
            example.createCriteria().andStatusLessThan((byte) Constants.CONFIRM)
                    .andAddressNotEqualTo(this.getWithdrawAddress())
                    .andConfirmNumGreaterThanOrEqualTo(currency.getDepositConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

            final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
            final List<AccountTransaction> accountTransactions = this.accountTransactionService.getByExample(example, table);
            final Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
            addresses.add(this.withdrawAddress);

            final BigDecimal total = addresses.parallelStream().map((address) -> this.getBalance(address, currency)).reduce(BigDecimal.ZERO, BigDecimal::add);
            log.info("get {} Balance end", currencyName);

            return total;
        } catch (final Throwable e) {
            log.error("get {} Balance error", currencyName, e);

        }
        return BigDecimal.ZERO;

    }

    @Override
    public BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            final JSONArray params = new JSONArray();
            final JSONObject param = new JSONObject();
            param.put("type", "Hash160");
            param.put("value", Wallet.toScriptHash(address).toString());
            params.add(param);
            final JSONObject balanceObject = this.command.invoke(currency.getContractAddress(), "balanceOf", params);
            if (!CollectionUtils.isEmpty(balanceObject)) {
                final JSONArray stack = balanceObject.getJSONArray("stack");
                final JSONObject value = stack.getJSONObject(0);

                if (!StringUtils.isEmpty(value.getString("value"))) {
                    final BigInteger bigInteger = new BigInteger(Helper.reverse(Helper.hexToBytes(value.getString("value"))));

                    balance = new BigDecimal(bigInteger).divide(currency.getDecimal());

                }
            }

        } catch (final Throwable e) {
            log.error("get {} balance error", currency.getName(), e);
        }

        return balance;
    }

    @Override
    public void updateTotalCurrencyBalance() {
        log.info("updateTotalCurrencyBalance nep5 begin");
        CurrencyEnum.NEP5_SET.parallelStream().forEach((currency) -> {
            log.info("update {} total Balance begin", currency.getName());
            final BigDecimal balance = this.getBalance(currency);
            this.updateTotalCurrencyBalance(currency, balance);
            log.info("update {} total Balance end", currency.getName());

        });

        log.info("updateTotalCurrencyBalance nep5 end");

    }

    @Override
    protected void updateWithdrawTXId(final String txId, final CurrencyEnum currency) {
        super.updateWithdrawTXId(txId, currency);
        this.updateAccountTransaction(txId, currency);
    }

    public List<TransactionDTO> findRelatedTxs(final JSONObject txJson, final Long height) {
        final String txId = txJson.getString("txid").replace("0x", "");
        final String txType = txJson.getString("type");
        //log.info("nep5 findRelatedTxs, txJson:{} ", txJson);

        if (!"InvocationTransaction".equals(txType)) {
            return null;
        }
        //log.info("nep5 findRelatedTxs, txid:{} begin", txId);

        final List<AccountTransaction> transactions = new LinkedList<>();
        try {
            JSONObject appLog = this.command.getApplicationLog(txId);
            //log.info("nep5 findRelatedTxs, appLog:{} ", appLog);

            if (!CollectionUtils.isEmpty(appLog)) {
                if (appLog.containsKey("executions")) {
                    appLog = appLog.getJSONArray("executions").getJSONObject(0);
                }
                final String vmState = appLog.getString("vmstate");
                if (StringUtils.hasText(vmState) && !vmState.contains("FAULT")) {
                    final JSONArray notifications = appLog.getJSONArray("notifications");
                    if (CollectionUtils.isEmpty(notifications)) {
                        return null;
                    }
                    final JSONObject notification = notifications.getJSONObject(0);
                    //transactions = notifications.stream().map((obj) -> {
                    //JSONObject notification = (JSONObject) obj;
                    final String contract = notification.getString("contract");
                    log.info("nep5 contract:{}", contract);
                    final CurrencyEnum currencyEnum = CurrencyEnum.parseContract(contract);
                    if (ObjectUtils.isEmpty(currencyEnum)) {
                        return null;
                    }
                    log.info("nep5 findRelatedTxs, txid:{} begin", txId);

                    //检测是不是我们发出的交易
                    this.updateWithdrawTXId(txId, currencyEnum);

                    final JSONObject state = notification.getJSONObject("state");
                    final JSONArray value = (JSONArray) state.get("value");
                    /**
                     * 对于转账交易，"state" 中 "value" 对应的数组包含以下四个对象：
                     * [事件，转出账户，转入账户，金额]
                     */
                    if (value.size() != 4) {
                        return null;
                    }

                    final String event = value.getJSONObject(0).getString("value");
                    // 7472616e73666572 是  'transfer' 的十六进制表示
                    final String transferEventType = "7472616e73666572";
                    if (!StringUtils.hasText(event) || !event.equals(transferEventType)) {
                        return null;
                    }

                    String from = value.getJSONObject(1).getString("value");
                    from = Wallet.toAddress(new UInt160(Helper.hexToBytes(from)));
                    String to = value.getJSONObject(2).getString("value");

                    // https://neoscan.io/transaction/D8079CB90F6249CB91A6F13241016C5CD21598974C7C9834E116CF9300E84DC0
                    // 这个tx_id对应的to为空
                    if (!StringUtils.hasText(from) || !StringUtils.hasText(to)) {
                        return null;
                    }

                    to = Wallet.toAddress(new UInt160(Helper.hexToBytes(to)));

                    if (!StringUtils.hasText(from) || !StringUtils.hasText(to)) {
                        return null;
                    }

                    final Address address = this.addressService.getAddress(to, CurrencyEnum.NEO);
                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }
                    final String amountValue = value.getJSONObject(3).getString("value");

                    final String type = value.getJSONObject(3).getString("type");

                    final BigInteger bigInteger;

                    if (type.equals("ByteArray")) {
                        bigInteger = new BigInteger(Helper.reverse(Helper.hexToBytes(amountValue)));
                    } else if (type.equals("Integer")) {
                        bigInteger = new BigInteger(amountValue);
                    } else {
                        return null;
                    }
                    final BigDecimal amount = new BigDecimal(bigInteger).divide(currencyEnum.getDecimal());
                    final Long bestHeight = this.getBestHeight();
                    final AccountTransaction transaction = AccountTransaction.builder()
                            .blockHeight(height)
                            .address(to)
                            .biz(address.getBiz())
                            .balance(amount)
                            .createDate(Date.from(Instant.now()))
                            .txId(txId)
                            .currency(currencyEnum.getIndex())
                            .status((byte) Constants.WAITING)
                            .confirmNum(bestHeight - height + 1)
                            .build();
                    transactions.add(transaction);
                    //return transaction;

                    //}).filter(tx -> !ObjectUtils.isEmpty(tx)).collect(Collectors.toList());
                }
            }
        } catch (final Exception e) {
            log.error("get nep5 txid:{} error", txId, e);
        }

        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(transactions)) {
            dtos = transactions.parallelStream()
                    .map(tx -> {
                        CurrencyEnum currency = CurrencyEnum.parseValue(tx.getCurrency());
                        ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
                        this.accountTransactionService.addOnDuplicateKey(tx, table);
                        return this.convertAccountTxToDto(tx);
                    })
                    .collect(Collectors.toList());
        }
        return dtos;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void transfer(final String address, final CurrencyEnum currency, final Date deadline) {
        final BigDecimal balance = this.getBalance(address, currency);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            final WithdrawRecord record = WithdrawRecord.builder()
                    .address(this.withdrawAddress)
                    .balance(balance)
                    .currency(currency.getIndex())
                    .build();
            final WithdrawTransaction transferTransaction = this.buildTransaction(record, address, TRANSFER);
            if (transferTransaction == null) {
                log.error(" {} transfer error, buildTransaction failed", currency.getName());
                return;
            }
            //把交易推送到待签名队列
            final String val = JSONObject.toJSONString(transferTransaction);
            //eth 只支持单签，所以直接用第二台机器签名
            REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
        }

        final AccountTransaction transaction = new AccountTransaction();
        transaction.setStatus((byte) Constants.SIGNING);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andAddressEqualTo(address)
                .andConfirmNumGreaterThan(currency.getDepositConfirmNum())
                .andCreateDateLessThan(deadline);

        this.accountTransactionService.editByExample(transaction, example, table);
        log.info("{} buildTransaction success, id:{}", currency.getName(), transaction.getId());

    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {

        return this.buildTransaction(record, this.withdrawAddress, WITHDRAW);
    }

    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from, final String type) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        final AddressExample addrExam = new AddressExample();
        addrExam.createCriteria().andAddressEqualTo(from);
        final ShardTable addressTable = ShardTable.builder().prefix(CurrencyEnum.NEO.getName()).build();

        final Address address = this.addressService.getAndLockOneByExample(addrExam, addressTable);

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("value", record.getBalance());

        final UtxoTransactionExample example = new UtxoTransactionExample();
        final BigDecimal minGasFee = new BigDecimal("0.00000001");
        example.createCriteria().andStatusEqualTo((byte) Constants.WAITING).andBalanceGreaterThan(minGasFee);
        final UtxoTransaction gasUtxo = this.utxoTransactionService.markAsSpent(example, GAS);

        if (ObjectUtils.isEmpty(gasUtxo)) {
            log.error("buildTransaction failed , neo gas is not enough");
            return null;
        } else {

            final Address gasAddress = this.addressService.getAddress(gasUtxo.getAddress(), addressTable);
            signature.put("gasInput", gasUtxo);
            signature.put("gasAddress", gasAddress);
            signature.put("gasChange", this.withdrawAddress);
        }

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId(type)
                .signature(signature.toJSONString())
                .build();
        ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);

        //把占用的gas utxo和withdraw transaction绑定
        final String transactionId = transaction.getId().toString() + currency.getName();
        gasUtxo.setSpentTxId(transactionId);
        table = ShardTable.builder().prefix(GAS.getName()).build();
        this.utxoTransactionService.editById(gasUtxo, table);

        log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

    @Override
    public Long getBestHeight() {
        final long height = this.command.getBlockCount("latest");
        //由于neo获取最新区块有时会报错：Unknown block
        return height - 1;
    }

    @Override
    public int getConfirm(final String txId) {
        final JSONObject transaction = this.command.getRawTransaction(txId, 1);
        if (ObjectUtils.isEmpty(transaction)) {
            return -1;
        } else {
            final int confirm = transaction.getIntValue("confirmations");
            return confirm;
        }
    }

}
