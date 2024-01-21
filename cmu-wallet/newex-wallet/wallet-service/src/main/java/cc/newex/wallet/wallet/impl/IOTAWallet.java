package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.WithdrawRecordService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jota.IotaAPI;
import jota.dto.response.FindTransactionResponse;
import jota.dto.response.GetInclusionStateResponse;
import jota.error.ArgumentException;
import jota.model.Transaction;
import jota.utils.Checksum;
import jota.utils.InputValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 2018/6/8
 */
@Slf4j
@Component
public class IOTAWallet extends AbstractAccountWallet implements IWallet {
    @Autowired
    protected AccountTransactionService accountTransactionService;
    @Autowired
    WithdrawTransactionService withdrawTransactionService;
    @Autowired
    WithdrawRecordService recordService;
    @Autowired
    AddressService addressService;

    @Value("${newex.iota.server}")
    private String iotaServer;

    private IotaAPI command;

    @PostConstruct
    public void init() {
        final String[] serverAndPort = this.iotaServer.split(":");
        this.command = new IotaAPI.Builder().protocol(serverAndPort[0]).host(serverAndPort[1].replace("//", "")).port(serverAndPort[2]).build();

    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.IOTA.getDecimal();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.IOTA;
    }

    /**
     * 生成新地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andUserIdEqualTo(-1L);
        addressExample.setOrderByClause("id asc");
        final Address address = this.addressService.getAndLockOneByExample(addressExample, table);
        address.setUserId(userId);
        address.setBiz(biz);
        address.setUpdateDate(Date.from(Instant.now()));

        this.addressService.editById(address, table);

        return address;
    }

    @Override
    public BigDecimal getBalance() {
        final String currencyName = this.getCurrency().getName();
        log.info("get {} Balance begin", currencyName);
        try {
            final AddressExample example = new AddressExample();
            example.createCriteria().andStatusLessThan((byte) Constants.CONFIRM).andUserIdGreaterThan(-1L);
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final BigDecimal balance = this.addressService.getTotalBalance(example, table);
            log.info("get {} Balance end", currencyName);
            return ObjectUtils.isEmpty(balance) ? BigDecimal.ZERO : balance;
        } catch (final Exception e) {
            log.error("{} getBalance error", this.getCurrency(), e);

        }
        return BigDecimal.ZERO;
    }

    /**
     * iota 没有区块高度概念
     *
     * @return
     */
    @Override
    public Long getBestHeight() {
        return 0L;
    }

