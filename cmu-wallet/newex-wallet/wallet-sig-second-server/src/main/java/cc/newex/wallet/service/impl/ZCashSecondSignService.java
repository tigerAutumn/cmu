package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Component;
import org.zcash.sdk.crypto.Base58;
import org.zcash.sdk.crypto.Utils;
import org.zcash.sdk.transaction.ZCashTransaction;
import org.zcash.sdk.transaction.ZCashTransactionOutput;
import sdk.bip.Bip32Node;

import java.util.Arrays;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/11/30
 */
@Component
@Slf4j
public class ZCashSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {

    public static final String PUBHSH_PREFIX = "76a914";
    public static final String PUBHSH_END = "88ac";

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ZEC;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {

        final JSONObject signature;
        signature = JSONObject.parseObject(transaction.getSignature());
        try {

            final List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
            final List<Address> addresses = signature.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);

            //创建transaction
            final ZCashTransaction zCashTransaction = new ZCashTransaction();

            final int size = utxos.size();
            //fee
            final long fee = (121 + utxos.size() * 148 + records.size() * 35) * 5;

            long sentAmount = 0;
            //先添加输出，单签的时添加script 需要 output列表
            for (final WithdrawRecord record : records) {
                final long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                zCashTransaction.addOutPut(amount, record.getAddress());
                sentAmount = sentAmount + amount;
            }
            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            final long changeAmount = totalAmount - sentAmount - fee;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                zCashTransaction.addOutPut(changeAmount, changeAddr);
            }
            //添加输入 并且带上privateKey
            for (int i = 0; i < size; i++) {
                final Address address = addresses.get(i);
                final Bip32Node node = this.getBipNODE(address);
                final ECKey ecKey = node.getEcKey();

                final byte[] fromKeyHash = Arrays.copyOfRange(Base58.decodeChecked(address.getAddress()), 2, 22);

                final ZCashTransactionOutput zCashTransactionOutput = new ZCashTransactionOutput();
                zCashTransactionOutput.address = address.getAddress();
                zCashTransactionOutput.txid = utxos.get(i).getTxId();
                zCashTransactionOutput.n = Long.valueOf(utxos.get(i).getSeq().toString());
                zCashTransactionOutput.value = utxos.get(i).getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                zCashTransactionOutput.hex = String.format("%s%s%s", PUBHSH_PREFIX, Utils.bytesToHex(fromKeyHash), PUBHSH_END);

                zCashTransaction.addSignInput(zCashTransactionOutput, ecKey.getPrivKeyBytes());
            }
            return Utils.bytesToHex(zCashTransaction.getBytes());
        } catch (final Exception e) {
            log.error("zcash second sign error {} ", e);
            return null;
        }
    }

    private Bip32Node getBipNODE(final Address address) {
        final Bip32Node bip32Node = BipNodeUtil.getMainBipNODE();
        return bip32Node
                .getChild(44)
                .getChild(this.getCurrency().getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());

    }

}
