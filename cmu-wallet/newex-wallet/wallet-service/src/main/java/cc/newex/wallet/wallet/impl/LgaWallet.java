package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.LgaCommand;
import cc.newex.wallet.config.PubKeyConfig;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.rpc.*;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.beidouchain.utils.BeidouchainUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.ECKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.UNSPENT_TX_ID;


/**
 * @author newex-team
 * @data 27/03/2018
 */
@Slf4j
@Component
public class LgaWallet extends AbstractBtcLikeWallet implements IWallet {

    @Autowired
    LgaCommand command;
    @Autowired
    protected PubKeyConfig pubKeyConfig;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
    }

    @Override
    public BigDecimal getDecimal() {
        return this.getCurrency().getDecimal();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LGA;
    }

    /**
     * 扫描高度:height 区块，获得相关交易
     *
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(Long height) {
        String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);
        String hash = this.getBlockHash(height);
        BtcLikeBlock block = this.command.getBlock(hash);
        if (block == null) {
            log.error("block is null ,blockHash:{}！", hash);
            return null;
        }
        List<String> txidList = block.getTx();
        List<UtxoTransaction> results = txidList.parallelStream()
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
            ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.utxoTransactionService.batchAddOnDuplicateKey(results, table);
            dtos = results.parallelStream().map(this::convertUtxoToDto).collect(Collectors.toList());
        }

        LgaWallet.log.info("{} findRelatedTxs, height:{} end", currencyName, height);
        return dtos;
    }

    private List<UtxoTransaction> getUtxo(String txid, Long height) {
        BtcLikeRawTransaction rawTransaction = this.getRawTransaction(txid);

        List<TxOutput> vout = rawTransaction.getVout();
        List<UtxoTransaction> utxoTransactions = vout.parallelStream()
                .map((output) -> {

                    ScriptPubKey pubKey = output.getScriptPubKey();
                    if (CollectionUtils.isEmpty(pubKey.getAddresses())) {
                        return null;
                    }
                    String addressStr = pubKey.getAddresses().get(0);
                    ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                    Address address = this.addressService.getAddress(addressStr, table);
                    Long confirm = this.height - height + 1;

                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }
                    BigDecimal balance = BigDecimal.ZERO;
                    List<TxOutAsset> txOutAssets = output.getAssets();
                    if (ObjectUtils.isEmpty(txOutAssets)) {
                        return null;
                    }
                    for (TxOutAsset txOutAsset :
                            txOutAssets) {
                        if (!txOutAsset.getName().equals("lgacoin") || !txOutAsset.getType().equals("transfer")) {
                            return null;
                        }

                        balance = balance.add(txOutAsset.getQty());
                    }

                    UtxoTransaction utxo = UtxoTransaction.builder()
                            .balance(balance)
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
    public Address genNewAddress(Long userId, Integer biz) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;

        //获取该userId在biz业务线下面已经生成了多少地址
        if (!CollectionUtils.isEmpty(addressList)) {

            Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }
        CurrencyEnum currency = this.getCurrency();
        ECKey ecKey = this.pubKeyConfig.NODE2.getChild(currency.getIndex()).getChild(44).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        String newAddress = BeidouchainUtils.createAddressByPub(ecKey.getPubKey());
        Address address = Address.builder()
                .address(newAddress)
                .biz(biz)
                .currency(this.getCurrency().getName())
                .userId(userId)
                .index(index).build();
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    @Override
    public boolean checkAddress(String addressStr) {
        try {
            return BeidouchainUtils.validate(addressStr);
        } catch (final Exception e) {
            LgaWallet.log.error("{} is not valid", addressStr, e);
        }
        return false;
    }

}
