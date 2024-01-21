package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import com.ofbank.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.ethereum.util.ByteUtil;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class OfSecondSignService extends AbstractEthLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.OF;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        log.info("{} second sign begin", currency.getName());
        if (CurrencyEnum.ORC_TOKEN_SET.contains(currency)) {
            return this.signOrcTokenTransaction(transaction);
        } else {
            return this.signOfTransaction(transaction);
        }
    }

    public String signOfTransaction(final WithdrawTransaction transaction) {
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);
        final BigInteger nonce = BigInteger.valueOf(address.getNonce().longValue());
        final BigInteger gasPrice = sigJson.getBigInteger("gasPrice");
        final BigInteger gas = sigJson.getBigInteger("gas");
        final BigDecimal value = transaction.getBalance();

        String signResult = "";
        try {
            signResult = Wallet.SignTransaction(nonce, gasPrice, gas, ByteUtil.hexStringToBytes(sigJson.getString("to")), value, ByteUtil.EMPTY_BYTE_ARRAY, node.getEcKey().getPrivKeyBytes());
        } catch (final Throwable e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    public String signOrcTokenTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);
        final BigInteger nonce = BigInteger.valueOf(address.getNonce().longValue());
        final BigInteger gasPrice = sigJson.getBigInteger("gasPrice");
        final BigInteger gas = sigJson.getBigInteger("gas");
        final BigDecimal amount = transaction.getBalance().multiply(currency.getDecimal());

        String signResult = "";
        try {
            final String zeros = "0000000000000000000000000000000000000000000000000000000000000000";
            String value = ByteUtil.toHexString(ByteUtil.bigIntegerToBytes(amount.toBigInteger()));
            value = zeros.substring(value.length()) + value;

            String data = "0xa9059cbb00000000000000";
            String receiver = sigJson.getString("to");
            if (receiver.startsWith("0x")) {
                receiver = receiver.substring(2);
            }
            data = data + receiver;
            data = data + value;

            final com.ofbank.Transaction ofTx = new com.ofbank.Transaction(ByteUtil.bigIntegerToBytes(nonce), ByteUtil
                    .bigIntegerToBytes(gasPrice), ByteUtil.bigIntegerToBytes(gas), ByteUtil.hexStringToBytes(currency.getContractAddress()),
                    ByteUtil.bigIntegerToBytes(BigInteger.ZERO), ByteUtil.hexStringToBytes(data));
            ofTx.sign(node.getEcKey().getPrivKeyBytes());
            signResult = "0x" + ByteUtil.toHexString(ofTx.getEncoded());

        } catch (final Throwable e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }
}
