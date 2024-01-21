package cc.newex.wallet.service;

import cc.newex.wallet.config.PubKeyConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import sdk.bip.Bip32Node;
import sdk.core.TransactionBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Slf4j
abstract public class AbstractBtcLikeFirstSign implements ISignService {
    /**
     * 交易签名
     *
     * @param transaction
     */
    public Bip32Node NODE;
    @Autowired
    protected PubKeyConfig pubKeyConfig;
    @Value("${newex.wallet.masterKey}")
    String masterKey;

    @PostConstruct
    public void init() {
        this.NODE = Bip32Node.decode(this.masterKey);
    }

    protected NetworkParameters getNetworkParameters() {
        return Constants.NET_PARAMS;
    }

    @Override
    public void signTransaction(final WithdrawTransaction transaction) {

        final TransactionBuilder transactionBuilder = new TransactionBuilder(this.getNetworkParameters());
        final JSONObject signature;
        signature = JSONObject.parseObject(transaction.getSignature());
        try {

            final List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
            final List<Address> addresses = signature.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);
            final int size = utxos.size();
            String privateKey;
            for (int i = 0; i < size; i++) {
                final Address address = addresses.get(i);
                final Bip32Node node = this.getBipNODE(address);
                privateKey = node.getEcKey().getPrivateKeyEncoded(this.getNetworkParameters()).toString();
                final UtxoTransaction utxo = utxos.get(i);
                transactionBuilder.addInput(utxo.getTxId(), utxo.getSeq());
                final String redeem = this.pubKeyConfig.genScript(address);
                transactionBuilder.addSignInfo(i, privateKey, redeem);
            }

            //预估交易大小，用来计算手续费
            final long totalByte = (325 * size + 35 * (records.size()) + 15);
            final long feePerKb = signature.getLongValue("feePerKb");
            final long fee = totalByte * feePerKb;

            long sentAmount = 0;

            for (final WithdrawRecord record : records) {
                final long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                transactionBuilder.addOutput(record.getAddress(), Coin.valueOf(amount));
                //userFee = userFee + record.getFee().multiply(this.getCurrency().getDecimal()).longValue();
                sentAmount = sentAmount + amount;
            }
            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            //userFee = userFee > fee ? userFee : fee;
            //userFee = userFee > maxFee ? maxFee : userFee;
            final long changeAmount = totalAmount - sentAmount - fee;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                transactionBuilder.addOutput(changeAddr, Coin.valueOf(changeAmount));
            }
            final String firstSignTx = transactionBuilder.buildIncomplete();
            signature.put("firstSignTx", firstSignTx);
            signature.put("valid", true);


        } catch (final Throwable e) {
            AbstractBtcLikeFirstSign.log.error("signTransaction error", e);
            signature.put("valid", false);
        }
        transaction.setSignature(signature.toJSONString());
    }

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    abstract public CurrencyEnum getCurrency();

    public Bip32Node getBipNODE(final Address address) {
        final CurrencyEnum currencyEnum = CurrencyEnum.parseName(address.getCurrency());
        final Bip32Node node = this.NODE.getChild(44)
                .getChild(currencyEnum.getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
        return node;
    }

}
