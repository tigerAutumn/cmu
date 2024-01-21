package cc.newex.wallet.wallet.impl;


import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.baicwallet.model.Transaction;
import cc.newex.wallet.command.BaicCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.BaicABIReq;
import cc.newex.wallet.pojo.rpc.BaicPushTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static cc.newex.wallet.utils.Constants.BAIC_ACTION_NAME_TRANSFER;

/**
 * @author newex-team
 * @data 2019/1/11
 */
@Slf4j
@Component
public class BaicWallet extends EosWallet implements IWallet {

    @Value("${newex.baic.server}")
    private String baicServer;

    @Value("${newex.baic.withdraw.address}")
    private String withdrawAddress;

    @Autowired
    BaicCommand command;

    @PostConstruct
    public void init() {
        this.rpc = new Rpc(this.baicServer);
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }


    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BAIC;
    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.BAIC.getDecimal();
    }

    /**
     * 广播签名交易
     * @param transaction
     * @return
     */
    @Override
    public String sendRawTransaction(WithdrawTransaction transaction){
        String result = "";
        try {
            JSONObject signature = JSONObject.parseObject(transaction.getSignature());

            final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
            Address address = signature.getJSONObject("address").toJavaObject(Address.class);
            String from = address.getAddress();

            String rawTransaction = signature.getString("rawTransaction");
            String action_data = signature.getString("abiJsonToBinData");
            String headBlockTime = signature.getString("headBlockTime");
            Long lastIrreversibleBlockNum = signature.getLong("lastIrreversibleBlockNum");
            Long refBlockPrefix = signature.getLong("refBlockPrefix");
            String action_name = signature.getString("action_name");


            String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("sendRawTransaction err,currency {},account is null", currency.getName());
                return "";
            }

            Transaction transaction1 = Transaction.buildTransaction(account, action_name, action_data, from,
                    lastIrreversibleBlockNum, refBlockPrefix,headBlockTime);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(rawTransaction);
            BaicPushTransaction pushTransaction = BaicPushTransaction.builder()
                    .signatures(jsonArray)
                    .compression("none")
                    .packed_context_free_data(StringUtil.EMPTY)
                    .packed_trx(new String(Hex.encode(transaction1.pack())))
                    .build();

            JSONObject jsonObject = this.command.pushTransaction(pushTransaction);
            log.info("{} sendRawTransaction log，result :{}", currency.getName(), jsonObject);
            if (jsonObject == null) {
                log.error("{} sendRawTransaction error，result :{}", currency.getName(), jsonObject);
                return result;
            }
            result = jsonObject.containsKey("transaction_id") ? jsonObject.getString("transaction_id"): "";

        } catch (Throwable e) {
            log.error("sendRawTransaction error{}", e);
            result = "";
        }
        return result;
    }


    /**
     * 构建交易
     * @param record
     * @return
     */
    @Override
    protected  WithdrawTransaction buildTransaction(WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        //获取根账户地址
        final ShardTable addressTable = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final Address address = this.addressService.getAddress(this.getWithdrawAddress(), addressTable);

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", this.getWithdrawAddress());
        signature.put("to", record.getAddress());

        DecimalFormat fnum = new DecimalFormat("##0.000000000000000000");
        fnum.setRoundingMode(RoundingMode.CEILING);
        String amount = fnum.format(record.getBalance());
        signature.put("amount", amount);
        BigDecimal amountDB = new BigDecimal(amount);
        record.setBalance(amountDB);

        ChainInfo info = this.command.getChainInfo();
        if (info == null){
            log.error("BAICWallet buildTransaction getChainInfo error");
            return null;
        }
        Block block = this.rpc.getBlock(info.getLastIrreversibleBlockNum().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"));
        signature.put("headBlockTime", dateFormat.format(info.getHeadBlockTime().getTime()));
        signature.put("lastIrreversibleBlockNum", info.getLastIrreversibleBlockNum());
        signature.put("refBlockPrefix", block.getRefBlockPrefix());
        signature.put("chainId", info.getChainId());

        // json to abi
        JSONArray param = new JSONArray();
        param.add(this.getWithdrawAddress());
        String []addStr = record.getAddress().split(":");
        if (addStr.length != 2){
            log.error("{} signTransaction error,addressAndTag.Len !=2 ", currency);
            return null;
        }
        String to = addStr[0];
        String memo = addStr[1];
        param.add(to);
        param.add(amountDB.toString() + " " + this.getSymbol(this.getCurrency().getContractAddress()));
        param.add(memo);

        BaicABIReq abiReq = BaicABIReq.builder()
                .code(this.getAccount(this.getCurrency().getContractAddress()))
                .action(BAIC_ACTION_NAME_TRANSFER)
                .args(param)
                .build();
        JSONObject jsonObject = this.command.abiJsonToBin(abiReq);
        if (jsonObject == null){
            log.error("BAICWallet buildTransaction abiJsonToBin error");
            return null;
        }
        if (!jsonObject.containsKey("binargs") || jsonObject.getString("binargs").equals("")){
            log.error("BAICWallet buildTransaction abiJsonToBin error");
            return null;
        }

        signature.put("abiJsonToBinData", jsonObject.getString("binargs"));
        signature.put("action_name", BAIC_ACTION_NAME_TRANSFER);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(amountDB)
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId("transfer")
                .signature(signature.toJSONString())
                .build();

        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);
        log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }


    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        super.updateTXConfirmation(this.getCurrency());
        if (this.getCurrency() == CurrencyEnum.BAIC) {
            // TODO 基于BAIC发布的TOKEN
//            CurrencyEnum.BAIC_ASSET.parallelStream().forEach(super::updateTXConfirmation);
        }
    }

    /**
     * 更新钱包中的币余额
     */
    @Override
    public void updateTotalCurrencyBalance() {
        log.info("updateTotalCurrencyBalance {} begin", this.getCurrency().getName());
        super.updateTotalCurrencyBalance();
        if (this.getCurrency() == CurrencyEnum.BAIC) {
            // TODO 基于BAIC发布的TOKEN
//            CurrencyEnum.BAIC_ASSET.parallelStream().forEach((currency) -> {
//                log.info("update {} total Balance begin", currency.getName());
//                BigDecimal balance = this.getBalance(this.getWithdrawAddress(), currency);
//                this.updateTotalCurrencyBalance(currency, balance);
//                log.info("update {} total Balance end", currency.getName());
//
//            });
        }
        log.info("updateTotalCurrencyBalance {} end", this.getCurrency().getName());
    }
}
