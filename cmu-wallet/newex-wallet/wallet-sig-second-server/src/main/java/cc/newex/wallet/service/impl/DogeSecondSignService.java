package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.doge.DogeSdk;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.script.Script;
import org.bouncycastle.util.encoders.Hex;
import org.libdohj.params.DogecoinMainNetParams;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.util.List;

import static cc.newex.wallet.currency.CurrencyEnum.DOGE;

/**
 * @author newex-team
 * @data 02/04/2018
 */
@Component
@Slf4j
public class DogeSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return DOGE;
    }

    @Override
    protected NetworkParameters getNetworkParameters() {
        return DogecoinMainNetParams.get();
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
            final Transaction transaction1 = new Transaction(this.getNetworkParameters());

            final int size = utxos.size();
            //官方钱包模式1个doge，在doge网络发生交易时，也必须是至少一个doge
            final long fee = 10000_0000;
//             预估交易大小，用来计算手续费 147是一个输入的字节数量  44是一个输出的字节数量 没有输入和输出 是10
//            long minFee = 10000_0000;
//            long totalByte = (147 * size + 44 * (records.size()) + 10);
//            long feePerKb = signature.getLongValue("feePerKb");
//            long fee = totalByte * feePerKb;
//            if (fee < minFee) {
//                fee = minFee;
//            }
//            log.info("doge coin fee={}", fee);

            long sentAmount = 0;
            //先添加输出，单签的时添加script 需要 output列表
            for (final WithdrawRecord record : records) {
                final long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                transaction1.addOutput(Coin.valueOf(amount), org.bitcoinj.core.Address.fromBase58(this.getNetworkParameters(), record.getAddress()));
                sentAmount = sentAmount + amount;
            }
            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            final long changeAmount = totalAmount - sentAmount - fee;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                transaction1.addOutput(Coin.valueOf(changeAmount), org.bitcoinj.core.Address.fromBase58(this.getNetworkParameters(), changeAddr));
            }
            //添加输入 并且带上privateKey
            for (int i = 0; i < size; i++) {
                final Address address = addresses.get(i);
                final Bip32Node node = this.getBipNODE(address);
                final ECKey ecKey = node.getEcKey();
                final TransactionOutPoint outPoint = new TransactionOutPoint(this.getNetworkParameters(), utxos.get(i).getSeq(), Sha256Hash.wrap(utxos.get(i).getTxId()));
                final Script script = DogeSdk.createSignleSignScript(ecKey.getPubKeyHash());
                transaction1.addSignedInput(outPoint, script, ecKey);
            }
            //获取交易hex
            return Hex.toHexString(transaction1.bitcoinSerialize());
        } catch (final Exception e) {
            log.error("dogecoin second sign error {} ", e);
            return null;
        }
    }

    private Bip32Node getBipNODE(final Address address) {
        final Bip32Node dogeNode = BipNodeUtil.getMainBipNODE();
        return dogeNode
                .getChildH(this.getCurrency().getIndex())
                .getChild(44)
                .getChild(this.getCurrency().getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
    }

}
