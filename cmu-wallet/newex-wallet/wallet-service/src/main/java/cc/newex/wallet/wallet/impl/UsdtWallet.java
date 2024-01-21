package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.UsdtCommand;
import cc.newex.wallet.criteria.OmniTransactionExample;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.OmniTransaction;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import cc.newex.wallet.pojo.rpc.OmniRawTransaction;
import cc.newex.wallet.pojo.rpc.ScriptPubKey;
import cc.newex.wallet.pojo.rpc.TxOutput;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.OmniTransactionService;
import cc.newex.wallet.service.WithdrawRecordService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.UNSPENT_TX_ID;

/**
 * @author newex-team
 * @data 08/04/2018
 */
@Slf4j
@Component
public class UsdtWallet extends AbstractBtcLikeWallet implements IWallet {
    //发送到用户提现地址的固定btc数量
    private static final BigDecimal FIXED_AMOUNT = new BigDecimal("0.00002");
    private final ReentrantLock lock = new ReentrantLock();
    @Autowired
    UsdtCommand command;
    @Autowired
    AddressService addressService;
    @Autowired
    WithdrawTransactionService withdrawTransactionService;
    @Autowired
    WithdrawRecordService recordService;
    @Autowired
    OmniTransactionService omniTransactionService;
    @Value("${newex.usdt.withdraw.address}")
    private String withdrawAddress;
    private Integer USDT_PROPERTY_ID = 31;

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.USDT;
    }

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
        if (this.CONSTANT.NETWORK.equals("test")) {
            this.USDT_PROPERTY_ID = 2;
        }
    }

    @Override
    public NetworkParameters getNetworkParameters() {
        if (this.CONSTANT.NETWORK.equals("test")) {
            return TestNet3Params.get();
        }

        return MainNetParams.get();
    }

    public TransactionDTO checkOmniTransaction(final Transaction tx, final Long height) {
        final List<TransactionOutput> outputs = tx.getOutputs();
        boolean omniTx = false;
        for (final TransactionOutput out : outputs) {
            final Script script = out.getScriptPubKey();
            if (script.isOpReturn()) {
                final String data = script.getChunks().get(1).toString();
                if (data.contains(Constants.OMNI_HEADER)) {
                    omniTx = true;
                    break;
                }
            }
        }
        if (!omniTx) {
            return null;
        }

        try {
            final OmniRawTransaction omniRawTx = this.command.getOmniRawTransaction(tx.getHashAsString());
            if (ObjectUtils.isEmpty(omniRawTx) || omniRawTx.getPropertyid() != this.USDT_PROPERTY_ID || omniRawTx.getTypeint() != 0) {
                return null;
            }
            final String addressStr = omniRawTx.getReferenceaddress();
//            final AddressExample example = new AddressExample();
//            example.createCriteria().andAddressEqualTo(addressStr);
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final Address address = this.addressService.getAddress(addressStr, table);
            if (ObjectUtils.isEmpty(address)) {
                return null;
            }
            final TransactionOutput output = this.getOmniReceiverFromBj(outputs, omniRawTx.getReferenceaddress());
            if (ObjectUtils.isEmpty(output)) {
                UsdtWallet.log.error("can not find related omni receiver,txId:{} address:{}", tx.getHashAsString(), omniRawTx.getReferenceaddress());
                return null;
            }

            final Long bestHeight = this.getBestHeight();
            Long confirm = 0L;
            if (height > 0L) {
                confirm = bestHeight >= height ? bestHeight - height + 1 : 0;
            }
            final BigDecimal balance = new BigDecimal(output.getValue().getValue());
            final BigDecimal omniBalance = omniRawTx.getAmount();
            final OmniTransaction transaction = OmniTransaction.builder()
                    .address(omniRawTx.getReferenceaddress())
                    .balance(balance.divide(this.getDecimal()))
                    .omniBalance(omniBalance)
                    .biz(address.getBiz())
                    .currency(this.getCurrency().getIndex())
                    .blockHeight(height)
                    .confirmNum(confirm)
                    .seq((short) output.getIndex())
                    .spent((byte) 0)
                    .spentTxId(UNSPENT_TX_ID)
                    .createDate(Date.from(Instant.now()))
                    .txId(tx.getHashAsString())
                    .build();

            final TransactionDTO dto = this.convertOmniTXToDto(transaction);
            this.omniTransactionService.addOnDuplicateKey(transaction, table);
            return dto;

        } catch (final JsonRpcClientException e) {
            if (!e.toString().contains("No information available about transaction")) {
                UsdtWallet.log.error("checkOmniTransaction error", e);
            }
        } catch (final Throwable e) {
            UsdtWallet.log.error("checkOmniTransaction error", e);
        }
        return null;
    }

    //从bitcoinj的tx type中读取
    private TransactionOutput getOmniReceiverFromBj(final List<TransactionOutput> outputs, final String address) {
        for (final TransactionOutput out : outputs) {

            final Script script = out.getScriptPubKey();
            if (script.isOpReturn()) {
                continue;
            }
            if (script.isPayToScriptHash() || script.isPayToScriptHash() || script.isSentToRawPubKey()) {
                final String tmp = script.getToAddress(this.getNetworkParameters()).toBase58();
                if (tmp.equals(address)) {
                    return out;
                }
            }
        }
        return null;
    }

    //从原始的的tx type中读取
    private TxOutput getOmniReceiver(final List<TxOutput> outputs, final String address) {
        for (final TxOutput out : outputs) {
            if (CollectionUtils.isEmpty(out.getScriptPubKey().getAddresses())) {
                continue;
            }
            final String tmp = out.getScriptPubKey().getAddresses().get(0);
            if (tmp.equals(address)) {
                return out;
            }
        }
        return null;
    }

    /**
     * 扫描高度:height 区块，获得相omni关交易
     *
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();
        UsdtWallet.log.info("{} findOmniTxs, height:{} begin", currencyName, height);
        //final String hash = this.command.getBlockHash(height);
        final Set<String> txIdSet = this.command.listOmniBlockTxs(height);
        final List<OmniTransaction> results = txIdSet.parallelStream().map((txId) -> {
            //先检查交易是不是我们自己发出的提现交易
            this.updateWithdrawTXId(txId, this.getCurrency());

            OmniTransaction tx = this.getOmniTx(txId, height);
            return tx;
        }).filter((omni) -> !ObjectUtils.isEmpty(omni)).collect(Collectors.toList());

        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(results)) {
            dtos = results.parallelStream()
                    .map(this::convertOmniTXToDto).collect(Collectors.toList());

            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.omniTransactionService.batchAddOnDuplicateKey(results, table);
        }

        UsdtWallet.log.info("{} findOmniTxs, height:{} end", currencyName, height);
        return dtos;
    }

    /**
     * 当发现txid是我们发出的交易时，更新交易的状态
     *
     * @param txid
     */
    @Override
    protected void updateWithdrawTXId(final String txid, final CurrencyEnum currency) {
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final WithdrawTransactionExample withExam = new WithdrawTransactionExample();
        withExam.createCriteria().andTxIdEqualTo(txid);
        final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getOneByExample(withExam, table);
        if (!ObjectUtils.isEmpty(withdrawTransaction)) {
            withdrawTransaction.setStatus(Constants.CONFIRM);
            withdrawTransaction.setUpdateDate(Date.from(Instant.now()));
            this.withdrawTransactionService.editById(withdrawTransaction, table);

            final OmniTransactionExample example = new OmniTransactionExample();
            example.createCriteria().andSpentTxIdEqualTo(txid);
            final List<OmniTransaction> omniTransactions = this.omniTransactionService.getByExample(example, table);
            if (!CollectionUtils.isEmpty(omniTransactions)) {
                omniTransactions.parallelStream().forEach((omniTransaction -> omniTransaction.setStatus((byte) Constants.CONFIRM)));
                this.omniTransactionService.batchEdit(omniTransactions, table);
            }

            final WithdrawRecordExample recordExample = new WithdrawRecordExample();
            recordExample.createCriteria().andTxIdEqualTo(txid);
            final List<WithdrawRecord> withdrawRecords = this.recordService.getByExample(recordExample, table);
            if (!CollectionUtils.isEmpty(withdrawRecords)) {
                withdrawRecords.parallelStream().forEach((record -> record.setStatus((byte) Constants.CONFIRM)));
                this.recordService.batchEdit(withdrawRecords, table);
            }
        }
    }

    //根据txid解析omni tx
    private OmniTransaction getOmniTx(final String txId, final Long height) {

        try {
            final OmniRawTransaction omniRawTx = this.command.getOmniRawTransaction(txId);
            if (omniRawTx.getPropertyid() != this.USDT_PROPERTY_ID || omniRawTx.getTypeint() != 0 || !omniRawTx.getValid()) {
                return null;
            }

            final BtcLikeRawTransaction rawTransaction = this.command.getRawTransaction(txId, 1);
            final List<TxOutput> vout = rawTransaction.getVout();

            final String addressStr = omniRawTx.getReferenceaddress();
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final Address address = this.addressService.getAddress(addressStr, table);
            if (ObjectUtils.isEmpty(address)) {
                return null;
            }
            final TxOutput output = this.getOmniReceiver(vout, addressStr);

            final Long bestHeight = this.getBestHeight();
            Long confirm = 0L;
            if (height > 0L) {
                confirm = bestHeight >= height ? bestHeight - height + 1 : 0;
            }
            final BigDecimal omniBalance = omniRawTx.getAmount();
            final OmniTransaction transaction = OmniTransaction.builder()
                    .address(omniRawTx.getReferenceaddress())
                    .balance(output.getValue())
                    .omniBalance(omniBalance)
                    .biz(address.getBiz())
                    .currency(this.getCurrency().getIndex())
                    .blockHeight(height)
                    .confirmNum(confirm)
                    .seq((short) output.getN())
                    .spent((byte) 0)
                    .status((byte) Constants.WAITING)
                    .spentTxId(UNSPENT_TX_ID)
                    .createDate(Date.from(Instant.now()))
                    .txId(txId)
                    .build();
            return transaction;
        } catch (final Throwable e) {
            log.error("getOmniTx error,txid:{}", txId, e);
            return null;
        }
    }

    public void scanWithdrawAddrUtxo(final TxOutput vout, final String txId, final Long confirm) {
        final ScriptPubKey pubKey = vout.getScriptPubKey();
        final String addressStr = pubKey.getAddresses().get(0);
        if (addressStr.equals(this.withdrawAddress)) {
            final OmniTransaction omni = OmniTransaction.builder()
                    .balance(vout.getValue())
                    .address(addressStr)
                    .biz(0)
                    .currency(this.getCurrency().getIndex())
                    .spent((byte) 0)
                    .spentTxId(UNSPENT_TX_ID)
                    .seq(new Integer(vout.getN()).shortValue())
                    .txId(txId)
                    .blockHeight(this.height)
                    .confirmNum(confirm)
                    .createDate(Date.from(Instant.now()))
                    .status((byte) Constants.WAITING)
                    .build();
            this.omniTransactionService.addOnDuplicateKey(omni, ShardTable.builder().prefix(CurrencyEnum.USDT.getName()).build());

        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        final BigDecimal balance = this.getBalance(this.withdrawAddress);
        if (balance.compareTo(record.getBalance()) < 0) {
            UsdtWallet.log.error("The USDT balance is not enough");
            return false;
        }
//        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
//        final OmniTransactionExample example = new OmniTransactionExample();
//        final int feeInt = REDIS.getInt(Constants.WALLET_FEE + this.getCurrency().getIndex());
//        final BigDecimal fee = BigDecimal.valueOf(feeInt).divide(this.getCurrency().getDecimal());
//        example.createCriteria().andAddressEqualTo(this.withdrawAddress).andStatusEqualTo((byte) Constants.WAITING).andSpentEqualTo((byte) 0)
//                .andConfirmNumGreaterThanOrEqualTo(this.getCurrency().getDepositConfirmNum()).andBalanceGreaterThanOrEqualTo(fee.add(UsdtWallet.FIXED_AMOUNT));
        final OmniTransaction omniTransaction = this.getOmniWithdrawAddressUnspent();
        if (ObjectUtils.isEmpty(omniTransaction)) {
            UsdtWallet.log.error("there is no enough omni utxo");
            return false;
        }
//        final List<UtxoTransaction> utxos = new LinkedList<>();
//        final UtxoTransaction utxo = UtxoTransaction.builder()
//                .txId(omniTransaction.getTxId())
//                .seq(omniTransaction.getSeq())
//                .address(omniTransaction.getAddress())
//                .balance(omniTransaction.getBalance())
//                .biz(omniTransaction.getBiz())
//                .build();
        final List<OmniTransaction> omniTransactions = new LinkedList<>();
        omniTransactions.add(omniTransaction);
        final WithdrawTransaction transaction = this.buildTransaction(omniTransactions, record);
        if (transaction == null) {
            UsdtWallet.log.error(" {} withdraw error, buildTransaction failed", this.getCurrency().getName());
            return false;
        }
        final String transactionId = transaction.getId().toString();
        omniTransaction.setSpentTxId(transactionId);
        omniTransaction.setSpent((byte) 1);
        omniTransaction.setUpdateDate(Date.from(Instant.now()));
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        this.omniTransactionService.editById(omniTransaction, table);

        record.setStatus((byte) Constants.SIGNING);
        record.setTxId(transactionId);
        record.setUpdateDate(Date.from(Instant.now()));
        this.recordService.editById(record, table);
        //把交易推送到待签名队列
        final String val = JSONObject.toJSONString(transaction);
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_FIRST_KEY, val);
        UsdtWallet.log.info("{} buildTransaction success, id:{}", this.getCurrency().getName(), transaction.getId());

        return true;
    }

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void transferUsdt(final OmniTransaction transferTransaction) {

        final OmniTransaction omniTransaction = this.getOmniWithdrawAddressUnspent();
        if (ObjectUtils.isEmpty(omniTransaction)) {
            UsdtWallet.log.error("there is no enough omni utxo");
            return;
        }
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        transferTransaction.setSpent((byte) 1);
        transferTransaction.setStatus((byte) 1);
        transferTransaction.setUpdateDate(Date.from(Instant.now()));
        this.omniTransactionService.editById(transferTransaction, table);

        final List<OmniTransaction> omniTransactions = new LinkedList<>();
        omniTransactions.add(transferTransaction);
        omniTransactions.add(omniTransaction);
        final WithdrawRecord record = WithdrawRecord.builder()
                .address(this.withdrawAddress)
                .balance(transferTransaction.getOmniBalance())
                .build();
        final WithdrawTransaction transaction = this.buildTransaction(omniTransactions, record);
        if (transaction == null) {
            UsdtWallet.log.error(" {} transferUsdt error, buildTransaction failed", this.getCurrency().getName());
            return;
        }
        final String transactionId = transaction.getId().toString();
        omniTransaction.setSpentTxId(transactionId);
        omniTransaction.setSpent((byte) 1);
        omniTransaction.setUpdateDate(Date.from(Instant.now()));
        this.omniTransactionService.editById(omniTransaction, table);

        transferTransaction.setSpentTxId(transactionId);
        transferTransaction.setSpent((byte) 1);
        transferTransaction.setUpdateDate(Date.from(Instant.now()));
        this.omniTransactionService.editById(transferTransaction, table);

        //把交易推送到待签名队列
        final String val = JSONObject.toJSONString(transaction);
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_FIRST_KEY, val);
        UsdtWallet.log.info("{} transferUsdt success, id:{}", this.getCurrency().getName(), transaction.getId());

    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {

        String result;
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            //utxo 更新spentTxId

            final String raw = signature.getString("rawTransaction");
            result = this.command.sendRawTransaction(raw);

            final String txId = result;

            //更新utxo表中的txid
            final OmniTransactionExample omniExam = new OmniTransactionExample();
            omniExam.createCriteria().andSpentTxIdEqualTo(transaction.getId().toString());
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final List<OmniTransaction> omnis = this.omniTransactionService.getByExample(omniExam, table);
            omnis.parallelStream().forEach((omni) -> {
                omni.setSpentTxId(txId);
                omni.setSpent((byte) 1);
                omni.setStatus((byte) Constants.SENT);
                omni.setUpdateDate(Date.from(Instant.now()));
                this.omniTransactionService.editById(omni, table);
            });
        } catch (final Throwable e) {
            UsdtWallet.log.error("sendRawTransaction error", e);
            result = "";
        }
        return result;
    }

    private WithdrawTransaction buildTransaction(final List<OmniTransaction> omniTransactions, final WithdrawRecord record) {
        final List<UtxoTransaction> utxos = new LinkedList<>();
        final List<Address> addresses = new LinkedList<>();

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        BigDecimal walletAmount = BigDecimal.ZERO;
        for (final OmniTransaction omni : omniTransactions) {
            final UtxoTransaction utxo = UtxoTransaction.builder()
                    .txId(omni.getTxId())
                    .seq(omni.getSeq())
                    .address(omni.getAddress())
                    .balance(omni.getBalance())
                    .biz(omni.getBiz())
                    .build();
            utxos.add(utxo);
            walletAmount = walletAmount.add(omni.getBalance());

//            final AddressExample addressExample = new AddressExample();
//            addressExample.createCriteria().andAddressEqualTo(omni.getAddress());

            final Address address = this.addressService.getAddress(omni.getAddress(), table);
            addresses.add(address);
        }

        final JSONObject signature = new JSONObject();
        //final int feeInt = REDIS.getInt(Constants.WALLET_FEE + this.getCurrency().getIndex());
        final List<WithdrawRecord> records = new LinkedList<>();
        records.add(record);
        signature.put("utxos", utxos);
        signature.put("addresses", addresses);
        signature.put("withdraw", records);
        signature.put("changeAddress", this.withdrawAddress);
        //signature.put("feePerKb", feeInt);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(walletAmount)
                .currency(this.getCurrency().getIndex())
                .status(Constants.SIGNING)
                .txId("singing")
                .signature(signature.toJSONString())
                .build();
        this.withdrawTransactionService.add(transaction, table);

        UsdtWallet.log.info("buildTransaction end");
        return transaction;

    }

    /**
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        final String currencyName = this.getCurrency().getName();
        UsdtWallet.log.info("get {} Balance begin", currencyName);

        try {
            final OmniTransactionExample example = new OmniTransactionExample();
            example.createCriteria().andStatusLessThan((byte) Constants.CONFIRM)
                    .andAddressNotEqualTo(this.withdrawAddress)
                    .andConfirmNumGreaterThanOrEqualTo(this.getCurrency().getDepositConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final List<OmniTransaction> transactions = this.omniTransactionService.getByExample(example, table);
            final Set<String> addresses = transactions.parallelStream().map(OmniTransaction::getAddress).collect(Collectors.toSet());
            addresses.add(this.withdrawAddress);

            final BigDecimal total = addresses.parallelStream().map((address) -> this.getBalance(address)).reduce(BigDecimal.ZERO, BigDecimal::add);
            UsdtWallet.log.info("get {} Balance end", currencyName);

            return total;
        } catch (final Throwable e) {
            UsdtWallet.log.error("get {} Balance error", currencyName);

        }
        return BigDecimal.ZERO;

    }

    public BigDecimal getBalance(final String address) {
        final Map<String, BigDecimal> balanceMap = this.command.getBalance(address, this.USDT_PROPERTY_ID);
        return balanceMap.get("balance");
    }

//    @Override
//    public List<UtxoTransaction> getUtxoUnspent(final PageInfo pageInfo, final UtxoTransactionExample example, final ShardTable table) {
//        throw new RuntimeException("USDT does not support getUtxoUnspent method ");
//    }

    private OmniTransaction getOmniWithdrawAddressUnspent() {
        this.lock.lock();
        OmniTransaction omniTransaction = null;
        try {
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final OmniTransactionExample example = new OmniTransactionExample();
            final int feeInt = REDIS.getInt(Constants.WALLET_FEE + this.getCurrency().getIndex());
            final BigDecimal fee = BigDecimal.valueOf(feeInt).divide(this.getCurrency().getDecimal());
            example.createCriteria().andAddressEqualTo(this.withdrawAddress).andStatusEqualTo((byte) Constants.WAITING).andSpentEqualTo((byte) 0)
                    .andConfirmNumGreaterThanOrEqualTo(this.getCurrency().getDepositConfirmNum()).andBalanceGreaterThanOrEqualTo(fee.add(UsdtWallet.FIXED_AMOUNT));
            omniTransaction = this.omniTransactionService.getOneByExample(example, table);
            //更新utxo和WithdrawRecord的status
            if (ObjectUtils.isEmpty(omniTransaction)) {
                return omniTransaction;
            }
            omniTransaction.setStatus((byte) Constants.SIGNING);
            //omniTransaction.setSpentTxId(transactionId);
            omniTransaction.setSpent((byte) 1);
            omniTransaction.setUpdateDate(Date.from(Instant.now()));
            this.omniTransactionService.editById(omniTransaction, table);
        } catch (final Exception e) {
            UsdtWallet.log.error("getOmniWithdrawAddressUnspent error", e);

        } finally {
            this.lock.unlock();
        }

        return omniTransaction;
    }

    @Override
    public int getConfirm(final String txId) {

        final OmniRawTransaction transaction = this.command.getOmniRawTransaction(txId);

        if (ObjectUtils.isEmpty(transaction) || !transaction.getValid()) {
            return -1;

        } else {
            final Long confirm = new Integer(transaction.getConfirmations()).longValue();
            return confirm.intValue();
        }

    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        log.info("update {} transaction confirmation begin", currency.getName());
        final int PAGE_SIZE = 500;
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final PageInfo page = new PageInfo();
        page.setPageSize(PAGE_SIZE);
        page.setSortItem("id");
        page.setSortType(PageInfo.SORT_TYPE_ASC);
        page.setStartIndex(0);
        final OmniTransactionExample example = new OmniTransactionExample();
        example.createCriteria().andSpentTxIdEqualTo(UNSPENT_TX_ID).andConfirmNumLessThan(currency.getConfirmNum()).andOmniBalanceGreaterThan(BigDecimal.ZERO);

        while (true) {
            final List<OmniTransaction> omniTransactions = this.omniTransactionService.getByPage(page, example, table);
            omniTransactions.parallelStream().forEach((omni) -> {
                omni.setCurrency(currency.getIndex());
                omni.setUpdateDate(Date.from(Instant.now()));
                final Integer confirm = this.getConfirm(omni.getTxId());
                omni.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);

                final OmniTransaction tmp = OmniTransaction.builder().id(omni.getId()).confirmNum(omni.getConfirmNum()).build();
                this.omniTransactionService.editById(tmp, table);

                final TransactionDTO dto = this.convertOmniTXToDto(omni);
                this.transactionService.saveTransaction(dto);

            });

            if (omniTransactions.size() < PAGE_SIZE) {
                break;
            }
            page.setStartIndex(page.getStartIndex() + PAGE_SIZE);
        }

        log.info("update {} transaction confirmation end", currency.getName());
    }

}
