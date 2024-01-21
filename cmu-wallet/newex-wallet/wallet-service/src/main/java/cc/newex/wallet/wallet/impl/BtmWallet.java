package cc.newex.wallet.wallet.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.BtmCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import com.alibaba.fastjson.JSONObject;
import io.bytom.api.Block;
import io.bytom.api.Key;
import io.bytom.api.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.UNSPENT_TX_ID;

/**
 * @author newex-team
 * @data 2018/8/7
 */

@Slf4j
@Component
public class BtmWallet extends AbstractBtcLikeWallet {

    @Value("${newex.btm.pubKey}")
    private String pubKey;

    private Key PUB_KEY;
    @Autowired
    BtmCommand command;

    private final String SUCCESS = "success";

    @PostConstruct
    public void init() {
        this.PUB_KEY = Key.createFromPubkey(Base58.decode(this.pubKey));
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return this.getCurrency().getDecimal();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BTM;
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
        String path = "44" + StringUtil.DASH + currency.getIndex() + StringUtil.DASH + biz + StringUtil.DASH + userId + StringUtil.DASH + index;

        Key key = this.PUB_KEY.DeriveXPub(path);

        final String addressStr = key.toAddress();

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

    @Override
    public Long getBestHeight() {
        JSONObject res = this.command.getBlockCount();
        if (res.getString("status").equals(this.SUCCESS)) {
            this.height = res.getJSONObject("data").getLong("block_count");
        } else {
            log.error("get btm best height error");
            this.height = 0L;
        }
        return this.height;
    }

    /**
     * 扫描高度:height 区块，获得相关交易
     *
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);

        JSONObject param = new JSONObject();
        param.put("block_height", height);
        JSONObject res = this.command.getBlock(param);
        if (res == null) {
            log.error("block is null ,blockHeight:{}！", height);
            return null;
        }
        String status = res.getString("status");
        if (!status.equals(this.SUCCESS)) {
            log.error("block is null ,blockHeight:{}！", height);
            return null;
        }
        Block block = res.getJSONObject("data").toJavaObject(Block.class);

        final List<UtxoTransaction> results = block.transactions.parallelStream()
                .map((tx) -> {
                    //先更新提现交易的的状态
                    this.updateWithdrawTXId(tx.id, this.getCurrency());
                    List<UtxoTransaction> utxos = this.getUtxo(tx, block.height);
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

        log.info("{} findRelatedTxs, height:{} end", currencyName, height);
        return dtos;
    }

    @Override
    public TransactionDTO convertUtxoToDto(final UtxoTransaction utxo) {
        final StringBuilder txidBuilder = new StringBuilder();
        //btm 的txid字段包含了txid和muxId
        String[] strs = utxo.getTxId().split(String.valueOf(StringUtil.DASH));
        //utxo 的交易txid不能唯一标示一笔充值, 所以把`txid-seq`设置为唯一标识。方便统一处理
        txidBuilder.append(strs[0]).append(StringUtil.DASH).append(utxo.getSeq());
        final TransactionDTO dto = TransactionDTO.builder()
                .address(utxo.getAddress())
                .balance(utxo.getBalance())
                .blockHeight(utxo.getBlockHeight())
                .confirmNum(utxo.getConfirmNum())
                .txId(txidBuilder.toString())
                .currency(utxo.getCurrency())
                .biz(utxo.getBiz())
                .build();
        return dto;
    }


    private List<UtxoTransaction> getUtxo(Transaction tx, final Long height) {

        if (tx.statusFail) {
            return null;
        }

        final List<Transaction.Output> vout = tx.outputs;

        final List<UtxoTransaction> utxoTransactions = vout.parallelStream()
                .map((output) -> {
                    if (!output.assetId.equals(this.getCurrency().getContractAddress())) {
                        return null;
                    }
                    if (!output.type.equals("control")) {
                        return null;
                    }

                    ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                    Address address = this.addressService.getAddress(output.address, table);
                    Long confirm = this.height - height + 1;

                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }
                    BigDecimal amount = BigDecimal.valueOf(output.amount);

                    UtxoTransaction utxo = UtxoTransaction.builder()
                            .balance(amount.divide(this.getDecimal()))
                            .address(output.address)
                            .biz(address.getBiz())
                            .currency(this.getCurrency().getIndex())
                            .spent((byte) 0)
                            .spentTxId(UNSPENT_TX_ID)
                            .seq((short) output.position)
                            //把txid和muxId用 "-"拼接，因为签名时需要muxId
                            .txId(tx.id + StringUtil.DASH + tx.muxId)
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

        try {
            //更新utxo表中的txid
            final UtxoTransactionExample utxoExam = new UtxoTransactionExample();
            utxoExam.createCriteria().andSpentTxIdEqualTo(transaction.getId().toString());
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final List<UtxoTransaction> utxos = this.utxoTransactionService.getByExample(utxoExam, table);

            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            //utxo 更新spentTxId

            final String raw = signature.getString("rawTransaction");
            JSONObject param = new JSONObject();
            param.put("raw_transaction", raw);
            JSONObject res = this.command.sendRawTransaction(param);
            if (!res.getString("status").equals(this.SUCCESS)) {
                return "";
            }
            String txId = res.getJSONObject("data").getString("tx_id");
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
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                io.bytom.api.Address address = io.bytom.api.Address.decodeSegWitAddress(addressStr);
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
        UtxoTransaction utxoTransaction = this.utxoTransactionService.getByTxid(txId, this.getCurrency());
        JSONObject param = new JSONObject();
        param.put("block_height", utxoTransaction.getBlockHeight());
        JSONObject res = this.command.getBlock(param);
        if (!res.getString("status").equals(this.SUCCESS)) {
            return 0;
        }
        Block block = res.getJSONObject("data").toJavaObject(Block.class);
        boolean success = block.transactions.parallelStream().anyMatch((tx) -> {
            String id = tx.id + StringUtil.DASH + tx.muxId;
            return id.equals(txId) && !tx.statusFail;
        });
        if (success) {
            Long bestHeight = this.getBestHeight();
            Long confirm = bestHeight - block.height + 1;
            return confirm.intValue();

        } else {
            return -1;
        }
    }


}
