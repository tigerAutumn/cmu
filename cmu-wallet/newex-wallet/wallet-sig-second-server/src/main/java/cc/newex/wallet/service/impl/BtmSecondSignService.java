package cc.newex.wallet.service.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import io.bytom.api.Key;
import io.bytom.api.Transaction;
import io.bytom.common.Hash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author newex-team
 * @data 2018/6/11
 */
@Component
@Slf4j
public class BtmSecondSignService implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BTM;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        log.info("{} second sign begin", currency.getName());
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);

        final List<Address> addresses = sigJson.getJSONArray("addresses").toJavaList(Address.class);
        final List<WithdrawRecord> records = sigJson.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);

        final List<UtxoTransaction> utxos = sigJson.getJSONArray("utxos").toJavaList(UtxoTransaction.class);

        final String changeAddr = sigJson.getString("changeAddress");
        final Transaction tx = new Transaction();
        //20个区块不确认，则永远不会确认
        tx.timeRange = 0;
        tx.version = 1;
        tx.size = 0;

        tx.inputs = new LinkedList<>();
        for (int i = 0; i < utxos.size(); i++) {
            final UtxoTransaction utxo = utxos.get(i);
            final String[] strs = utxo.getTxId().split(String.valueOf(StringUtil.DASH));
            final Transaction.Input input = new Transaction.Input();

            final io.bytom.api.Address address = io.bytom.api.Address.decodeSegWitAddress(addresses.get(i).getAddress());
            input.version = 1;
            input.AssetId = Hash.fromString(this.getCurrency().getContractAddress());
            input.VMVersion = 1;
            input.SourceID = Hash.fromString(strs[1]);
            input.controlProgram = address.contractProgramStr;
            input.SourcePosition = utxo.getSeq();
            input.amount = utxo.getBalance().multiply(this.getCurrency().getDecimal()).longValue();
            tx.inputs.add(input);
        }
        tx.outputs = new LinkedList<>();
        BigDecimal sentAmount = BigDecimal.ZERO;

        for (int i = 0; i < records.size(); i++) {
            final WithdrawRecord record = records.get(i);
            this.addOutput(record.getAddress(), record.getBalance(), tx);
            sentAmount = sentAmount.add(record.getBalance());
        }

        final BigDecimal walletBalance = new BigDecimal(transaction.getBalanceStr());
        //BigDecimal totalOutput = sigJson.getBigDecimal("totalAmount");
        final long feeNeu = ((1800 * utxos.size() + 126 * (records.size() + 1) + 212) * 200 / 100000 + 1) * 100000;
        final BigDecimal fee = BigDecimal.valueOf(feeNeu).divide(this.getCurrency().getDecimal());
        if (sentAmount.add(fee).compareTo(walletBalance) < 0) {
            this.addOutput(changeAddr, walletBalance.subtract(sentAmount).subtract(fee), tx);
        }
        tx.getTxId();
        for (int i = 0; i < addresses.size(); i++) {
            final Address address = addresses.get(i);
            final Key key = this.getKeyByAddress(address);
            tx.sign(key, i);
        }

        return tx.marshalText();
    }

    private void addOutput(final String address, final BigDecimal amount, final Transaction tx) {
        final Transaction.Output output = new Transaction.Output();
        output.version = 1;
        output.VMVersion = 1;
        output.amount = amount.multiply(this.getCurrency().getDecimal()).longValue();
        output.AssetId = Hash.fromString(this.getCurrency().getContractAddress());
        final io.bytom.api.Address btmAddr = io.bytom.api.Address.decodeSegWitAddress(address);
        output.controlProgram = btmAddr.contractProgramStr;
        tx.outputs.add(output);
    }

    private Key getKeyByAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node btmNode = node.getChildH(CurrencyEnum.BTM.getIndex());
        final Key key = Key.createFromSeed(btmNode.getEcKey().getPrivKeyBytes());
        final String path = "44" + StringUtil.DASH + this.getCurrency().getIndex() + StringUtil.DASH + address.getBiz() +
                StringUtil.DASH + address.getUserId() + StringUtil.DASH + address.getIndex();

        return key.DeriveXPri(path);
    }

}
