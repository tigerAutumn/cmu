package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.ripple.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import org.stellar.sdk.Account;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.CreateAccountOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.MemoText;
import org.stellar.sdk.Network;
import org.stellar.sdk.Operation;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.xdr.OperationType;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;
import java.security.Security;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Component
@Slf4j
public class XlmSecondSignService implements ISignService {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XLM;
    }

    static {
        Network.usePublicNetwork();
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        String signResult = "";
        try {
            final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());

            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);
            final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);

            final String toAddr = sigJson.getString("to");
            final String[] addressAndMemo = toAddr.split(":");
            if (addressAndMemo.length != 2) {
                log.error("{} signTransaction error,addressAndMemo.Len !=2 ", currency);
                return null;
            }
            final String to = addressAndMemo[0];
            final String memo = addressAndMemo[1];

            final BigDecimal amount = transaction.getBalance();

            final Operation op;
            final int xlmType = sigJson.getInteger("xlmType");
            if (xlmType == OperationType.PAYMENT.getValue()) {
                final PaymentOperation operation = new PaymentOperation.Builder(KeyPair.fromAccountId(to),
                        new AssetTypeNative(), amount.toString()).build();
                op = operation;
            } else {
                final CreateAccountOperation createAccountOperation = new CreateAccountOperation.Builder(
                        KeyPair.fromAccountId(to), amount.toString()).build();
                op = createAccountOperation;
            }

            final Long sequenceNumber = sigJson.getLong("sequenceNumber");

            final Account account = getAccountByAddress(address, sequenceNumber);

            final Transaction.Builder builder = new org.stellar.sdk.Transaction.Builder(account);
            final Transaction tx = builder.addOperation(op).addMemo(new MemoText(memo))
                    .build();
            final String seed = new String(account.getKeypair().getSecretSeed());
            tx.sign(KeyPair.fromSecretSeed(seed));
            signResult = tx.toEnvelopeXdrBase64();
        } catch (final Exception e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    private static Account getAccountByAddress(final Address address, final Long sequenceNumber) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node eosAccountNode = node.getChildH(CurrencyEnum.parseName(address.getCurrency()).getIndex())
                .getChild(44)
                //biz
                .getChild(address.getBiz())
                //userid
                .getChild(address.getUserId().intValue())
                //index
                .getChild(address.getIndex());
        final ECKey ecKey = eosAccountNode.getEcKey();
        final KeyPair stellarKeyPair = KeyPair.fromSecretSeed(ecKey.getPrivKeyBytes());
        final Account account = new Account(stellarKeyPair, sequenceNumber);
        return account;
    }
}
