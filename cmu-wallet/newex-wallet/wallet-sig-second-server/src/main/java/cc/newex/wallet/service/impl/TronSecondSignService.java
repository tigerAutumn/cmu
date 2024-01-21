package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.tron.TronWalletApi;
import org.tron.protos.Protocol;
import org.tron.wallet.util.ByteArray;
import sdk.bip.Bip32Node;

import java.io.IOException;
import java.math.BigDecimal;

import static cc.newex.wallet.currency.CurrencyEnum.TRX;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class TronSecondSignService implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return TRX;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final JSONObject sigJson = JSONObject.parseObject(transaction.getSignature());
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final String from = sigJson.getString("from");
        final String toAddr = sigJson.getString("to");
        final BigDecimal value = transaction.getBalance();
        try {
            final Protocol.Transaction waitSignTx = TronWalletApi.createTransaction(TronWalletApi.decodeFromBase58Check(from), TronWalletApi.decodeFromBase58Check(toAddr),
                    value.multiply(currency.getDecimal()).longValue(),
                    sigJson.getString("block"));
            final Protocol.Transaction signedTxObject = TronWalletApi.signTransaction2Object(waitSignTx.toByteArray(), this.getKeyByAddress(address));
            if (!ObjectUtils.isEmpty(signedTxObject)) {
                return ByteArray.toHexString(signedTxObject.toByteArray());
            }
            return "";
        } catch (final IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private byte[] getKeyByAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node tronNode = node.getChildH(TRX.getIndex())
                .getChild(44)
                .getChild(this.getCurrency().getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
        final ECKey tronKey = tronNode.getEcKey();
        return tronKey.getPrivKeyBytes();
    }

}
