package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.XemCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ripple.core.coretypes.uint.UInt32;
import lombok.extern.slf4j.Slf4j;
import org.nem.core.crypto.KeyPair;
import org.nem.core.crypto.PublicKey;
import org.nem.core.model.Account;
import org.nem.core.model.NetworkInfos;
import org.ripple.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Slf4j
@Component
public class XemWallet extends AbstractAccountWallet {

    @Autowired
    XemCommand command;

    @Value("${newex.xem.withdraw.address}")
    private String withdrawAddress;

    static {
        NetworkInfos.setDefault(NetworkInfos.getMainNetworkInfo());
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XEM;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.XEM.getDecimal();
    }

    /**
     * 生成新地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        final String placeholder = UUID.randomUUID().toString();
        final Address address = new Address();
        address.setAddress(placeholder);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(0);

        this.addressService.add(address, table);
        final String addressStr = this.withdrawAddress + ":" + address.getId();
        address.setAddress(addressStr);
        this.addressService.editById(address, table);

        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    /**
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.withdrawAddress, this.getCurrency());

    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        BigDecimal amount = BigDecimal.ZERO;
        try {
            final JSONObject res = this.command.getBalance(address);
            amount = res.getJSONObject("account").getBigDecimal("balance").divide(this.getCurrency().getDecimal());
        } catch (final Throwable e) {
            log.error("get currency:{} balance error", this.getCurrency(), e);
            return amount;
        }
        return amount;
    }

    @Override
    public Long getBestHeight() {
        try {
            final JSONObject res = this.command.getBlockCount();
            return res.getLong("height");
        } catch (final Throwable e) {
            e.printStackTrace();
            log.error("getBestHeight currency:{} error", this.getCurrency(), e);
            return 0L;
        }

    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long start) {
        final String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, start);
        List<TransactionDTO> dtos = new LinkedList<>();
        List<AccountTransaction> accountTransactions = null;
        final JSONObject params = new JSONObject();
        params.put("height", start);
        try {
            final long currentHeight = this.getBestHeight();
            final JSONObject res = this.command.blocksByHeight(params);
            final JSONArray blockArray = res.getJSONArray("data");
            final JSONArray txArray = blockArray.stream()
                    .map(obj -> {
                        JSONObject block = (JSONObject) obj;
                        long height = block.getJSONObject("block").getLongValue("height");
                        JSONArray txes = block.getJSONArray("txes");
                        txes.parallelStream().forEach(tx -> ((JSONObject) tx).put("height", height));
                        return txes;
                    })
                    .reduce(new JSONArray(), (left, right) -> {
                        left.addAll(right);
                        return left;
                    });
            accountTransactions = txArray.parallelStream()
                    .filter(tx -> {
                        JSONObject txJson = (JSONObject) tx;
                        String address = txJson.getJSONObject("tx").getString("recipient");
                        return this.getWithdrawAddress().equals(address);

                    })
                    .map(tx -> this.convertFromTxJson((JSONObject) tx, currentHeight))
                    .filter(tx -> tx != null)
                    .collect(
                            Collectors.toList());

            if (!org.apache.commons.collections4.CollectionUtils.isEmpty(accountTransactions)) {
                final ShardTable table = ShardTable.builder().prefix(currencyName).build();
                this.accountTransactionService.batchAddOnDuplicateKey(accountTransactions, table);
                dtos = accountTransactions.parallelStream().map(this::convertAccountTxToDto).collect(Collectors.toList());
            }
            return dtos;
        } catch (final Throwable e) {
            log.error("{} findRelatedTxs, height:{} err:", currencyName, start, e);
            return dtos;
        }
    }

    private AccountTransaction convertFromTxJson(final JSONObject txJson, final long currentHeight) {
        AccountTransaction transaction = null;
        if (txJson == null) {
            return null;
        }
        try {

            //先检测是不是我们发出的交易
            final String txId = txJson.getString("hash");
            this.updateWithdrawTXId(txId, this.getCurrency());

            JSONObject tx = txJson.getJSONObject("tx");
            if (tx.containsKey("mosaics") && tx.getJSONArray("mosaics").size() > 0) {
                return null;
            }

            if (!tx.containsKey("amount")) {
                tx = tx.getJSONObject("otherTrans");
                if (tx.containsKey("mosaics") && tx.getJSONArray("mosaics").size() > 0) {
                    return null;
                }
            }
            final KeyPair keyPair = new KeyPair(PublicKey.fromHexString(tx.getString("signer")));

            final Account sender = new Account(keyPair);
            if (this.coinCheckStolenAddress(sender.getAddress().getEncoded())) {
                return null;
            }

            final long amount = Long.valueOf(tx.getString("amount"));
            final Long blockNum = txJson.getLong("height");

            final String toAddrStr;
            if (tx.getJSONObject("message").getInteger("type") == null) {
                //没有memo，充值不会到账
                toAddrStr = tx.getString("recipient");

            } else {
                String payload = tx.getJSONObject("message").getString("payload");
                payload = new String(ByteUtils.fromHexString(payload));
                String address = tx.getString("recipient") + ":" + payload;
                if (address.length() >= 250) {
                    address = address.substring(0, 250);
                }
                toAddrStr = address;
            }

            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final Address address = this.addressService.getAddress(toAddrStr, table);
            if (ObjectUtils.isEmpty(address)) {
                log.error("xlm address is null,toAddrStr :{}", toAddrStr);
                return null;
            }

            transaction = AccountTransaction.builder()
                    .address(toAddrStr)
                    .balance(BigDecimal.valueOf(amount).divide(this.getCurrency().getDecimal()))
                    .biz(address.getBiz())
                    .txId(txJson.getString("hash"))
                    .blockHeight(blockNum)
                    .confirmNum(currentHeight - blockNum)
                    .status((byte) Constants.WAITING)
                    .currency(this.getCurrency().getIndex())
                    .createDate(Date.from(Instant.now()))
                    .updateDate(Date.from(Instant.now()))
                    .build();
            return transaction;

        } catch (final Throwable e) {
            log.error("xem request convertFromTxJson hash:{}", txJson.getString("hash"), e);
            return null;
        }
    }

    /**
     * 发送签好的原始交易
     *
     * @param transaction
     * @return
     */
    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        String txId = "";
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());

            final String raw = signature.getString("rawTransaction");
            final JSONObject res = this.command.sendRawTransaction(JSONObject.parseObject(raw));
            txId = res.getJSONObject("transactionHash").getString("data");
            return txId;
        } catch (final Throwable e) {
            log.error("sendRawTransaction error", e);
            return "";
        }
    }

    @Override
    public boolean checkAddress(final String addressWithTag) {
        try {
            final String[] addressAndTag = addressWithTag.split(":");
            if (addressAndTag.length != 2) {
                return false;
            }
            final String addressStr = addressAndTag[0];

            final String tag = addressAndTag[1];
            final BigInteger tagInteger = new BigInteger(tag);
            if (tagInteger.compareTo(UInt32.Max32) > 0) {
                return false;
            }
            //通过转化来判断地址是否合法，如果不合法会抛异常
            final org.nem.core.model.Address address = org.nem.core.model.Address.fromEncoded(addressStr);
            return address.isValid();

        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", addressWithTag, e);
            return false;
        }

    }

    @Override
    public int getConfirm(final String txId) {
        try {
            final String officialDomain = "http://alice2.nem.ninja:7890";
            final String uri = officialDomain + "/transaction/get?hash=" + txId;
            final JSONObject txJson = this.curlGet(uri);

            final Long height = txJson.getJSONObject("meta").getLong("height");
            final Long bestHeight = this.getBestHeight();
            final Long confirm = bestHeight - height;
            return confirm.intValue();
        } catch (final Throwable e) {
            log.error("getConfirm txId:{} error", txId, e);
            return 0;
        }
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        //获取根账户地址
        final ShardTable addressTable = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final Address address = this.addressService.getAddress(this.getWithdrawAddress(), addressTable);

        final JSONObject signature = new JSONObject();

        final DecimalFormat fnum = new DecimalFormat("##0.0000000");
        fnum.setRoundingMode(RoundingMode.FLOOR);
        final String amount = fnum.format(record.getBalance());
        final BigDecimal amountFloor = new BigDecimal(amount);

        signature.put("address", address);
        signature.put("from", this.getWithdrawAddress());
        signature.put("to", record.getAddress());
        signature.put("amount", amountFloor);

        // String[] addressAndTag = record.getAddress().split(":");
        // if (addressAndTag.length != 2) {
        //     log.info("{} buildTransaction error,record.getAddress !=2 {}", this.getCurrency().getName(), record.getAddress());
        //     return null;
        // }

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(amountFloor)
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

    private boolean coinCheckStolenAddress(final String address) {
        final JSONObject res = this.command.getAcscountMosaics(address);
        final JSONArray mosaics = res.getJSONArray("data");
        boolean isStolen = false;
        if (CollectionUtils.isEmpty(mosaics)) {
            return isStolen;
        }
        for (final Object obj : mosaics) {
            final JSONObject mosaic = (JSONObject) obj;
            if (
                    mosaic.getJSONObject("mosaicId").getString("namespaceId").indexOf("coincheck_stolen_funds") > 0 ||
                            mosaic.getJSONObject("mosaicId").getString("name").indexOf("coincheck_stolen_funds") > 0 ||
                            mosaic.getJSONObject("mosaicId").getString("namespaceId").indexOf("accept_stolen_funds") > 0 ||
                            mosaic.getJSONObject("mosaicId").getString("name").indexOf("accept_stolen_funds") > 0
                    ) {
                isStolen = true;
                break;
            }
        }
        return isStolen;
    }

    private JSONObject curlGet(String url) {
        try {
            final String httpStart = "http";
            if (!url.startsWith(httpStart)) {
                url = httpStart + "://" + url;
            }

            final String command = "curl " + url;
            final Process p = Runtime.getRuntime().exec(command);
            String line;
            String res = "";
            final BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                res = res + line;
            }
            br.close();
            return JSONObject.parseObject(res);
        } catch (final Throwable e) {
            log.error("curlGet {} error", url, e);
        }
        return null;
    }
}
