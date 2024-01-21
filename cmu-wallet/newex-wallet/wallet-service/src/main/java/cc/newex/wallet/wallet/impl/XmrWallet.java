package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.XmrCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.XmrKeyImage;
import cc.newex.wallet.pojo.rpc.XmrTransfer;
import cc.newex.wallet.pojo.rpc.XmrTransferDestinations;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.Sha256Hash;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.tron.wallet.util.ByteArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.SETPEXPORTKEYIMAGES;
import static cc.newex.wallet.utils.Constants.SETPSIGN;
import static cc.newex.wallet.utils.Constants.WALLET_WITHDRAW_XMR_TX_LOCK_KEY;

/**
 * @author newex-team
 * @data 2018/12/25
 */
@Slf4j
@Component
public class XmrWallet extends AbstractAccountWallet implements IWallet {
    @Value("${newex.xmr.server}")
    private String xmrServer;

    @Value(("${newex.xmr.user}"))
    private String username;

    @Value(("${newex.xmr.pwd}"))
    private String password;

    @Value("${newex.xmr.withdraw.address}")
    private String withdrawAddress;

    @Autowired
    XmrCommand command;

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XMR;
    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.XMR.getDecimal();
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    /**
     * 生成地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final int index = 0;
        final String paymentId = Hex.toHexString(Sha256Hash.hash(ByteArray.fromHexString(UUID.randomUUID().toString().replace("-", ""))));
        final String addressStr = this.getWithdrawAddress() + ":" + paymentId;
        final Address address = new Address();
        address.setAddress(addressStr);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(index);
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    /**
     * 检查地址格式是否正确
     *
     * @param address
     * @return
     */
    @Override
    public boolean checkAddress(final String address) {
        final String[] strs = address.split(":");
        if (strs.length != 2) {
            return false;
        }
        final String addr = strs[0];
        final String paymentId = strs[1];

        // 检查地址合法性
        try {
            cc.newex.wallet.xmrwallet.model.Address.decodeFromAddressStr(addr);
        } catch (final RuntimeException e) {
            log.error("XMR checkAddress RuntimeException error {}", e.getMessage());
            return false;
        } catch (final IOException e) {
            log.error("XMR checkAddress IOException error {}", e.getMessage());
            return false;
        }

        // 检查paymentId 合法性
        if (paymentId.length() != 64) {
            return false;
        }

        return true;
    }

    /**
     * 返回当前币种钱包中未锁定的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance(final String withdrawAddress, final CurrencyEnum currencyEnum) {
        final JSONObject jsonObject = this.command.getBalance();
        return jsonObject.containsKey("unlocked_balance") ? jsonObject.getBigDecimal("unlocked_balance").divide(this.getDecimal()) : BigDecimal.ZERO;
    }

    /**
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        final JSONObject jsonObject = this.command.getBalance();
        return jsonObject.containsKey("balance") ? jsonObject.getBigDecimal("balance").divide(this.getDecimal()) : BigDecimal.ZERO;
    }

    /**
     * 获得区块链的当前最大高度
     *
     * @return
     */
    @Override
    public Long getBestHeight() {
        final JSONObject jsonObject = this.command.getHeight();
        return jsonObject.containsKey("height") ? jsonObject.getLong("height") : 0L;
    }

    @Override
    public int getConfirm(final String txId) {
        final JSONObject jsonObject = this.command.getTransferByTxid(txId);
        return jsonObject.containsKey("transfer") ?
                jsonObject.getObject("transfer", XmrTransfer.class).getConfirmations().intValue()
                : 0;
    }

    /**
     * 发送签好的原始交易
     * 这里分两步
     * setp exportKeyImages：
     * 将签名服务器keyImages导入只读钱包，生成交易
     * setp sign:
     * 将未签名的交易发送到待签名队列签名
     * 最后广播交易
     *
     * @param transaction
     * @return
     */
    @Override
    public synchronized String sendRawTransaction(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        final String setp = signature.getString("setp");
        final String raw = signature.getString("rawTransaction");
        String setpStatus = "";
        switch (setp) {
            case SETPEXPORTKEYIMAGES:
                JSONObject jsonObject = JSONObject.parseObject(raw);
                final XmrKeyImage xmrKeyImage = jsonObject.toJavaObject(XmrKeyImage.class);
                if (ObjectUtils.isEmpty(xmrKeyImage.getSigned_key_images())) {
                    break;
                }
                this.command.importKeyImages(xmrKeyImage.getSigned_key_images(), xmrKeyImage.getOffset());
                final List<XmrTransferDestinations> xmrTransferDestinations = new ArrayList<>();

                final String destAddr = signature.getString("to").split(":")[0];
                final String paymentId = signature.getString("to").split(":")[1];

                final XmrTransferDestinations xmrTransferDestinations1 = XmrTransferDestinations.builder()
                        .address(destAddr)
                        .amount(signature.getBigDecimal("amount").multiply(this.getDecimal()).longValue())
                        .build();
                xmrTransferDestinations.add(xmrTransferDestinations1);
                jsonObject = this.command.transfer(xmrTransferDestinations, 0, Arrays.asList(0), 0, true, 11, paymentId);

                final String unsignedTxSet = jsonObject.getString("unsigned_txset");

                // 将未签名交易发送给签名服务器
                signature.remove("unsign");
                signature.put("unsign", unsignedTxSet);

                // 修改setp 步骤为sign
                signature.remove("setp");
                signature.put("setp", SETPSIGN);

                // 删除outputs
                signature.remove("outputs");
                transaction.setSignature(signature.toJSONString());

                // 重新将未签名交易发送到待签名队列
                final String val = JSONObject.toJSONString(transaction);
                REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
                break;
            case SETPSIGN:
                jsonObject = this.command.submitTransfer(raw);
                // 默认只有一个交易hash
                setpStatus = jsonObject.getJSONArray("tx_hash_list").getString(0);
                // 解锁接受提现
                REDIS.del(WALLET_WITHDRAW_XMR_TX_LOCK_KEY);
                break;
            default:
                log.error("unknown setp id: {}", setp);
                break;
        }
        return setpStatus;
    }

