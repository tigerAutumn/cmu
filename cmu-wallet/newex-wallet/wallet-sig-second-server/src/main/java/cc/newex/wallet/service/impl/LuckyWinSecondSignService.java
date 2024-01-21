package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import com.chain.lucky.LuckWinSdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.math.BigInteger;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class LuckyWinSecondSignService extends AbstractEthLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LUCKYWIN;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = this.getBipNODE(address);
        final BigInteger value = sigJson.getBigDecimal("value").multiply(this.getCurrency().getDecimal()).toBigInteger();
        final String to = sigJson.getString("to");
        final BigInteger privateKey = node.getEcKey().getPrivKey();
        final BigInteger nonce = BigInteger.valueOf(address.getNonce());
        final BigInteger gas = sigJson.getBigDecimal("gas").toBigInteger();
        final BigInteger gasPrice = sigJson.getBigDecimal("gasPrice").toBigInteger();
        try {
            return LuckWinSdk.buildAndSign(privateKey, to, value, gas, gasPrice, nonce);
        } catch (final Exception e) {
            return null;
        }
    }

    private Bip32Node getBipNODE(final Address address) {
        final Bip32Node mainNode = BipNodeUtil.getMainBipNODE();
        return mainNode
                .getChildH(this.getCurrency().getIndex())
                .getChild(44)
                .getChild(this.getCurrency().getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
    }
}
