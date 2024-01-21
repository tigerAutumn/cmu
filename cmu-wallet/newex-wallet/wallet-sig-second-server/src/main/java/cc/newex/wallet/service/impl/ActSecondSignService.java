package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.achain.data.ACTPrivateKey;
import com.achain.data.Transaction;
import com.alibaba.fastjson.JSONObject;
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
public class ActSecondSignService implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ACT;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);

        final ACTPrivateKey actPrivateKey = new ACTPrivateKey(node.getEcKey().getPrivKeyBytes());
        final Transaction trx;
        final BigDecimal amount = sigJson.getBigDecimal("amount");
        final String toAddr = sigJson.getString("to");
        if (CurrencyEnum.isATP(currency)) {
            trx = new Transaction(
                    actPrivateKey,
                    currency.getContractAddress(),
                    toAddr,
                    amount.stripTrailingZeros().toString(),
                    1000L
            );
        } else {
            trx = new Transaction(actPrivateKey, amount.multiply(currency.getDecimal()).longValue(), toAddr, "cmx");
        }

        return trx.toJSONString();
    }
}
