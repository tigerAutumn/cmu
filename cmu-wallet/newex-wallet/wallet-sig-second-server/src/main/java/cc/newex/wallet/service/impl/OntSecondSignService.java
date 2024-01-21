package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.signature.KeyConfig;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.bip32.Deserializer;
import com.github.ontio.crypto.bip32.ExtendedPrivateKey;
import com.github.ontio.smartcontract.nativevm.Ong;
import com.github.ontio.smartcontract.nativevm.Ont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class OntSecondSignService implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ONT;
    }

    private ExtendedPrivateKey PRIVATE_KEY;

    private Account getAccountByAddress(final Address address) {
        if (ObjectUtils.isEmpty(this.PRIVATE_KEY)) {
            final Deserializer privateKeyDeserializer = ExtendedPrivateKey.deserializer();
            final String mk = KeyConfig.getValue("masterNode");
            this.PRIVATE_KEY = (ExtendedPrivateKey) privateKeyDeserializer.deserialize(mk);
        }
        final CurrencyEnum currencyEnum = CurrencyEnum.parseName(address.getCurrency());
        final ExtendedPrivateKey privateKey = this.PRIVATE_KEY
                .cKDpriv(44)
                .cKDpriv(currencyEnum.getIndex())
                .cKDpriv(address.getBiz())
                .cKDpriv(address.getUserId().intValue())
                .cKDpriv(address.getIndex());

        Account account = null;
        try {
            account = new Account(privateKey.hdKey.key, OntSdk.defaultSignScheme);
        } catch (final Exception e) {
            log.error("{} getAccountByAddress error", this.getCurrency(), e);
        }
        return account;

    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        String signResult = "";

        try {
            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);
            final Address addressSeed = sigJson.getJSONObject("address").toJavaObject(Address.class);
            final CurrencyEnum ontAssetCurrency = CurrencyEnum.parseValue(transaction.getCurrency());

            final Long gasPrice = sigJson.getLong("gasPrice");
            final Long gas = sigJson.getLong("gas");
            final BigDecimal value = transaction.getBalance().multiply(ontAssetCurrency.getDecimal());
            final Address addressPayer = sigJson.getJSONObject("payer").toJavaObject(Address.class);

            final boolean claim = sigJson.getBoolean("claim");
            final String to = sigJson.getString("to");
            final String seed = addressSeed.getAddress();
            final String payerAddress = addressPayer.getAddress();

            final Transaction tx = this.getTransaction(claim, ontAssetCurrency, seed, to, value.longValue(), payerAddress, gas, gasPrice);
            if (ObjectUtils.isEmpty(tx)) {
                log.error("{} getTransaction tx is null", this.getCurrency());
                return signResult;
            }

            final com.github.ontio.account.Account acctSeed = this.getAccountByAddress(addressSeed);
            OntSdk.addSignLocal(tx, acctSeed);
            //如果seed与payer不一致，则需要再把payer的签名加上
            if (!seed.equals(payerAddress)) {
                final com.github.ontio.account.Account acctPayer = this.getAccountByAddress(addressPayer);
                OntSdk.addSignLocal(tx, acctPayer);
            }

            signResult = tx.toHexString();
        } catch (final Exception e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    public Transaction getTransaction(final boolean claim, final CurrencyEnum currencyEnum, final String sender, final String recvAddr, final long amount, final String payer, final long gaslimit, final long gasprice) {
        try {
            //领取ong
            if (claim) {
                return Ong.makeWithdrawOngLocal(sender, recvAddr, amount, payer, gaslimit, gasprice);
            }
            if (currencyEnum == CurrencyEnum.ONT) {
                return Ont.makeTransferLocal(sender, recvAddr, amount, payer, gaslimit, gasprice);
            }
            return Ong.makeTransferLocal(sender, recvAddr, amount, payer, gaslimit, gasprice);
        } catch (final Exception e) {
            log.error("{} getTransaction error", this.getCurrency(), e);
            return null;
        }
    }
}
