package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.WalletContext;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 处理用户充错币种
 *
 * @author newex-team
 * @data 2018/5/9
 */
@Component
@ElasticJobExtConfig(cron = "1 1/5 * * * ?")
@Slf4j
public class TransferBetweenCurrency implements SimpleJob {

    @Autowired
    AddressService addressService;

    @Autowired
    WithdrawTransactionService transactionService;
    @Autowired
    WalletContext walletContext;

    @Override
    public void execute(final ShardingContext shardingContext) {
        TransferBetweenCurrency.log.info("TransferBetweenCurrency begin");
        final String key = Constants.WALLET_TRANSFER_BETWEEN_CURRENCY_KEY;
        try {
            while (true) {
                final String recordStr = REDIS.rPop(key);
                if (ObjectUtils.isEmpty(recordStr)) {
                    break;
                }
                final JSONObject recordJson = JSONObject.parseObject(recordStr);
                final String srcCur = recordJson.getString("srcCurrency");

                if (CurrencyEnum.parseName(srcCur) == CurrencyEnum.ETH || CurrencyEnum.parseName(srcCur) == CurrencyEnum.ETC) {
                    this.transferBetweenEtcAndEth(recordJson);
                } else {
                    this.transferBetweenUtxo(recordJson);
                }


            }
        } catch (final Throwable e) {
            TransferBetweenCurrency.log.error("TransferBetweenCurrency error", e);

        }


        TransferBetweenCurrency.log.info("TransferBetweenCurrency end");
    }

    private void transferBetweenUtxo(final JSONObject recordJson) {

        final String dstCurStr = recordJson.getString("dstCurrency");
        final String dstAddrStr = recordJson.getString("dstAddress");
        final BigDecimal transferAmount = recordJson.getBigDecimal("transferAmount");
        final BigDecimal amount = recordJson.getBigDecimal("amount");

        final ShardTable addressTable = ShardTable.builder().prefix(dstCurStr).build();
        final List<Address> addresses = new LinkedList<>();
        final Address address = this.addressService.getAddress(dstAddrStr, addressTable);
        addresses.add(address);
        final String[] txIdAndSqe = recordJson.getString("txId").split("-");
        final LinkedList<UtxoTransaction> utxos = new LinkedList<>();
        final UtxoTransaction utxo = UtxoTransaction.builder()
                .balance(amount)
                .txId(txIdAndSqe[0])
                .seq(Short.parseShort(txIdAndSqe[1]))
                .build();
        utxos.add(utxo);

        final CurrencyEnum srcCurrency = CurrencyEnum.parseName(recordJson.getString("srcCurrency"));
        final String srcAddrStr = recordJson.getString("srcAddress");
        final String changeAddr = recordJson.getString("changeAddrStr");
        final int feePerKb = REDIS.getInt(Constants.WALLET_FEE + srcCurrency.getIndex());

        final JSONObject signature = new JSONObject();
        //final IWallet wallet = this.walletContext.getWallet(srcCurrency);
        //final Address changeAddress = wallet.genNewAddress(Constants.USER_ID, Constants.BIZ);
        final List<WithdrawRecord> records = new LinkedList<>();
        final WithdrawRecord record = WithdrawRecord.builder()
                .address(srcAddrStr)
                .balance(transferAmount)
                .fee(BigDecimal.ZERO)
                .build();
        records.add(record);
        signature.put("utxos", utxos);
        signature.put("addresses", addresses);
        signature.put("withdraw", records);
        signature.put("changeAddress", changeAddr);
        signature.put("feePerKb", feePerKb);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(amount)
                .currency(srcCurrency.getIndex())
                .status(Constants.SIGNING)
                .txId("singing")
                .signature(signature.toJSONString())
                .build();
        final ShardTable table = ShardTable.builder().prefix(srcCurrency.getName()).build();

        this.transactionService.add(transaction, table);
        final String val = JSONObject.toJSONString(transaction);
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_FIRST_KEY, val);

    }

    private void transferBetweenEtcAndEth(final JSONObject recordJson) {
        final String dstCurStr = recordJson.getString("dstCurrency");
        final String dstAddrStr = recordJson.getString("dstAddress");
        final BigDecimal amount = recordJson.getBigDecimal("amount");
        final ShardTable addressTable = ShardTable.builder().prefix(dstCurStr).build();
        final Address address = this.addressService.getAddress(dstAddrStr, addressTable);

        final CurrencyEnum srcCurrency = CurrencyEnum.parseName(recordJson.getString("srcCurrency"));
        final String srcAddrStr = recordJson.getString("srcAddress");

        final AbstractEthLikeWallet wallet = (AbstractEthLikeWallet) this.walletContext.getWallet(srcCurrency);
        final Long nonce = wallet.getAddressNonce(dstAddrStr);
        address.setNonce(nonce.intValue());
        final BigDecimal gas = new BigDecimal("0.00000000000042");
        final BigDecimal gasPrice = new BigDecimal("0.00000002");
        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", dstAddrStr);
        signature.put("to", srcAddrStr);
        signature.put("value", amount);
        signature.put("gas", gas);
        signature.put("nonce", nonce);
        signature.put("gasPrice", gasPrice);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(amount)
                .currency(srcCurrency.getIndex())
                .status(Constants.SIGNING)
                .txId("transferBetweenCurrency")
                .signature(signature.toJSONString())
                .build();
        final ShardTable table = ShardTable.builder().prefix(srcCurrency.getName()).build();
        this.transactionService.add(transaction, table);
        final String val = JSONObject.toJSONString(transaction);
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);

    }

}
