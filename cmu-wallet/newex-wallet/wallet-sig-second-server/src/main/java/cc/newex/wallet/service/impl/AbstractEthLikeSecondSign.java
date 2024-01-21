package cc.newex.wallet.service.impl;

import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.ethereum.core.Transaction;
import org.spongycastle.util.encoders.Hex;
import sdk.bip.Bip32Node;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Slf4j
abstract public class AbstractEthLikeSecondSign implements ISignService {
    public static final String prefix = "0x";

    public static byte[] StringHexToByteArray(String x) {
        if (x.startsWith(AbstractEthLikeSecondSign.prefix)) {
            x = x.substring(2);
        }
        if (x.length() % 2 != 0) {
            x = "0" + x;
        }
        return Hex.decode(x);
    }

    @Override
    public String signTransaction(WithdrawTransaction transaction) {
        String sigStr = transaction.getSignature();
        JSONObject sigJson = JSONObject.parseObject(sigStr);
        Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        Bip32Node node = BipNodeUtil.getBipNODE(address);
        org.ethereum.crypto.ECKey ecKey = org.ethereum.crypto.ECKey.fromPrivate(node.getEcKey().getPrivKeyBytes());

        String nonce = AbstractEthLikeSecondSign.prefix + Integer.toHexString(address.getNonce());
        String gasPrice = sigJson.getBigDecimal("gasPrice").multiply(this.getCurrency().getDecimal()).toBigInteger().toString(16);
        String gas = sigJson.getBigDecimal("gas").multiply(this.getCurrency().getDecimal()).toBigInteger().toString(16);
        String value = AbstractEthLikeSecondSign.prefix + transaction.getBalance().multiply(this.getCurrency().getDecimal()).toBigInteger().toString(16);
        Transaction ethTx;
        String signResult = "";
        try {
            ethTx = new Transaction(
                    StringHexToByteArray(nonce),
                    StringHexToByteArray(gasPrice),
                    StringHexToByteArray(gas),
                    StringHexToByteArray(sigJson.getString("to")),
                    StringHexToByteArray(value),
                    StringHexToByteArray(AbstractEthLikeSecondSign.prefix));
            ethTx.sign(ecKey);
            signResult = AbstractEthLikeSecondSign.prefix + Hex.toHexString(ethTx.getEncoded());

        } catch (Throwable e) {
            AbstractEthLikeSecondSign.log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }


}