    @Override
    public boolean checkAddress(final String address) {
        try {
            final boolean valid = InputValidator.isAddress(address);
            return valid;
        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", address, e);
            return false;
        }

    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final int PAGE_SIZE = 10;

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(PAGE_SIZE);
        pageInfo.setSortItem("id");
        pageInfo.setStartIndex(0);

        final ShardTable shardTable = new ShardTable();
        shardTable.setPrefix("iota");
        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdGreaterThan(-1L).andStatusEqualTo((byte) Constants.WAITING);

        try {
            List<Address> addresses = null;
            final Map<String, AccountTransaction> accountTransactions = new LinkedHashMap<>();
            do {
                addresses = this.addressService.getByPage(pageInfo, example, shardTable);

                final String[] addressesStr = addresses.stream().map(Address::getAddress).toArray(String[]::new);

                final List<jota.model.Transaction> rawTxs = this.command.findTransactionObjectsByAddresses(addressesStr);
                final List<AccountTransaction> tmp = rawTxs.parallelStream()
                        .map(this::convertFromJotaTx)
                        .filter(tx -> tx != null)
                        .collect(Collectors.toList());
                for (final AccountTransaction tx : tmp) {
                    final AccountTransaction origin = accountTransactions.get(tx.getTxId());
                    if (ObjectUtils.isEmpty(origin) || origin.getConfirmNum() < tx.getConfirmNum()) {
                        accountTransactions.put(tx.getTxId(), tx);
                    }
                }
                pageInfo.setStartIndex(pageInfo.getStartIndex() + PAGE_SIZE);

            } while (addresses != null && addresses.size() >= PAGE_SIZE);

            List<TransactionDTO> dtos = new LinkedList<>();
            if (!accountTransactions.isEmpty()) {
                dtos = accountTransactions.values().parallelStream()
                        .filter((tx) -> {
                            IOTAWallet selfReference = this.getSelf(this.getClass());
                            return selfReference.updateAddrBalance(tx);
                        })
                        .map(this::convertAccountTxToDto).collect(Collectors.toList());

                final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                final List<AccountTransaction> txList = new LinkedList<>();
                txList.addAll(accountTransactions.values());
                this.accountTransactionService.batchAddOnDuplicateKey(txList, table);
            }
            log.info("{} findRelatedTxs, height:{} end", this.getCurrency().getName(), height);
            return dtos;
        } catch (final Throwable e) {
            log.error("{} findRelatedTxs error", this.getCurrency(), e);
            return null;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateAddrBalance(final AccountTransaction tx) {
        final String txid = tx.getTxId();
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andTxIdEqualTo(txid).andConfirmNumGreaterThan(0L);
        final AccountTransaction exist = this.accountTransactionService.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(exist)) {

            final String addressStr = tx.getAddress();
            final AddressExample addressExample = new AddressExample();
            addressExample.createCriteria().andAddressEqualTo(addressStr).andUserIdGreaterThan(-1L).andStatusEqualTo((byte) Constants.WAITING);

            final Address address = this.addressService.getAndLockOneByExample(addressExample, table);
            if (ObjectUtils.isEmpty(address)) {
                return false;
            } else {
                if (tx.getConfirmNum() >= this.getCurrency().getDepositConfirmNum()) {
                    final BigDecimal balance = address.getBalance().add(tx.getBalance());
                    address.setBalance(balance);
                    address.setStatus((byte) Constants.SIGNING);
                }
                tx.setBiz(address.getBiz());
                address.setUpdateDate(java.util.Date.from(Instant.now()));
                this.addressService.editById(address, table);
                return true;
            }
        } else {
            return true;
        }
    }

//    @Override
//    protected void updateWithdrawTXId(final String txId, final CurrencyEnum currency) {
//        super.updateWithdrawTXId(txId, currency);
//        try {
//            final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
//
//            final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getByTxId(txId, currency);
//            if (!ObjectUtils.isEmpty(withdrawTransaction)) {
//                JSONObject sigJson = JSONObject.parseObject(withdrawTransaction.getSignature());
//                List<Address> addressesPojo = sigJson.getJSONArray("addresses").toJavaList(Address.class);
//                List<String> addressesStr = addressesPojo.parallelStream().map(Address::getAddress).collect(Collectors
//                        .toList());
//                final AddressExample example = new AddressExample();
//                example.createCriteria().andAddressIn(addressesStr);
//                final Address address = new Address();
//                address.setStatus((byte) Constants.CONFIRM);
//                this.addressService.editByExample(address, example, table);
//            }
//        } catch (Throwable e) {
//            log.error("updateWithdrawTXId error ,txid:{}", txId);
//        }
//
//    }

    private AccountTransaction convertFromJotaTx(final jota.model.Transaction tx) {
        AccountTransaction transaction = null;
        if (tx == null || tx.getValue() <= 0) {
            return null;
        }
        try {
            final String hash = tx.getBundle();
            this.updateWithdrawTXId(hash, this.getCurrency());

            transaction = new AccountTransaction();

            if (Checksum.isAddressWithoutChecksum(tx.getAddress())) {
                transaction.setAddress(Checksum.addChecksum(tx.getAddress()));
            } else {
                transaction.setAddress(tx.getAddress());
            }

            final long amount = tx.getValue();
            transaction.setBalance(BigDecimal.valueOf(amount).divide(this.getCurrency().getDecimal()));
            transaction.setBlockHeight(0L);
            transaction.setTxId(hash + "-" + tx.getCurrentIndex());
            if (this.checkTxConfirmed(tx)) {
                //当交易已经确认时，修改地址的状态，用于统计余额（统计余额时是根据地址状态统计：getBalance）
                transaction.setConfirmNum(this.getCurrency().getConfirmNum());
                final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

                final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getByTxId(hash, this.getCurrency());
                if (!ObjectUtils.isEmpty(withdrawTransaction)) {
                    final JSONObject sigJson = JSONObject.parseObject(withdrawTransaction.getSignature());
                    final List<Address> addressesPojo = sigJson.getJSONArray("addresses").toJavaList(Address.class);
                    final List<String> addressesStr = addressesPojo.parallelStream().map(Address::getAddress).collect(Collectors
                            .toList());
                    final AddressExample example = new AddressExample();
                    example.createCriteria().andAddressIn(addressesStr);
                    final Address address = new Address();
                    address.setStatus((byte) Constants.CONFIRM);
                    this.addressService.editByExample(address, example, table);
                }
            } else {
                transaction.setConfirmNum(0L);
            }
            transaction.setStatus((byte) Constants.WAITING);
            transaction.setBiz(BizEnum.SPOT.getIndex());
            transaction.setCurrency(this.getCurrency().getIndex());
            transaction.setCreateDate(java.util.Date.from(Instant.now()));
            transaction.setUpdateDate(java.util.Date.from(Instant.now()));

        } catch (final Throwable e) {
            log.error("convertFromJotaTx error", e);
        }

        return transaction;
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
            final String raw = signature.getString("rawTransaction");
            final List<String> trytes = JSONArray.parseArray(raw, String.class);

            final List<Transaction> trxs = this.command.sendTrytes(trytes.toArray(new String[trytes.size()]), 4, 14);
            final Optional<Transaction> tx = trxs.parallelStream().filter((trx) -> trx.getCurrentIndex() == 0L).findFirst();
            if (tx.isPresent()) {
                final String bundle = tx.get().getBundle();
                final String txHash = tx.get().getHash();
                REDIS.hSet(Constants.WALLET_IOTA_UNCONFIRMED_TX_KEY, bundle, txHash);
            }
            return trxs.get(0).getBundle();
        } catch (final Throwable e) {
            log.error("sendRawTransaction error", e);
        }

        return "";
    }

