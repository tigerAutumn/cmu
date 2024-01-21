package cc.newex.wallet.wallet.impl;

import NEO.Core.InvocationTransaction;
import NEO.Core.Transaction;
import NEO.Core.TransferTransaction;
import NEO.Cryptography.bip32.Deserializer;
import NEO.Cryptography.bip32.ExtendedPublicKey;
import NEO.Helper;
import NEO.IO.Serializable;
import NEO.Wallets.Account;
import NEO.Wallets.Contract;
import NEO.Wallets.Wallet;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.NeoCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cc.newex.wallet.currency.CurrencyEnum.GAS;
import static cc.newex.wallet.currency.CurrencyEnum.NEO;
import static cc.newex.wallet.utils.Constants.UNSPENT_TX_ID;

/**
 * @author newex-team
 * @data 2018/7/12
 */

@Slf4j
@Component
public class NeoWallet extends AbstractBtcLikeWallet implements IWallet {

    private ExtendedPublicKey PUB_KEY;

    @Value("${newex.neo.pubKey}")
    private String neoPubKey;

    @Value("${newex.neo.withdraw.address}")
    private String withdrawAddress;

    @Autowired
    private NeoCommand command;

    @Autowired
    Nep5Wallet nep5Wallet;

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        CurrencyEnum.NEO_ASSET.parallelStream().forEach(super::updateTXConfirmation);
        CurrencyEnum.NEP5_SET.parallelStream().forEach(this.nep5Wallet::updateTXConfirmation);
    }

    @PostConstruct
    public void init() {
        final Deserializer publicKeyDeserializer = ExtendedPublicKey.deserializer();

        if (StringUtils.isNotBlank(this.neoPubKey)) {
            this.PUB_KEY = (ExtendedPublicKey) publicKeyDeserializer.deserialize(this.neoPubKey);
        }

    }

    @Override
    public BigDecimal getDecimal() {
        return NEO.getDecimal();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return NEO;
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

        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;
        /**
         * 获取该userId在biz业务线下面已经生成了多少地址
         */
        if (!CollectionUtils.isEmpty(addressList)) {

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
        hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();
        final Account account = Account.fromPubkey(this.PUB_KEY.cKDpub(44).cKDpub(currency.getIndex()).cKDpub(biz).cKDpub
                (userId.intValue()).cKDpub(index).hdKey.key);
        final Contract contract = Contract.createSignatureContract(account.publicKey);

        final String addressStr = Wallet.toAddress(contract.scriptHash());

        final Address address = new Address();
        address.setAddress(addressStr);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(index);
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    /**
     * 获得区块链的当前最大高度
     *
     * @return
     */
    @Override
    public Long getBestHeight() {
        final long height = this.command.getBlockCount("latest");
        //由于neo获取最新区块有时会报错：Unknown block
        return height - 1;
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);

        final JSONObject block = this.command.getBlock(height, 1);
        if (ObjectUtils.isEmpty(block)) {
            log.error("findRelatedTxs error, block is null");
            return null;
        }

        final JSONArray transactions = block.getJSONArray("tx");

        final List<UtxoTransaction> utxoTransaction = transactions.parallelStream()
                .map(tx -> this.getUtxo((JSONObject) tx, height))
                .filter((utxos) -> !CollectionUtils.isEmpty(utxos))
                .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);

        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(utxoTransaction)) {

            utxoTransaction.parallelStream().forEach((record) -> {
                final CurrencyEnum assetCurrency = CurrencyEnum.parseValue(record.getCurrency());
                final ShardTable shardTable = ShardTable.builder().prefix(assetCurrency.getName()).build();
                this.utxoTransactionService.addOnDuplicateKey(record, shardTable);
            });
            dtos = utxoTransaction.parallelStream().map(this::convertUtxoToDto).collect(Collectors.toList());
        }
        log.info("{} findRelatedTxs, rawTx size:{}", currencyName, transactions.size());

        final List<TransactionDTO> nepsDtos = transactions.parallelStream()
                .map(tx -> this.nep5Wallet.findRelatedTxs((JSONObject) tx, height))
                .filter((txs) -> !CollectionUtils.isEmpty(txs))
                .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);

        if (!CollectionUtils.isEmpty(nepsDtos)) {
            dtos.addAll(nepsDtos);
        }

        log.info("{} findRelatedTxs, height:{} end", currencyName, height);
        return dtos;
    }

    //更新钱包中的币余额
    @Override
    public void updateTotalCurrencyBalance() {
        super.updateTotalCurrencyBalance();
        CurrencyEnum.NEO_ASSET.parallelStream().forEach((currency) -> {
            log.info("update {} total Balance begin", currency.getName());

            if (this.shouldUpdateTotalBalance()) {
                final BigDecimal balance = this.getBalance(currency);
                this.updateTotalCurrencyBalance(currency, balance);
                log.info("update {} total Balance:{}", currency.getName(), balance);
            }

            log.info("update {} Balance end", currency.getName());
        });

        this.nep5Wallet.updateTotalCurrencyBalance();
    }

    private List<UtxoTransaction> getUtxo(final JSONObject txJson, final Long height) {

        final JSONArray vout = txJson.getJSONArray("vout");

        final List<UtxoTransaction> utxoTransactions = vout.parallelStream()
                .map((obj) -> {
                    JSONObject output = (JSONObject) obj;
                    if (StringUtils.isBlank(output.getString("address"))) {
                        return null;
                    }

                    String contract = output.getString("asset");
                    CurrencyEnum assetCurrency = CurrencyEnum.parseContract(contract);
                    if (ObjectUtils.isEmpty(assetCurrency)) {
                        return null;
                    }
                    String txId = txJson.getString("txid");
                    if (txId.startsWith("0x")) {
                        txId = txId.substring(2);
                    }

                    this.updateWithdrawTXId(txId, assetCurrency);

                    String addressStr = output.getString("address");
                    ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                    Address address = this.addressService.getAddress(addressStr, table);

                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }
                    Long confirm = this.getBestHeight() - height + 1;
                    int seq = output.getIntValue("n");
                    BigDecimal amount = output.getBigDecimal("value");

                    UtxoTransaction utxo = UtxoTransaction.builder()
                            .balance(amount)
                            .address(addressStr)
                            .biz(address.getBiz())
                            .currency(assetCurrency.getIndex())
                            .spent((byte) 0)
                            .spentTxId(UNSPENT_TX_ID)
                            .seq((short) seq)
                            .txId(txId)
                            .blockHeight(height)
                            .confirmNum(confirm)
                            .status((byte) Constants.WAITING)
                            .createDate(Date.from(Instant.now()))
                            .updateDate(Date.from(Instant.now()))
                            .build();
                    return utxo;

                })
                .filter(utxo -> !ObjectUtils.isEmpty(utxo))
                .collect(Collectors.toList());
        return utxoTransactions;
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {

        //String result;
        try {
            //更新utxo表中的txid
            UtxoTransactionExample utxoExam = new UtxoTransactionExample();
            utxoExam.createCriteria().andSpentTxIdEqualTo(transaction.getId().toString());
            final CurrencyEnum currencyEnum = CurrencyEnum.parseValue(transaction.getCurrency());
            ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            final String raw = signature.getString("rawTransaction");
            final boolean success = this.command.sendRawTransaction(raw);

            if (success) {
                final Transaction tx;
                if (CurrencyEnum.NEP5_SET.contains(currencyEnum)) {
                    tx = Serializable.from(Helper.hexToBytes(raw), InvocationTransaction.class);
                } else {
                    tx = Serializable.from(Helper.hexToBytes(raw), TransferTransaction.class);
                    final String result = tx.hash().toString();
                    final List<UtxoTransaction> utxos = this.utxoTransactionService.getByExample(utxoExam, table);
                    utxos.parallelStream().forEach((utxo) -> {
                        this.utxoTransactionService.setSpentTxId(utxo, result, currencyEnum);
                    });
                }

                //更新占用的gas utxo
                utxoExam = new UtxoTransactionExample();
                utxoExam.createCriteria().andSpentTxIdEqualTo(transaction.getId().toString() + currencyEnum.getName());
                table = ShardTable.builder().prefix(GAS.getName()).build();
                final List<UtxoTransaction> utxos = this.utxoTransactionService.getByExample(utxoExam, table);
                final String result = tx.hash().toString();
                utxos.parallelStream().forEach((utxo) -> {
                    this.utxoTransactionService.setSpentTxId(utxo, result, GAS);
                });

                return result;

            } else {
                log.error("sendRawTransaction error");
                return "";
            }
        } catch (final Throwable e) {
            log.error("sendRawTransaction error", e);
            return "";
        }
    }

    @Override
    public boolean withdraw(final WithdrawRecord record) {
        final CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());
        if (CurrencyEnum.NEP5_SET.contains(currencyEnum)) {
            return this.nep5Wallet.withdraw(record);
        } else {
            return true;
        }
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

    @Override
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.isNotBlank(addressStr)) {
            try {
                Wallet.toScriptHash(addressStr);
                valid = true;
            } catch (final Throwable e) {
                log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

}





















