package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoincashj.core.*;
import org.bitcoincashj.crypto.TransactionSignature;
import org.bitcoincashj.params.MainNetParams;
import org.bitcoincashj.params.TestNet3Params;
import org.bitcoincashj.script.Script;
import org.bitcoincashj.script.ScriptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Component
@Slf4j
public class BchFirstSignService extends AbstractBtcLikeFirstSign implements ISignService {

    @Autowired
    Constants CONS;
    private NetworkParameters params = MainNetParams.get();

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BCH;
    }

    //? TestNet3Params.get() : MainNetParams.get();
    @Override
    @PostConstruct
    public void init() {
        this.NODE = Bip32Node.decode(this.masterKey);
        if (this.CONS.NETWORK.equals("test")) {
            this.params = TestNet3Params.get();
        } else {
            this.params = MainNetParams.get();
        }
    }


    @Override
    public void signTransaction(final WithdrawTransaction transaction) {

        //final TransactionBuilder transactionBuilder = new TransactionBuilder(Constants.NET_PARAMS);
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        try {

            final List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
            final List<Address> addresses = signature.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);
            final Transaction tx = new Transaction(this.params);
            final int size = utxos.size();

            //List<ECKey> ecKeyList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                final Address address = addresses.get(i);
                //final Bip32Node node = this.getBipNODE(address);
                //ecKeyList.add(convertECKey(node.getEcKey()));
                //privateKey = node.getEcKey().getPrivateKeyEncoded(Constants.NET_PARAMS).toString();
                final UtxoTransaction utxo = utxos.get(i);
                final String redeemStr = this.pubKeyConfig.genScript(address);
                final Script redeem = new Script(Utils.HEX.decode(redeemStr));
                final long amount = utxo.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                //tx.addInput(new Sha256Hash(utxo.getTxId()), utxo.getSeq(), redeem, Coin.valueOf(amount));
                final TransactionOutPoint outPoint = new TransactionOutPoint(this.params, utxo.getSeq(), new Sha256Hash(utxo.getTxId()));
                final TransactionInput input = new TransactionInput(this.params, tx, redeem.getProgram(), outPoint, Coin.valueOf(amount));
                tx.addInput(input);

//                final Sha256Hash hash = tx.hashForSignature4MultiSign(i, redeem, Transaction.SigHash.ALL, false, amount);
//                final ECKey key = this.convertECKey(node.getEcKey());
//                final ECKey.ECDSASignature sig = key.sign(hash);
//                final TransactionSignature inputSignature = new TransactionSignature(sig, Transaction.SigHash.ALL, false, true);
//                final List<TransactionSignature> inputSignatures = new ArrayList<>();
//                inputSignatures.add(inputSignature);
//                final Script scriptSig = ScriptBuilder.createP2SHMultiSigInputScript(inputSignatures, redeem);
//                input.setScriptSig(scriptSig);

                //tx.addInput(input);
            }
            //预估交易大小，用来计算手续费
            final long totalByte = (325 * size + 35 * (records.size()) + 15);
            final long feePerKb = signature.getLongValue("feePerKb");
            final long fee = totalByte * feePerKb;
            //final long maxFee = fee * 10;
            //long userFee = 0;

            long sentAmount = 0;

            for (final WithdrawRecord record : records) {
                final long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                final org.bitcoincashj.core.Address outputAddr = org.bitcoincashj.core.Address.fromBase58(this.params,
                        record.getAddress());
                tx.addOutput(Coin.valueOf(amount), outputAddr);
                //userFee = userFee + record.getFee().multiply(this.getCurrency().getDecimal()).longValue();
                sentAmount = sentAmount + amount;
            }

            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            //userFee = userFee > maxFee ? maxFee : userFee;
            final long changeAmount = totalAmount - sentAmount - fee;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                tx.addOutput(Coin.valueOf(changeAmount), org.bitcoincashj.core.Address.fromBase58(this.params, changeAddr));
            }
            final List<TransactionInput> txInputs = tx.getInputs();
            for (int i = 0; i < txInputs.size(); i++) {
                final TransactionInput input = txInputs.get(i);
                final UtxoTransaction utxo = utxos.get(i);

                final long amount = utxo.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

                final Script redeemScript = new Script(input.getScriptSig().getProgram());
                final Address address = addresses.get(i);
                final Bip32Node node = this.getBipNODE(address);
                final Sha256Hash hash = tx.hashForSignature4MultiSign(i, redeemScript, Transaction.SigHash.ALL, false, amount);
                final ECKey key = this.convertECKey(node.getEcKey());
                final ECKey.ECDSASignature sig = key.sign(hash);
                final TransactionSignature inputSignature = new TransactionSignature(sig, Transaction.SigHash.ALL, false, true);
                final List<TransactionSignature> inputSignatures = new ArrayList<>();
                inputSignatures.add(inputSignature);
                final Script scriptSig = ScriptBuilder.createP2SHMultiSigInputScript(inputSignatures, redeemScript);
                input.setScriptSig(scriptSig);
            }


            final String firstSignTx = Utils.HEX.encode(tx.bitcoinSerialize());
            signature.put("firstSignTx", firstSignTx);
            signature.put("valid", true);


        } catch (final Throwable e) {
            BchFirstSignService.log.error("signTransaction error", e);
            signature.put("valid", false);
        }
        transaction.setSignature(signature.toJSONString());
    }

    // 把bitcoinj 中的ECkey转变成bitcoincashj中的Eckey
    private ECKey convertECKey(final org.bitcoinj.core.ECKey key) {
        final BigInteger pri = key.getPrivKey();
        return ECKey.fromPrivate(pri);
    }
}
