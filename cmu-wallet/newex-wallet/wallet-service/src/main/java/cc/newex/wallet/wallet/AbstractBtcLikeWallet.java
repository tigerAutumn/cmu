package cc.newex.wallet.wallet;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.BtcLikeCommand;
import cc.newex.wallet.config.PubKeyConfig;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.BtcLikeBlock;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import cc.newex.wallet.pojo.rpc.ScriptPubKey;
import cc.newex.wallet.pojo.rpc.TxOutput;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.impl.UsdtWallet;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.UNSPENT_TX_ID;

/**
 * @author newex-team
 * @data 28/03/2018
 */
@Slf4j
public abstract class AbstractBtcLikeWallet extends AbstractWallet implements IWallet {

    @Autowired
    protected Constants CONSTANT;
    @Autowired
    protected PubKeyConfig pubKeyConfig;
    protected BtcLikeCommand command;
    protected Long height = 0L;
    @Autowired
    UsdtWallet usdtWallet;

    public void setCommand(final BtcLikeCommand com) {
        this.command = com;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return BigDecimal.valueOf(10000_0000L);
    }

    /**
     * 获取当前币的网络参数
     *
     * @return
     */
    public NetworkParameters getNetworkParameters() {
        throw new RuntimeException("Not Support GetNetworkParameters Method");
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

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparingInt(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
        hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();

        final String addressStr = this.pubKeyConfig.genThree_TwoAddress(currency.getIndex(), userId.intValue(), biz, index);

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
     * 解析 {@link org.bitcoinj.core.Transaction} 格式的交易为本地 UtxoTransaction 格式
     */
    public List<TransactionDTO> convertFromBjTx(final Transaction transaction, final Long height) {
        List<UtxoTransaction> txs = null;
        if (ObjectUtils.isEmpty(transaction)) {
            return new LinkedList<>();
        }
        final List<TransactionOutput> outputs = transaction.getOutputs();
        final Long bestHeight = this.getBestHeight();

        txs = outputs.parallelStream()
                .map((output) -> {
                    UtxoTransaction utxo = null;
                    try {
                        String addressStr = output.getScriptPubKey().getToAddress(Constants.NET_PARAMS).toString();
                        ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                        Address address = this.addressService.getAddress(addressStr, table);
                        if (ObjectUtils.isEmpty(address)) {
                            return utxo;
                        }

                        BigDecimal balance = new BigDecimal(output.getValue().getValue());
                        Long confirm = 0L;
                        if (height > 0L) {
                            confirm = bestHeight >= height ? bestHeight - height + 1 : 0;
                        }
                        utxo = UtxoTransaction.builder()
                                .address(addressStr)
                                .blockHeight(height)
                                .confirmNum(confirm)
                                .txId(transaction.getHashAsString())
                                .balance(balance.divide(this.getDecimal()))
                                .seq(new Integer(output.getIndex()).shortValue())
                                .spent((byte) 0)
                                .biz(address.getBiz())
                                .currency(this.getCurrency().getIndex())
                                .spentTxId(UNSPENT_TX_ID)
                                .status((byte) Constants.WAITING)
                                .createDate(Date.from(Instant.now()))
                                .updateDate(Date.from(Instant.now()))
                                .build();
                    } catch (ScriptException e) {
                        utxo = null;
                    } catch (Throwable e) {
                        log.error("convertFromBjTx map error", e);
                    }
                    return utxo;
                })
                .filter((utxo) -> utxo != null).collect(Collectors.toList());
        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(txs)) {
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.utxoTransactionService.batchAddOnDuplicateKey(txs, table);
            dtos = txs.parallelStream().map(this::convertUtxoToDto).collect(Collectors.toList());
        }
        return dtos;
    }

    public List<TransactionDTO> convertFromBjTx(final Transaction transaction) {
        return this.convertFromBjTx(transaction, 0L);
    }

    @Override
    public boolean withdraw(final WithdrawRecord record) {
        return true;
    }

    /**
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.getCurrency());
    }

    public BigDecimal getBalance(final CurrencyEnum currencyEnum) {
        final String currencyName = currencyEnum.getName();
        log.info("get {} Balance begin", currencyName);
        try {
            final UtxoTransactionExample example = new UtxoTransactionExample();
            example.createCriteria().andStatusLessThan((byte) Constants.CONFIRM).andConfirmNumGreaterThan(0L);
            final ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();
            final BigDecimal balance = this.utxoTransactionService.getTotalBalance(example, table);
            log.info("get {} Balance end", currencyName);
            return ObjectUtils.isEmpty(balance) ? BigDecimal.ZERO : balance;
        } catch (final Exception e) {
            log.error("{} getBalance error", currencyEnum, e);

        }
        return BigDecimal.ZERO;
    }

    /**
     * 扫描高度:height 区块，获得相关交易
     *
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();

        log.info("{} findRelatedTxs begin, height: {}", currencyName, height);

        final String hash = this.getBlockHash(height);
        final BtcLikeBlock block = this.command.getBlock(hash);
        if (block == null) {
            log.error("block is null, blockHash: {}", hash);
            return null;
        }

        final List<String> txidList = block.getTx();
        final List<UtxoTransaction> results = txidList.parallelStream()
                .map((txid) -> {
                    //先更新提现交易的的状态
                    this.updateWithdrawTXId(txid, this.getCurrency());
                    List<UtxoTransaction> utxos = this.getUtxo(txid, height);
                    return utxos;
                })
                .filter((utxos) -> !CollectionUtils.isEmpty(utxos))
                .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);

        List<TransactionDTO> dtos = new LinkedList<>();

        if (!CollectionUtils.isEmpty(results)) {
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.utxoTransactionService.batchAddOnDuplicateKey(results, table);
            dtos = results.parallelStream().map(this::convertUtxoToDto).collect(Collectors.toList());
        }

        log.info("{} findRelatedTxs end, height: {}", currencyName, height);

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

            final UtxoTransactionExample example = new UtxoTransactionExample();
            example.createCriteria().andSpentTxIdEqualTo(txid);
            final List<UtxoTransaction> utxoTransactions = this.utxoTransactionService.getByExample(example, table);
            if (!org.springframework.util.CollectionUtils.isEmpty(utxoTransactions)) {
                utxoTransactions.parallelStream().forEach((utxoTransaction -> utxoTransaction.setStatus((byte) Constants.CONFIRM)));
                this.utxoTransactionService.batchEdit(utxoTransactions, table);
            }

            final WithdrawRecordExample recordExample = new WithdrawRecordExample();
            recordExample.createCriteria().andTxIdEqualTo(txid);
            final List<WithdrawRecord> withdrawRecords = this.recordService.getByExample(recordExample, table);
            if (!org.springframework.util.CollectionUtils.isEmpty(withdrawRecords)) {
                withdrawRecords.parallelStream().forEach((record -> record.setStatus((byte) Constants.CONFIRM)));
                this.recordService.batchEdit(withdrawRecords, table);
            }
        }
    }

    private List<UtxoTransaction> getUtxo(final String txid, final Long height) {
        final BtcLikeRawTransaction rawTransaction = this.getRawTransaction(txid);

        final List<TxOutput> vout = rawTransaction.getVout();
        final List<UtxoTransaction> utxoTransactions = vout.parallelStream()
                .map((output) -> {

                    ScriptPubKey pubKey = output.getScriptPubKey();
                    if (CollectionUtils.isEmpty(pubKey.getAddresses())) {
                        return null;
                    }
                    String addressStr = pubKey.getAddresses().get(0);
                    ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                    Address address = this.addressService.getAddress(addressStr, table);
                    Long confirm = this.height - height + 1;

                    //如是btc的类型的交易，那么检查是不是usdt提现地址的utxo
                    if (this.getCurrency() == CurrencyEnum.BTC) {
                        this.usdtWallet.scanWithdrawAddrUtxo(output, txid, confirm);
                    }
                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }

                    UtxoTransaction utxo = UtxoTransaction.builder()
                            .balance(output.getValue())
                            .address(addressStr)
                            .biz(address.getBiz())
                            .currency(this.getCurrency().getIndex())
                            .spent((byte) 0)
                            .spentTxId(UNSPENT_TX_ID)
                            .seq(new Integer(output.getN()).shortValue())
                            .txId(txid)
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
    public Long getBestHeight() {
        this.height = this.command.getBlockCount();
        return this.height;
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {

        try {
            //更新utxo表中的txid
            final UtxoTransactionExample utxoExam = new UtxoTransactionExample();
            utxoExam.createCriteria().andSpentTxIdEqualTo(transaction.getId().toString());
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final List<UtxoTransaction> utxos = this.utxoTransactionService.getByExample(utxoExam, table);
            //final String txId = transaction.getTxId();
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            //utxo 更新spentTxId

            final String raw = signature.getString("rawTransaction");
            final String txId = this.command.sendRawTransaction(raw);
            utxos.parallelStream().forEach((utxo) -> {
                this.utxoTransactionService.setSpentTxId(utxo, txId, this.getCurrency());
            });
            return txId;
        } catch (final Throwable e) {
            log.error("sendRawTransaction error", e);
            return "";
        }
    }

    @Override
    public String getBlockHash(final Long height) {
        return this.command.getBlockHash(height);
    }

    public BtcLikeRawTransaction getRawTransaction(final String txid) {
        try {
            return this.command.getRawTransaction(txid, true);
        } catch (final JsonRpcClientException e) {
            return this.command.getRawTransaction(txid, 1);
        } catch (final Exception e) {
            log.error("getRawTransaction error: {}", txid, e);

            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e1) {
                log.error(e1.getMessage(), e1);
            }

            // 重试一次
            return this.command.getRawTransaction(txid, true);
        }

//        final String rawTx = this.command.getRawTransactionStr(txid);
//        return this.command.decodeRawTransactionStr(rawTx);
    }

    @Override
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                final org.bitcoinj.core.Address address = org.bitcoinj.core.Address.fromBase58(Constants.NET_PARAMS, addressStr);
                if (!ObjectUtils.isEmpty(address)) {
                    valid = true;
                }
            } catch (final AddressFormatException e) {
                log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

    @Override
    public int getConfirm(final String txId) {
        final BtcLikeRawTransaction transaction = this.getRawTransaction(txId);
        if (ObjectUtils.isEmpty(transaction)) {
            return -1;
        } else {
            return transaction.getConfirmations();
        }
    }

    @Override
    public String getTxId(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        final String raw = signature.getString("rawTransaction");
        final Transaction signCompleteTx = new Transaction(this.getNetworkParameters(), Utils.HEX.decode(raw));
        return signCompleteTx.getHashAsString();
    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        log.info("{} update transaction confirmation begin", this.getCurrency().getName());
        final int PAGE_SIZE = 500;
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final PageInfo page = new PageInfo();
        page.setPageSize(PAGE_SIZE);
        page.setSortItem("id");
        page.setSortType(PageInfo.SORT_TYPE_ASC);
        page.setStartIndex(0);
        final UtxoTransactionExample example = new UtxoTransactionExample();
        example.createCriteria().andSpentTxIdEqualTo(UNSPENT_TX_ID).andConfirmNumLessThan(currency.getConfirmNum());

        while (true) {
            final List<UtxoTransaction> utxos = this.utxoTransactionService.getByPage(page, example, table);
            utxos.parallelStream().forEach((utxo) -> {
                utxo.setCurrency(currency.getIndex());
                utxo.setUpdateDate(Date.from(Instant.now()));
                final Integer confirm = this.getConfirm(utxo.getTxId());
                utxo.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);
                final UtxoTransaction tmp = UtxoTransaction.builder().id(utxo.getId()).confirmNum(utxo.getConfirmNum()).build();
                this.utxoTransactionService.editById(tmp, table);
                final TransactionDTO dto = this.convertUtxoToDto(utxo);
                this.transactionService.saveTransaction(dto);

            });

            if (utxos.size() < PAGE_SIZE) {
                break;
            }
            page.setStartIndex(page.getStartIndex() + PAGE_SIZE);
        }

        log.info("update transaction confirmation end");
    }
}
