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
 * @data 17/04/2018
 */
@Component
@Slf4j
public class Erc20SecondSignService extends AbstractEthLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ERC20;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final Bip32Node node = BipNodeUtil.getBipNODE(address);
        final org.ethereum.crypto.ECKey ecKey = org.ethereum.crypto.ECKey.fromPrivate(node.getEcKey().getPrivKeyBytes());

        final String nonce = AbstractEthLikeSecondSign.prefix + Integer.toHexString(address.getNonce());
        final String gasPrice = sigJson.getBigDecimal("gasPrice").multiply(CurrencyEnum.ETH.getDecimal()).toBigInteger().toString(16);
        final String gas = sigJson.getBigDecimal("gas").multiply(CurrencyEnum.ETH.getDecimal()).toBigInteger().toString(16);
        final Uint256 value = new Uint256(transaction.getBalance().multiply(currency.getDecimal()).toBigInteger());
        Transaction ethTx = null;
        String signResult = "";
        try {

            final List<Type> input = new ArrayList<>();
            input.add(new org.web3j.abi.datatypes.Address(sigJson.getString("to")));
            input.add(value);
            final List<TypeReference<?>> output = new ArrayList<>();
            final Function function = new Function("transfer", input, output);

            final String encodedFunction = FunctionEncoder.encode(function);

            ethTx = new org.ethereum.core.Transaction(
                    AbstractEthLikeSecondSign.StringHexToByteArray(nonce),
                    AbstractEthLikeSecondSign.StringHexToByteArray(gasPrice),
                    AbstractEthLikeSecondSign.StringHexToByteArray(gas),
                    AbstractEthLikeSecondSign.StringHexToByteArray(currency.getContractAddress()),
                    AbstractEthLikeSecondSign.StringHexToByteArray("0"),
                    AbstractEthLikeSecondSign.StringHexToByteArray(encodedFunction));

            ethTx.sign(ecKey);
            signResult = AbstractEthLikeSecondSign.prefix + Hex.toHexString(ethTx.getEncoded());

        } catch (final Throwable e) {
            Erc20SecondSignService.log.error("{} signTransaction error", currency, e);
        }
        return signResult;
    }
}
