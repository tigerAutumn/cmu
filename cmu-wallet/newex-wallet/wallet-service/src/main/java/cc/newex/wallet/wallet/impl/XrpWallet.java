package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.XrpCommand;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ripple.core.coretypes.AccountID;
import com.ripple.core.coretypes.uint.UInt32;
import com.ripple.crypto.ecdsa.Seed;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.ECKey;
import org.ripple.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Security;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static cc.newex.wallet.currency.CurrencyEnum.XRP;
import static cc.newex.wallet.utils.Constants.CONFIRM;

/**
 * @author newex-team
 * @data 2018/6/10
 */
@Slf4j
@Component
public class XrpWallet extends AbstractAccountWallet {

    @Autowired
    private XrpCommand command;

    @Value("${newex.xrp.withdraw.address}")
    private String withdrawAddress;

    private static final Set<String> txKey = new HashSet<>();

    static {
        txKey.add("Account");
        txKey.add("Amount");
        txKey.add("Destination");
        txKey.add("DestinationTag");
        txKey.add("Fee");
        txKey.add("Flags");
        txKey.add("LastLedgerSequence");
        txKey.add("Sequence");
        txKey.add("SigningPubKey");
        txKey.add("TransactionType");
        txKey.add("TxnSignature");
        txKey.add("date");
        txKey.add("hash");
        txKey.add("inLedger");
        txKey.add("ledger_index");
        txKey.add("Signers");
        Security.addProvider(new BouncyCastleProvider());

    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XRP;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.XRP.getDecimal();
    }

    /**
     * 生成新地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        final String placeholder = UUID.randomUUID().toString();
        final Address address = new Address();
        address.setAddress(placeholder);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(0);

        this.addressService.add(address, table);
        final String addressStr = this.withdrawAddress + ":" + address.getId();
        address.setAddress(addressStr);
        this.addressService.editById(address, table);

        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    /**
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.withdrawAddress, this.getCurrency());
    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        try {
            final JSONObject param = new JSONObject();
            param.put("account", address);
            final JSONObject res = this.command.getAccountInfo(param);
            BigDecimal amount = res.getJSONObject("account_data").getBigDecimal("Balance");
            amount = amount.divide(this.getCurrency().getDecimal());
            return amount;
        } catch (final Throwable e) {
            log.error("get currency:{} balance error", this.getCurrency(), e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public Long getBestHeight() {
        try {
            final JSONObject res = this.command.getCurrentHeight();

            final Long height = res.getLongValue("ledger_current_index");

            return height;
        } catch (final Throwable e) {
            log.error("getBestHeight currency:{} error", this.getCurrency(), e);

            return 0L;
        }

    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();

        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);

        final JSONObject paramsJson = new JSONObject();

        int offset = 0;

        paramsJson.put("account", this.withdrawAddress);
        paramsJson.put("binary", false);
        paramsJson.put("ledger_index_max", height);
        paramsJson.put("ledger_index_min", height);
        paramsJson.put("limit", 100);

        final List<AccountTransaction> accountTransactions = new LinkedList<>();

        do {
            paramsJson.put("offset", offset);

            final JSONObject result = this.command.getAccountTx(paramsJson);

            log.info("get ripple tx size: {}", result.size());

            final JSONArray txsArray = result.getJSONArray("transactions");

            final List<AccountTransaction> tmp = txsArray.parallelStream()
                    .map((object) -> this.getAccountTx((JSONObject) object))
                    .filter(tx -> !ObjectUtils.isEmpty(tx))
                    .collect(Collectors.toList());

            accountTransactions.addAll(tmp);

            offset = accountTransactions.size();

            //offset % 100 == 0说明交易可能没有获取完毕。
        } while (offset > 0 && offset % 100 == 0);

        List<TransactionDTO> dtos = new LinkedList<>();

        if (!CollectionUtils.isEmpty(accountTransactions)) {
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.accountTransactionService.batchAddOnDuplicateKey(accountTransactions, table);
            dtos = accountTransactions.parallelStream().map(this::convertAccountTxToDto).collect(Collectors.toList());
        }

        log.info("{} findRelatedTxs, height:{} end", currencyName, height);

        return dtos;
    }

    private AccountTransaction getAccountTx(JSONObject txJson) {
        if (txJson == null) {
            return null;
        }

        try {
            //boolean validated = txJson.getBooleanValue("validated");

            if (txJson.getJSONObject("tx") != null) {
                txJson = txJson.getJSONObject("tx");
            }

            if (!txJson.containsKey("TransactionType") || !txJson.getString("TransactionType").equals("Payment")) {
                return null;
            }

            for (final String key : txJson.keySet()) {
                if (!txKey.contains(key)) {
                    return null;
                }
            }

            final String txId = txJson.getString("hash");

            this.updateWithdrawTXId(txId, this.getCurrency());

            final BigInteger tag = txJson.getBigInteger("DestinationTag");

            final String toAddress = this.withdrawAddress;

            if (toAddress.equals(txJson.getString("Destination"))) {
                final Address address = this.getAddress(toAddress, tag);

                if (ObjectUtils.isEmpty(address)) {
                    return null;
                }

                final AccountTransaction transaction = AccountTransaction.builder()
                        .address(address.getAddress())
                        .balance(txJson.getBigDecimal("Amount").divide(this.getCurrency().getDecimal()))
                        .biz(address.getBiz())
                        .txId(txId)
                        .blockHeight(txJson.getLong("ledger_index"))
                        //As long as one tx is found, it is confirmed, so the confirmation num is set to XRP's ConfirmNum
                        .confirmNum(this.getCurrency().getConfirmNum())
                        .status((byte) Constants.WAITING)
                        .currency(this.getCurrency().getIndex())
                        .createDate(Date.from(Instant.now()))
                        .updateDate(Date.from(Instant.now()))
                        .build();

                return transaction;
            } else {
                return null;
            }

        } catch (final Throwable e) {
            log.error("convertFromJson error,txJson:{}", txJson, e);
            return null;
        }
    }

    /**
     * 如果tag错误，默认转入10号账户，但是10号账户的地址需要提前生成
     *
     * @param toAddress
     * @param tag
     * @return
     */
    private Address getAddress(final String toAddress, final BigInteger tag) {
        final Long userId = 10L;

        String addressStr = toAddress;
        if (!ObjectUtils.isEmpty(tag)) {
            addressStr = toAddress + ":" + tag.toString();
        }

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        // 验证xrp地址
        Address address = this.addressService.getAddress(addressStr, table);

        // tag错误，默认转入10号账户
        if (ObjectUtils.isEmpty(address) || address.getUserId() <= 0L) {
            log.error("invalid tag, toAddress: {}, tag: {}, transfer in No.10", toAddress, tag);

            address = this.addressService.getAddress(userId, this.getCurrency());
        }

        if (!ObjectUtils.isEmpty(address)) {
            final String mainAddress = address.getAddress().split(":")[0];

            if (!toAddress.equals(mainAddress)) {
                log.error("invalid address, toAddress: {}, tag: {}, mainAddress: {}", toAddress, tag, mainAddress);

                return null;
            }
        }

        return address;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final WithdrawTransactionExample withExam = new WithdrawTransactionExample();
        withExam.setOrderByClause("id desc");

        final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getOneByExample(withExam, table);
        //ripple 发送交易必须一笔一笔发送，所以要确保最后一笔交易已经确认了
        if (!ObjectUtils.isEmpty(withdrawTransaction) && withdrawTransaction.getStatus() < CONFIRM) {
            return false;
        } else {
            return super.withdraw(record);
        }
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        final ShardTable addressTable = ShardTable.builder().prefix(currency.getName()).build();

        final Address address = this.addressService.getAddress(this.withdrawAddress, addressTable);

        final JSONObject signature = new JSONObject();

        final JSONObject param = new JSONObject();
        param.put("account", address.getAddress());
        JSONObject res = this.command.getAccountInfo(param);
        final Integer remoteSequence = res.getJSONObject("account_data").getInteger("Sequence");

        address.setNonce(remoteSequence);

        res = this.command.getServerInfo();

        final BigDecimal baseFee = res.getJSONObject("info").getJSONObject("validated_ledger").getBigDecimal(
                "base_fee_xrp").multiply(this.getCurrency().getDecimal());
        final int loadFactor = res.getJSONObject("info").getIntValue("load_factor");

        final long lastLedgerSequence = res.getJSONObject("info").getJSONObject("validated_ledger").getLongValue("seq") + 1000;

        signature.put("address", address);
        signature.put("from", this.withdrawAddress);
        signature.put("to", record.getAddress());
        signature.put("amount", record.getBalance());
        signature.put("fee", 2 * loadFactor * baseFee.intValue());
        signature.put("lastLedgerSequence", lastLedgerSequence);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId("transfer")
                .signature(signature.toJSONString())
                .build();

        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);
        log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