    /**
     * 获取当前区块高度下的充值交易
     *
     * @param height
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        List<TransactionDTO> dtos = new LinkedList<>();
        log.info("XMR findRelatedTxs start,height:{}", height);
        final JSONObject transferJson = this.command.getTransfers(true, true, true, height - 1, height);

        // 更新交易确认状态
        if (transferJson.containsKey("out")) {
            final List<XmrTransfer> xmrTransfers = transferJson.getJSONArray("out").toJavaList(XmrTransfer.class);
            xmrTransfers.parallelStream().forEach(xmrTransfer -> this.updateWithdrawTXId(xmrTransfer.getTxid(), this.getCurrency()));
        }

        if (!transferJson.containsKey("in")) {
            return dtos;
        }
        final List<XmrTransfer> xmrTransfers = transferJson.getJSONArray("in").toJavaList(XmrTransfer.class);
        if (ObjectUtils.isEmpty(xmrTransfers)) {
            log.info("findRelatedTxs getTransfers is null,height:{}", height);
            return dtos;
        }

        final List<AccountTransaction> accountTransactions = xmrTransfers.parallelStream()
                .map(xmrTransfer -> {
                    // 是否是系统内部地址
                    String addressStr = xmrTransfer.getAddress() + ":" + xmrTransfer.getPayment_id();
                    final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                    final Address address = this.addressService.getAddress(addressStr, table);
                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }

                    // 双花交易
                    if (xmrTransfer.getDouble_spend_seen()) {
                        return null;
                    }

                    // TODO 待验证
                    if (xmrTransfer.getUnlock_time() > 0L) {
                        return null;
                    }

                    AccountTransaction accountTransaction = AccountTransaction.builder()
                            .txId(xmrTransfer.getTxid())
                            .blockHeight(height)
                            .address(addressStr)
                            .balance(xmrTransfer.getAmount().divide(this.getDecimal()))
                            .confirmNum(xmrTransfer.getConfirmations())
                            .biz(address.getBiz())
                            .currency(this.getCurrency().getIndex())
                            .status((byte) Constants.WAITING)
                            .createDate(Date.from(Instant.now()))
                            .updateDate(Date.from(Instant.now())).build();
                    return accountTransaction;
                })
                .filter((acTx) -> !ObjectUtils.isEmpty(acTx)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(accountTransactions)) {
            dtos = accountTransactions.parallelStream()
                    .map(tx -> {
                        CurrencyEnum currency = CurrencyEnum.parseValue(tx.getCurrency());
                        ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
                        this.accountTransactionService.addOnDuplicateKey(tx, table);
                        return this.convertAccountTxToDto(tx);
                    })
                    .collect(Collectors.toList());
        }
        log.info("XMR findRelatedTxs end,height:{}", height);

        return dtos;
    }

    @Override
    protected synchronized WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        final boolean lock = REDIS.setNX(WALLET_WITHDRAW_XMR_TX_LOCK_KEY, "lock");
        final long threeMinutes = 3 * 60;
        // 锁住xmr提现，排队
        if (lock) {
            REDIS.expire(WALLET_WITHDRAW_XMR_TX_LOCK_KEY, threeMinutes);
            return this.buildTransaction(record, this.withdrawAddress);
        } else {
            return null;
        }
    }

    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from) {
        final JSONObject jsonObject = this.command.exportOutputs();
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final ShardTable addressTable;
        addressTable = ShardTable.builder().prefix(currency.getName()).build();
        final Address address = this.addressService.getAddress(from, addressTable);

        final JSONObject signature = new JSONObject();
        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("amount", record.getBalance());
        signature.put("setp", SETPEXPORTKEYIMAGES);
        signature.put("outputs", jsonObject.getString("outputs_data_hex"));
        signature.put("unsign", "");

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId("transfer")
                .signature(signature.toJSONString())
                .build();
        address.setNonce(address.getNonce() + 1);
        address.setUpdateDate(Date.from(Instant.now()));
        this.addressService.editById(address, addressTable);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);
        log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

}
