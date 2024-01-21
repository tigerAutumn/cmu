package cc.newex.wallet.service.impl;

import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptChunk;

import java.util.List;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Slf4j
abstract public class AbstractBtcLikeSecondSign implements ISignService {

    protected NetworkParameters getNetworkParameters() {
        return Constants.NET_PARAMS;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {

        AbstractBtcLikeSecondSign.log.info("second signTransaction begin");
        final String signature = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(signature);
        final String firstSignTx = sigJson.getString("firstSignTx");
        final List<Address> addresses = sigJson.getJSONArray("addresses").toJavaList(Address.class);
        final Transaction spendTx = new Transaction(this.getNetworkParameters(), Utils.HEX.decode(firstSignTx));

        for (int i = 0; i < spendTx.getInputs().size(); i++) {
            final TransactionInput input = spendTx.getInput(i);
            final List<ScriptChunk> chunkList = input.getScriptSig().getChunks();
            final int size = chunkList.size();
            // 多签的签名信息最后一项内容为多签脚本。内容： “0 签名1 签名2 ... 多签脚本”
            if (size < 2) {
                AbstractBtcLikeSecondSign.log.error("error signature");
                sigJson.put("valid", false);
                transaction.setSignature(sigJson.toJSONString());
                return "";
            }
            final ECKey ecKey = BipNodeUtil.getBipNODE(addresses.get(i)).getEcKey();
            final Script multiSigScript = new Script(chunkList.get(size - 1).data);
            final Sha256Hash sigHash =
                    spendTx.hashForSignature(i, multiSigScript, Transaction.SigHash.ALL, false);
            final ECKey.ECDSASignature ecSignature = ecKey.sign(sigHash);
            final TransactionSignature secondSig =
                    new TransactionSignature(ecSignature, Transaction.SigHash.ALL, false);

            final ScriptBuilder builder = new ScriptBuilder();
            // 多签chunks中数据格式： 0 [signature...][redeemScript]
            if (size == 2 + 1) {
                int pos = 0;
                // 加入之前的各个签名内容。
                while (pos < size - 1) {
                    builder.addChunk(chunkList.get(pos++));
                }
            } else {
                // bitcoinj的处理：没有的签名位置使用“0”进行占位，0 signature1 0 redeemscript
                builder.addChunk(chunkList.get(0));
                for (int pos = 1; pos < size; pos++) {
                    final ScriptChunk chunk = chunkList.get(pos);
                    if (chunk.data == null || chunk.data.length == 0) {
                        break;
                    }
                    builder.addChunk(chunk);
                }
            }
            // 加入网站私钥签名信息。
            builder.data(secondSig.encodeToBitcoin());
            // 加入多签脚本。
            builder.data(multiSigScript.getProgram());
            final Script result = builder.build();
            input.setScriptSig(result);
        }
        final String tx = Utils.HEX.encode(spendTx.bitcoinSerialize());

        AbstractBtcLikeSecondSign.log.info("second signTransaction end");
        return tx;
    }

//    @Override
//    public String getTxId(final String rawTx) {
//        final Transaction signCompleteTx = new Transaction(this.getNetworkParameters(), Utils.HEX.decode(rawTx));
//        return signCompleteTx.getHashAsString();
//    }

//    public Bip32Node getBipNODE(final Address address) {
//        if (ObjectUtils.isEmpty(this.NODE)) {
//            final String mk = KeyConfig.getValue("masterNode");
//            //final String dk = SecretConfig.decryptKey(mk);
//            this.NODE = Bip32Node.decode(mk);
//        }
//        final Bip32Node node = this.NODE.getChild(44)
//                .getChild(this.getCurrency().getIndex())
//                .getChild(address.getBiz())
//                .getChild(address.getUserId().intValue())
//                .getChild(address.getIndex());
//        return node;
//    }
}
