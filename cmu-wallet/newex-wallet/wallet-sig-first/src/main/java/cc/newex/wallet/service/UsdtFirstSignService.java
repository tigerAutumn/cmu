package cc.newex.wallet.service;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;
import sdk.core.TransactionBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author newex-team
 * @data 11/04/2018
 */
@Component
@Slf4j
public class UsdtFirstSignService extends AbstractBtcLikeFirstSign implements ISignService {

    //发送到用户提现地址的固定btc数量
    private static final BigDecimal FIXED_AMOUNT = new BigDecimal("0.000006");

    private final Integer USDT_PROPERTY_ID = 31;

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.USDT;
    }

    @Override
    public void signTransaction(final WithdrawTransaction transaction) {

        final TransactionBuilder transactionBuilder = new TransactionBuilder(Constants.NET_PARAMS);
        final JSONObject signature;
        signature = JSONObject.parseObject(transaction.getSignature());
        try {

            final List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
            final List<Address> addresses = signature.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);
            final int size = utxos.size();
            String privateKey;
            for (int i = 0; i < size; i++) {
                final Address address = addresses.get(i);
                final Bip32Node node = this.getBipNODE(address);
                privateKey = node.getEcKey().getPrivateKeyEncoded(Constants.NET_PARAMS).toString();
                final UtxoTransaction utxo = utxos.get(i);
                transactionBuilder.addInput(utxo.getTxId(), utxo.getSeq());
                final String redeem = this.pubKeyConfig.genScript(address);
                transactionBuilder.addSignInfo(i, privateKey, redeem);
            }


            final WithdrawRecord record = records.get(0);
            transactionBuilder.addOpReturn(this.omniPayload(record.getBalance()));

            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
            final long fixedAmount = UsdtFirstSignService.FIXED_AMOUNT.multiply(this.getCurrency().getDecimal()).longValue();
            final int fee = REDIS.getInt(Constants.WALLET_FEE + this.getCurrency().getIndex());
            final long changeAmount = totalAmount - fee - fixedAmount;
            final String changeAddr = signature.getString("changeAddress");
            if (!changeAddr.equals(record.getAddress())) {
                transactionBuilder.addOutput(changeAddr, Coin.valueOf(changeAmount));
                transactionBuilder.addOutput(record.getAddress(), Coin.valueOf(fixedAmount));
            } else {
                transactionBuilder.addOutput(changeAddr, Coin.valueOf(changeAmount + fixedAmount));
            }
            final String firstSignTx = transactionBuilder.buildIncomplete();
            signature.put("firstSignTx", firstSignTx);
            signature.put("valid", true);


        } catch (final Throwable e) {
            UsdtFirstSignService.log.error("signTransaction error", e);
            signature.put("valid", false);
        }
        transaction.setSignature(signature.toJSONString());
    }

    private String omniPayload(final BigDecimal amount) {
        final long amountLong = amount.multiply(this.getCurrency().getDecimal()).longValueExact();
        String rawTxHex = String.format("00000000%08x%016x", this.USDT_PROPERTY_ID, amountLong);
        rawTxHex = "6f6d6e69" + rawTxHex;
        return rawTxHex;
    }

}
