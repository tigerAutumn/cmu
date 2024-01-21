package cc.newex.wallet.wallet;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.ActLikeCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.utils.Constants;
import com.achain.data.ACTAddress;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.achain.data.ACTAddress.Type.ADDRESS;

/**
 * @author newex-team
 * @data 2018/5/14
 */
@Slf4j
@Data
public abstract class AbstractActLikeWallet extends AbstractAccountWallet implements IWallet {
    @Autowired
    protected AccountTransactionService accountTransactionService;
    @Autowired
    AddressService addressService;
    ActLikeCommand command;
    private String withdrawAddress;

    public void setCommand(final ActLikeCommand com) {
        this.command = com;
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        AbstractActLikeWallet.log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());
        final UUID uuid = UUID.randomUUID();
        final String subAddress = uuid.toString().replace("-", "");
        final Address address = new Address();
        address.setAddress(this.withdrawAddress + subAddress);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(0);
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        this.addressService.add(address, table);
        AbstractActLikeWallet.log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    /**
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.getCurrency());
    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        return this.getBalance(currency);
    }

    public BigDecimal getBalance(final CurrencyEnum currency) {
        AbstractActLikeWallet.log.info("get {} Balance begin", this.getCurrency().getName());
        try {
            BigDecimal balance = BigDecimal.ZERO;
            final JSONArray dataArray = this.command.getAddressBalance(this.withdrawAddress);
            if (!CollectionUtils.isEmpty(dataArray)) {
                for (int i = 0; i < dataArray.size(); i++) {
                    try {
                        final JSONObject tmp = dataArray.getJSONArray(i).getJSONObject(1);
                        final JSONObject condition = tmp.getJSONObject("condition");
                        final int assetId = condition.getIntValue("asset_id");
                        if (assetId == 0) {
                            balance = balance.add(tmp.getBigDecimal("balance").divide(this.getCurrency().getDecimal()));
                        }
                    } catch (final Throwable e) {
                        AbstractActLikeWallet.log.error("foreach {} Balance error", this.getCurrency().getName(), e);
                        return BigDecimal.ZERO;

                    }
                }
            }
            AbstractActLikeWallet.log.info("get {} Balance end", this.getCurrency().getName());

            return balance;
        } catch (final Exception e) {
            AbstractActLikeWallet.log.error("get {} Balance error", this.getCurrency().getName(), e);

        }
        return BigDecimal.ZERO;

    }

    /**
     * 获得区块链的当前最大高度
     *
     * @return
     */
    @Override
    public Long getBestHeight() {
        try {
            return this.command.getBlockCount("latest");
        } catch (final Exception e) {
            AbstractActLikeWallet.log.error("{} getBestHeight error", this.getCurrency().getName(), e);
            return 0L;
        }
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();

        AbstractActLikeWallet.log.info("{} findRelatedTxs, height:{} begin", currencyName, height);

        final JSONObject block = this.command.getBlockByHeight(height);
        if (ObjectUtils.isEmpty(block)) {
            AbstractActLikeWallet.log.error("findRelatedTxs error, block is null");
            return null;
        }
        final JSONArray txIdArray = block.getJSONArray("user_transaction_ids");
        if (CollectionUtils.isEmpty(txIdArray)) {
            AbstractActLikeWallet.log.info("{} findRelatedTxs empty, height:{} end", currencyName, height);
            return new LinkedList<>();
        }
        final List<AccountTransaction> accountTransactions = txIdArray.parallelStream().map((obj) -> {
            AccountTransaction accountTransaction = this.getAccountTx((String) obj, height);
            if (ObjectUtils.isEmpty(accountTransaction)) {
                return accountTransaction;
            }
            CurrencyEnum txCurrency = CurrencyEnum.parseValue(accountTransaction.getCurrency());
            final ShardTable table = ShardTable.builder().prefix(txCurrency.getName()).build();
            this.accountTransactionService.addOnDuplicateKey(accountTransaction, table);
            return accountTransaction;
        }).filter((acTx) -> !org.springframework.util.ObjectUtils.isEmpty(acTx)).collect(Collectors.toList());
        List<TransactionDTO> dtos = new LinkedList<>();
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(accountTransactions)) {
            dtos = accountTransactions.parallelStream()
                    .map(this::convertAccountTxToDto)
                    .collect(Collectors.toList());
        }

