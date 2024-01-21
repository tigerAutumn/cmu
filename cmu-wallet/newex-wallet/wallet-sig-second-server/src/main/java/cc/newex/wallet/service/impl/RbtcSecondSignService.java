package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.ethereum.core.Transaction;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import sdk.bip.Bip32Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class RbtcSecondSignService extends AbstractEthLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.RBTC;
    }

    @Override
    public String signTransaction(WithdrawTransaction transaction) {
        CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        String signStr;
        if (CurrencyEnum.RSK_TOKEN_ASSET.contains(currency)) {
            signStr = this.signRskTokenTransaction(transaction);
        } else {
            signStr = super.signTransaction(transaction);
        }
        return signStr;
    }

    private String signRskTokenTransaction(WithdrawTransaction transaction) {
        CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        String sigStr = transaction.getSignature();
        JSONObject sigJson = JSONObject.parseObject(sigStr);
        Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        Bip32Node node = BipNodeUtil.getBipNODE(address);
        org.ethereum.crypto.ECKey ecKey = org.ethereum.crypto.ECKey.fromPrivate(node.getEcKey().getPrivKeyBytes());

        String nonce = AbstractEthLikeSecondSign.prefix + Integer.toHexString(address.getNonce());
        String gasPrice = sigJson.getBigDecimal("gasPrice").multiply(CurrencyEnum.RSK_TOKEN.getDecimal()).toBigInteger().toString(16);
        String gas = sigJson.getBigDecimal("gas").multiply(this.getCurrency().getDecimal()).toBigInteger().toString(16);
        Uint256 value = new Uint256(transaction.getBalance().multiply(currency.getDecimal()).toBigInteger());
        Transaction ethTx;
        String signResult = "";
        try {

            List<Type> input = new ArrayList<>();
            input.add(new org.web3j.abi.datatypes.Address(sigJson.getString("to")));
            input.add(value);
            List<TypeReference<?>> output = new ArrayList<>();
            Function function = new Function("transfer", input, output);

            String encodedFunction = FunctionEncoder.encode(function);

            ethTx = new org.ethereum.core.Transaction(
                    AbstractEthLikeSecondSign.StringHexToByteArray(nonce),
                    AbstractEthLikeSecondSign.StringHexToByteArray(gasPrice),
                    AbstractEthLikeSecondSign.StringHexToByteArray(gas),
                    AbstractEthLikeSecondSign.StringHexToByteArray(currency.getContractAddress()),
                    AbstractEthLikeSecondSign.StringHexToByteArray("0"),
                    AbstractEthLikeSecondSign.StringHexToByteArray(encodedFunction));

            ethTx.sign(ecKey);
            signResult = AbstractEthLikeSecondSign.prefix + Hex.toHexString(ethTx.getEncoded());

        } catch (Throwable e) {
            log.error("{} signTransaction error", currency, e);
        }
        return signResult;
    }

}
