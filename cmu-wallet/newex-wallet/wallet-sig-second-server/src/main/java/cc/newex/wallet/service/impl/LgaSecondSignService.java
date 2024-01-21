package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.signature.KeyConfig;
import com.alibaba.fastjson.JSONObject;
import com.beidouchain.crypto.ECKeyPairs;
import com.beidouchain.transaction.SignedInput;
import com.beidouchain.transaction.Transaction;
import com.beidouchain.utils.BeidouchainUtils;
import com.beidouchain.utils.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Component
@Slf4j
public class LgaSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LGA;
    }

    @Override
    public String signTransaction(WithdrawTransaction withdrawTransaction) {
        JSONObject signature;
        signature = JSONObject.parseObject(withdrawTransaction.getSignature());
        try {

            List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
            List<Address> addresses = signature.getJSONArray("addresses").toJavaList(Address.class);
            List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);

            int size = utxos.size();

            //添加输入 并且带上privateKey
            List<SignedInput> inputs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Address address = addresses.get(i);
                Bip32Node node = this.getBipNODE(address);
                ECKey ecKey = node.getEcKey();
                ECKeyPairs keyPairs = new ECKeyPairs(ecKey.getPrivKey(), true);

                UtxoTransaction utxoTransaction = utxos.get(i);
                SignedInput signedInput = new SignedInput(keyPairs, utxoTransaction.getTxId(), utxoTransaction.getSeq(), utxoTransaction.getBalance().multiply(this.getCurrency().getDecimal()).toBigInteger(), utxoTransaction.getAddress());
                inputs.add(signedInput);
            }

            //添加输出
            List<com.beidouchain.transaction.TransactionOutput> outputs = new ArrayList<>();
            long sentAmount = 0;
            for (WithdrawRecord record : records) {

                long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                com.beidouchain.transaction.TransactionOutput transactionOutput = new com.beidouchain.transaction.TransactionOutput(record.getAddress(), BigInteger.valueOf(amount));
                outputs.add(transactionOutput);
                sentAmount = sentAmount + amount;
            }
            long totalAmount = withdrawTransaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            long changeAmount = totalAmount - sentAmount;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                com.beidouchain.transaction.TransactionOutput transactionOutput = new com.beidouchain.transaction.TransactionOutput(changeAddr, BigInteger.valueOf(changeAmount));
                outputs.add(transactionOutput);
            }

            Transaction transaction = new Transaction(inputs, outputs);
            //获取交易hex
            return Hex.toHexString(transaction.getBytes());
        } catch (Exception e) {
            log.error("dogecoin second sign error {} ", e);
            return null;
        }
    }

    private Bip32Node getBipNODE(Address address) {
        Bip32Node lgaNode = BipNodeUtil.getMainBipNODE();
        return lgaNode
                .getChild(this.getCurrency().getIndex())
                .getChild(44)
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
    }

}
