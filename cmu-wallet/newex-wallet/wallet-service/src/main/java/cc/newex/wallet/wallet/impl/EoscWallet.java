package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import client.crypto.model.chain.PackedTransaction;
import client.domain.response.chain.AbiJsonToBin;
import client.impl.EosApiClientFactory;
import client.impl.EosApiRestClient;
import client.util.TransactionUtils;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.TableRows;
import io.eblock.eos4j.api.vo.TableRowsReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EoscWallet extends EosWallet implements IWallet {

    EosApiRestClient eosApiRestClient;

    @Value("${newex.eosc.server}")
    private String eoscServer;

    @Value("${newex.eosc.withdraw.address}")
    private String withdrawAddress;

    @Override
    @PostConstruct
    public void init() {
        this.rpc = new Rpc(this.eoscServer);
        this.eosApiRestClient = EosApiClientFactory.newInstance(this.eoscServer).newRestClient();
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.EOSC;
    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.EOSC.getDecimal();
    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        try {
            final String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("getBalance err,currency {},account is null", currency.getName());
                return BigDecimal.ZERO;
            }
            final TableRowsReq tableRowsReq = new TableRowsReq(account, account, "accounts", address, true, 1);
            final TableRows currencyBalance = this.rpc.getTableRows(tableRowsReq);
            if (ObjectUtils.isEmpty(currencyBalance) || ObjectUtils.isEmpty(currencyBalance.getRows())
                    || currencyBalance.getRows().size() != 1) {
                return BigDecimal.ZERO;
            }
            final String value = currencyBalance.getRows().get(0).get("available").toString();
            return this.quantity2balance(value);
        } catch (final Throwable e) {
            log.error("get currency:{} balance error", this.getCurrency(), e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        final ShardTable addressTable = ShardTable.builder().prefix(currency.getName()).build();

        final Address address = this.addressService.getAddress(this.withdrawAddress, addressTable);

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", this.withdrawAddress);
        signature.put("to", record.getAddress());
        final DecimalFormat fnum = new DecimalFormat("##0.0000");
        fnum.setRoundingMode(RoundingMode.CEILING);
        final String amount = fnum.format(record.getBalance());
        final BigDecimal amountDB = new BigDecimal(amount);
        signature.put("amount", amount);
        record.setBalance(amountDB);

        final client.domain.response.chain.ChainInfo chainInfo = this.eosApiRestClient.getChainInfo();

        final String headBlockTime = chainInfo.getHeadBlockTime();
        final String headBlockId = chainInfo.getHeadBlockId();
        final String chainId = chainInfo.getChainId();
        final String action = "transfer";

        final Map<String, String> params = new HashMap<>(4);
        params.put("from", this.withdrawAddress);
        params.put("quantity", amount + " EOS");

        final String[] addressAndTag = record.getAddress().split(":");
        if (addressAndTag.length != 2) {
            log.error("{} signTransaction error,addressAndTag.Len !=2 ", this.getCurrency());
            return null;
        }
        params.put("to", addressAndTag[0]);
        params.put("memo", addressAndTag[1]);
        final String account = this.getAccount(currency.getContractAddress());
        if (StringUtils.isEmpty(account)) {
            log.error("buildTransaction err,currency {},account is null", currency.getName());
            return null;
        }
        final AbiJsonToBin eoscData = this.eosApiRestClient.abiJsonToBin(account, action, params);

        signature.put("eoscData", eoscData);

        signature.put("headBlockTime", headBlockTime);
        signature.put("headBlockId", headBlockId);
        signature.put("chainId", chainId);

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
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        String result = "";
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            final String rawTransaction = signature.getString("rawTransaction");
            final PackedTransaction packedTransaction = JSONObject.parseObject(rawTransaction, PackedTransaction.class);
            return TransactionUtils.transferLocalEOSC(this.eosApiRestClient, packedTransaction);
        } catch (final Throwable e) {
            log.error("sendRawTransaction error{}", e);
            result = "";
        }
        return result;
    }

    @Override
    public boolean checkAddress(final String addressWithTag) {
        try {
            final String[] addressAndTag = addressWithTag.split(":");
            if (addressAndTag.length != 2) {
                return false;
            }
            final String address = addressAndTag[0];
            //通过转化来判断地址是否合法，如果不合法会抛异常
            this.rpc.getAccount(address);
            return true;

        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", addressWithTag, e);
            return false;
        }

    }

}
