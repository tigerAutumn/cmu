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
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.dashj.DashSdk;
import org.dashj.core.Coin;
import org.dashj.core.Sha256Hash;
import org.dashj.core.Transaction;
import org.dashj.core.TransactionInput;
import org.dashj.core.Utils;
import org.dashj.crypto.TransactionSignature;
import org.dashj.script.Script;
import org.dashj.script.ScriptBuilder;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.util.List;

import static cc.newex.wallet.currency.CurrencyEnum.DASH;

/**
 * @author newex-team
 * @data 02/04/2018
 */
@Component
@Slf4j
public class DashSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return DASH;
    }

    @Override
    protected NetworkParameters getNetworkParameters() {
        return MainNetParams.get();
    }

    private static org.dashj.core.NetworkParameters getDashParameters() {
        return org.dashj.params.MainNetParams.get();
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
            final Transaction transaction1 = new Transaction(getDashParameters());

            final int size = utxos.size();
            final long fee = 400;
            long sentAmount = 0;
            //先添加输出，单签的时添加script 需要 output列表
            for (final WithdrawRecord record : records) {
                final long amount = record.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
                transaction1.addOutput(Coin.valueOf(amount), org.dashj.core.Address.fromBase58(getDashParameters(), record.getAddress()));
                sentAmount = sentAmount + amount;
            }
            final long totalAmount = transaction.getBalance().multiply(this.getCurrency().getDecimal()).longValue();

            final long changeAmount = totalAmount - sentAmount - fee;
            if (changeAmount > 0) {
                final String changeAddr = signature.getString("changeAddress");
                transaction1.addOutput(Coin.valueOf(changeAmount), org.dashj.core.Address.fromBase58(getDashParameters(), changeAddr));
            }

            for (int i = 0; i < size; i++) {
                final org.dashj.core.ECKey dashECKey = this.getEcKey(addresses, i);
                final org.dashj.script.Script script = DashSdk.createSignleSignScript(dashECKey.getPubKeyHash());
                transaction1.addInput(Sha256Hash.wrap(utxos.get(i).getTxId()), utxos.get(i).getSeq(), script);

            }

            for (int i = 0; i < transaction1.getInputs().size(); i++) {
                final org.dashj.core.ECKey dashECKey = this.getEcKey(addresses, i);
                final org.dashj.script.Script script = DashSdk.createSignleSignScript(dashECKey.getPubKeyHash());
                //sign
                final TransactionInput input = transaction1.getInput(i);
                input.setScriptSig(ScriptBuilder.createInputScript(null, dashECKey));
                final TransactionSignature transactionSignature = transaction1.calculateSignature(i, dashECKey, script, Transaction.SigHash.ALL, false);
                final int sigIndex = 0;
                final Script inputScript = script.getScriptSigWithSignature(input.getScriptSig(), transactionSignature.encodeToBitcoin(), sigIndex);
                input.setScriptSig(inputScript);
            }

            //获取交易hex
            return Utils.HEX.encode(transaction1.bitcoinSerialize());

        } catch (final Exception e) {
            log.error("dashcoin second sign error {} ", e);
            return null;
        }
    }

    private org.dashj.core.ECKey getEcKey(final List<Address> addresses, final int i) {
        final Address address = addresses.get(i);
        final Bip32Node node = this.getBipNODE(address);
        final ECKey ecKey = node.getEcKey();
        return org.dashj.core.ECKey.fromPrivate(ecKey.getPrivKeyBytes());
    }

    private Bip32Node getBipNODE(final Address address) {
        final Bip32Node dashNode = BipNodeUtil.getMainBipNODE();
        return dashNode
                .getChildH(this.getCurrency().getIndex())
                .getChild(44)
                .getChild(this.getCurrency().getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
    }

}
