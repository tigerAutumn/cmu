package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoincashj.core.*;
import org.bitcoincashj.crypto.TransactionSignature;
import org.bitcoincashj.params.MainNetParams;
import org.bitcoincashj.params.TestNet3Params;
import org.bitcoincashj.script.Script;
import org.bitcoincashj.script.ScriptBuilder;
import org.bitcoincashj.script.ScriptChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Component
@Slf4j
public class BchSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {

    @Autowired
    Constants CONS;
    private NetworkParameters params = MainNetParams.get();

    @PostConstruct
    public void init() {
        if (this.CONS.NETWORK.equals("test")) {
            this.params = TestNet3Params.get();
        } else {
            this.params = MainNetParams.get();
        }
    }


    @Override
    public String signTransaction(final WithdrawTransaction transaction) {

        BchSecondSignService.log.info("{} second signTransaction begin", this.getCurrency().getName());
        final String signature = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(signature);
        final String firstSignTx = sigJson.getString("firstSignTx");
        final List<Address> addresses = sigJson.getJSONArray("addresses").toJavaList(Address.class);
        final List<UtxoTransaction> utxos = sigJson.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
        final Transaction spendTx = new Transaction(this.params, Utils.HEX.decode(firstSignTx));
        for (int i = 0; i < spendTx.getInputs().size(); i++) {
            final TransactionInput input = spendTx.getInput(i);

            final List<ScriptChunk> chunkList = input.getScriptSig().getChunks();
            final int size = chunkList.size();
            // 多签的签名信息最后一项内容为多签脚本。内容： “0 签名1 签名2 ... 多签脚本”
            if (size < 2) {
                BchSecondSignService.log.error("error signature");
                sigJson.put("valid", false);
                transaction.setSignature(sigJson.toJSONString());
                return "";
            }
            final Script multiSigScript = new Script(chunkList.get(size - 1).data);
            final ECKey ecKey = this.convertECKey(BipNodeUtil.getBipNODE(addresses.get(i)).getEcKey());
            final UtxoTransaction utxo = utxos.get(i);
            final long amount = utxo.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
            final Sha256Hash hash = spendTx.hashForSignature4MultiSign(i, multiSigScript, Transaction.SigHash.ALL, false, amount);
            final ECKey.ECDSASignature sig = ecKey.sign(hash);
            final TransactionSignature secondSig = new TransactionSignature(sig, Transaction.SigHash.ALL, false, true);
            final ScriptBuilder builder = new ScriptBuilder();
            if (size == 2 + 1) {
                int pos = 0;
                while (pos < size - 1) {
                    builder.addChunk(chunkList.get(pos++));
                }
            } else {
                builder.addChunk(chunkList.get(0));
                for (int pos = 1; pos < size; pos++) {
                    final ScriptChunk chunk = chunkList.get(pos);
                    if (chunk.data == null || chunk.data.length == 0) {
                        break;
                    }
                    builder.addChunk(chunk);
                }
            }
            builder.data(secondSig.encodeToBitcoin());
            builder.data(multiSigScript.getProgram());
            final Script result = builder.build();
            input.setScriptSig(result);
        }

        final String tx = Utils.HEX.encode(spendTx.bitcoinSerialize());

        BchSecondSignService.log.info("{} second signTransaction end", this.getCurrency().getName());

        return tx;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BCH;
    }

    // 把bitcoinj 中的ECkey转变成bitcoincashj中的Eckey
    private ECKey convertECKey(final org.bitcoinj.core.ECKey key) {
        final BigInteger pri = key.getPrivKey();
        return ECKey.fromPrivate(pri);
    }
}
