package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import com.ripple.core.coretypes.AccountID;
import com.ripple.core.coretypes.Amount;
import com.ripple.core.coretypes.uint.UInt32;
import com.ripple.core.types.known.tx.signed.SignedTransaction;
import com.ripple.core.types.known.tx.txns.Payment;
import com.ripple.crypto.ecdsa.Seed;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.ripple.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;
import java.security.Security;

/**
 * @author newex-team
 * @data 2018/6/11
 */
@Component
@Slf4j
public class XrpSecondSignService implements ISignService {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XRP;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());

        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);
        final ECKey ecKey = node.getEcKey();

        final Seed seed = new Seed(Seed.passPhraseToSeedBytes(ecKey.getPrivateKeyAsHex()));
        final Payment payment = new Payment();
        payment.as(AccountID.Account, address.getAddress());

        final String toAddr = sigJson.getString("to");
        final String[] addressAndTag = toAddr.split(":");
        if (addressAndTag.length > 1) {
            payment.as(UInt32.DestinationTag, addressAndTag[1]);
        }
        payment.as(AccountID.Destination, addressAndTag[0]);
        final BigDecimal amount = sigJson.getBigDecimal("amount").multiply(currency.getDecimal());
        payment.as(Amount.Amount, String.valueOf(amount.longValue()));

        payment.as(UInt32.Sequence, address.getNonce().longValue());

        payment.as(Amount.Fee, String.valueOf(sigJson.getIntValue("fee")));
        final long lastLedgerSequence = sigJson.getLongValue("lastLedgerSequence");
        payment.as(UInt32.LastLedgerSequence, lastLedgerSequence);

        final SignedTransaction signed = payment.sign(seed.toString());
        return signed.tx_blob;
    }
}
