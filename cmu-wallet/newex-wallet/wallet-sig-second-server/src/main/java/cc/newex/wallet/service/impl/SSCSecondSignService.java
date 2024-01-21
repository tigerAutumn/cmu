package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import com.selfsell.data.SSCPrivateKey;
import com.selfsell.data.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/5/10
 */
@Slf4j
@Component
public class SSCSecondSignService implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.SSC;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);

        final SSCPrivateKey actPrivateKey = new SSCPrivateKey(node.getEcKey().getPrivKeyBytes());
        final Transaction trx;
        final BigDecimal amount = sigJson.getBigDecimal("amount");
        final String toAddr = sigJson.getString("to");

        trx = new Transaction(actPrivateKey, amount.multiply(currency.getDecimal()).longValue(), toAddr, "cmx");

        return trx.toJSONString();
    }
}