    /**
     * 发送签好的原始交易
     *
     * @param transaction
     * @return
     */
    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());

            final String raw = signature.getString("rawTransaction");
            final JSONObject params = new JSONObject();
            params.put("tx_blob", raw);
            final JSONObject result = this.command.sendRawTransaction(params);
            final String txId = result.getJSONObject("tx_json").getString("hash");
            return txId;
        } catch (final Throwable e) {
            log.error("sendRawTransaction error", e);
            return "";
        }
    }

    @Override
    public boolean checkAddress(final String addressWithTag) {
        try {
            final String[] addressAndTag = addressWithTag.split(":");
            if (addressAndTag.length != 2) {
                return false;
            }
            final String address = addressAndTag[0];

            if (address.equals(this.withdrawAddress)) {
                return false;
            }
            final String tag = addressAndTag[1];
            final BigInteger tagInteger = new BigInteger(tag);
            if (tagInteger.compareTo(UInt32.Max32) > 0) {
                return false;
            }
            //通过转化来判断地址是否合法，如果不合法会抛异常
            AccountID.fromAddress(address);
            return true;

        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", addressWithTag, e);
            return false;
        }

    }

    @Override
    public int getConfirm(final String txId) {
        final JSONObject params = new JSONObject();
        params.put("transaction", txId);
        JSONObject res = this.command.getTransaction(params);

        if (res.getJSONObject("tx") != null) {
            res = res.getJSONObject("tx");
        }
        final Long height = res.getLong("ledger_index");
        final Long bestHeight = this.getBestHeight();
        final Long confirm = bestHeight - height;
        return confirm.intValue();
    }

}