    @Override
    public boolean withdraw(final WithdrawRecord record) {
        return true;
    }

    @Override
    public int getConfirm(final String txId) {
        try {
            final List<jota.model.Transaction> transactions = this.command.findTransactionsObjectsByHashes(new String[]{txId});
            final boolean confirmed = this.checkTxConfirmed(transactions.get(0));
            if (confirmed) {
                final Long confirmation = this.getCurrency().getConfirmNum();
                return confirmation.intValue();
            }
        } catch (final ArgumentException e) {
            log.error("getConfirm errorm, txid:{}", txId, e);
        }
        return 0;

    }

    private boolean checkTxConfirmed(final jota.model.Transaction tx) {
        if (ObjectUtils.isEmpty(tx) || !InputValidator.isHash(tx.getHash())) {
            return false;
        }
        GetInclusionStateResponse stateResponse = null;
        boolean valid = false;
        try {
            stateResponse = this.command.getLatestInclusion(new String[]{tx.getHash()});
            valid = stateResponse.getStates()[0];
            if (tx.getValue() <= 0) {
                valid = false;
            }

        } catch (final Throwable e) {
            e.printStackTrace();
            log.error("isTxValid has an error,hash:{}", tx.getHash(), e);
        }
        return valid;
    }

    /**
     * 判断一笔交易是否要重新发送
     *
     * @param bundle
     * @return
     */
    public boolean isReattachable(final String bundle) {
        try {
            final FindTransactionResponse findTransactionResponse = this.command.findTransactions(null, null, null, new String[]{bundle});
            final GetInclusionStateResponse getInclusionStateResponse = this.command.getLatestInclusion(findTransactionResponse.getHashes());
            final boolean[] states = getInclusionStateResponse.getStates();
            final int index = ArrayUtils.indexOf(states, true);
            if (index >= 0) {
                return false;
            }
        } catch (final Throwable e) {
            log.error("There is a error when check bundle:{}", bundle, e);
        }
        return true;
    }

    public void replayBundle(final String txHash) {
        log.info("replayBundle :{} begin", txHash);
        try {
            this.command.replayBundle(txHash, 4, 14);
        } catch (final Throwable e) {
            log.error("There is a error when replayBundle txhash:{}", txHash, e);
        }
        log.info("replayBundle :{} end", txHash);

    }

}