        AbstractActLikeWallet.log.info("{} findRelatedTxs, height:{} end", currencyName, height);
        return dtos;
    }

    private AccountTransaction getAccountTx(final String txId, final Long height) {

        //先检测是不是我们发出的交易
        try {
            this.updateWithdrawTXId(txId, this.getCurrency());
            final JSONObject transaction = this.command.getTransactionByTxId(txId).getJSONObject(1).getJSONObject("trx");

            final JSONObject inPortAsset = transaction.getJSONObject("alp_inport_asset");
            if (inPortAsset.getIntValue("asset_id") != 0) {
                return null;
            }
            final JSONObject operations = transaction.getJSONArray("operations").getJSONObject(0);
            AccountTransaction accountTransaction = null;
            if (operations.getString("type").equals("deposit_op_type") || operations.getString("type").equals("withdraw_op_type")) {
                accountTransaction = this.getActTx(txId);
                if (ObjectUtils.isEmpty(accountTransaction)) {
                    return null;
                }
                final String alpAccount = transaction.getString("alp_account");
                if (StringUtils.hasText(alpAccount)) {
                    accountTransaction.setAddress(alpAccount);
                }
            } else if (operations.getString("type").equals("transaction_op_type")) {
                accountTransaction = this.getContractTx(height, txId);
                if (ObjectUtils.isEmpty(accountTransaction)) {
                    return null;
                }
            } else {
                return null;
            }
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

            final Address address = this.addressService.getAddress(accountTransaction.getAddress(), table);
            if (ObjectUtils.isEmpty(address)) {
                return null;
            }
            accountTransaction.setBiz(address.getBiz());
            accountTransaction.setBlockHeight(height);
            final Long confirm = this.getBestHeight() - height + 1;
            accountTransaction.setConfirmNum(confirm);

            return accountTransaction;
        } catch (final Throwable e) {
            log.error("currency:{} getAccountTx error,txid:{}", this.getCurrency(), txId, e);
            return null;
        }

    }

    private AccountTransaction getActTx(final String txid) {
        final JSONObject actJson = this.command.getPrettyTransactionByTxId(txid);
        final String toAddress = actJson.getJSONArray("ledger_entries").getJSONObject(0).getString("to_account");
        final BigDecimal amount = actJson.getJSONArray("ledger_entries").getJSONObject(0).getJSONObject("amount").getBigDecimal("amount").divide(this.getDecimal());
        final AccountTransaction actTx = AccountTransaction.builder()
                .address(toAddress)
                .balance(amount)
                .txId(txid)
                .status((byte) Constants.WAITING)
                .currency(this.getCurrency().getIndex())
                .createDate(Date.from(Instant.now()))
                .updateDate(Date.from(Instant.now()))
                .build();
        return actTx;
    }

    private AccountTransaction getContractTx(final long height, final String txid) {
        final JSONObject actJson = this.command.getPrettyContractTransactionByTxId(txid);
        final String contractAddress = actJson.getJSONObject("to_contract_ledger_entry").getString("to_account");
        final CurrencyEnum currencyEnum = CurrencyEnum.parseContract(contractAddress);
        if (ObjectUtils.isEmpty(currencyEnum)) {
            return null;
        }

        final JSONArray event = this.command.getEvents(height, txid);
        final String success = event.getJSONObject(0).getString("event_type");
        if (!success.equals("transfer_to_success")) {
            return null;
        } else {
            final String[] params = event.getJSONObject(0).getString("event_param").split(",");
            for (final String p : params) {
                final String[] addressAndAmount = p.split(":");
                if (addressAndAmount[0].equals(this.withdrawAddress)) {
                    final BigDecimal amount = new BigDecimal(addressAndAmount[1]).divide(this.getDecimal());
                    this.updateTotalCurrencyBalance(currencyEnum, amount);
                }
            }
        }

        final String orgTxId = actJson.getString("orig_trx_id");
        this.updateWithdrawTXId(orgTxId, currencyEnum);
        final String[] reserved = actJson.getJSONArray("reserved").getString(1).split("\\|");
        final AccountTransaction actTx = AccountTransaction.builder()
                .address(reserved[0])
                .balance(new BigDecimal(reserved[1]))
                .txId(orgTxId)
                .status((byte) Constants.WAITING)
                .currency(currencyEnum.getIndex())
                .createDate(Date.from(Instant.now()))
                .updateDate(Date.from(Instant.now()))
                .build();
        return actTx;
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {

        return this.buildTransaction(record, this.withdrawAddress);
    }

    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        final ShardTable addressTable;

        if (CurrencyEnum.isATP(currency)) {
            addressTable = ShardTable.builder().prefix(CurrencyEnum.ACT.getName()).build();
        } else {
            addressTable = ShardTable.builder().prefix(currency.getName()).build();
        }
        final Address address = this.addressService.getAddress(from, addressTable);

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("amount", record.getBalance());

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
        AbstractActLikeWallet.log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

    /**
     * 发送签好的原始交易
     *
     * @param transaction
     * @return
     */
    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        String result;
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());

            final String raw = signature.getString("rawTransaction");
            result = this.command.sendRawTransaction(JSONObject.parseObject(raw));
        } catch (final Throwable e) {
            AbstractActLikeWallet.log.error("sendRawTransaction error", e);
            result = "";
        }
        return result;
    }

    @Override
    public boolean checkAddress(String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                if (addressStr.startsWith(this.getCurrency().name())) {
                    addressStr = addressStr.substring(3);
                    if (addressStr.length() >= 64) {
                        addressStr = addressStr.substring(0, addressStr.length() - 32);
                    }
                }

                valid = ACTAddress.check(addressStr, ADDRESS);
            } catch (final Throwable e) {
                AbstractActLikeWallet.log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

    @Override
    public int getConfirm(final String txId) {
        final JSONArray transaction = this.command.getTransactionByTxId(txId);
        if (ObjectUtils.isEmpty(transaction)) {
            return -1;

        } else {
            final JSONObject info = transaction.getJSONObject(1);
            final Long blockHeight = info.getJSONObject("chain_location").getLong("block_num");
            final Long bestHeight = this.getBestHeight();
            final Long confirm = bestHeight - blockHeight + 1;
            return confirm.intValue();
        }

    }

    @Override
    public String getTxId(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        final String raw = signature.getString("rawTransaction");
        final String hash = this.command.getTransactionId(JSONObject.parseObject(raw));

        return hash;
    }

}
