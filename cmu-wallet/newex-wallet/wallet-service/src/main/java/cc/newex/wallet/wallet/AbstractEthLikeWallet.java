package cc.newex.wallet.wallet;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.EthLikeCommand;
import cc.newex.wallet.config.PubKeyConfig;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.EthLikeBlock;
import cc.newex.wallet.pojo.rpc.EthRawTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.WithdrawRecordService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.utils.EthereumUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.ethereum.core.Transaction;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.TRANSFER;
import static cc.newex.wallet.utils.Constants.WITHDRAW;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Data
abstract public class AbstractEthLikeWallet extends AbstractAccountWallet implements IWallet {

    public static final String ETH_ADDRESS_PREFIX = "0x";
    //eth地址中保留的币，转账和erc20划转要用
    public BigDecimal RESERVED;
    @Autowired
    protected PubKeyConfig pubKeyConfig;
    protected EthLikeCommand command;
    protected Long height = 0L;
    @Autowired
    protected AccountTransactionService accountTransactionService;
    @Autowired
    protected WithdrawTransactionService withdrawTransactionService;
    @Autowired
    protected WithdrawRecordService recordService;
    @Autowired
    protected AddressService addressService;
    private String withdrawAddress;

    /**
     * 上次更新总余额的时间戳
     */
    private long lastUpdateTotalBalance = 0;

//    @Autowired
//    ApplicationContext applicationContext;

    @Override
    protected boolean shouldUpdateTotalBalance() {
        final long now = System.currentTimeMillis();
        //6个小时整体刷新余额
        final long sixHours = 6 * 60 * 60 * 1000;
        if (now - this.lastUpdateTotalBalance > sixHours) {
            this.lastUpdateTotalBalance = now;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    public void setCommand(final EthLikeCommand com) {
        this.command = com;
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
        AbstractEthLikeWallet.log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());

        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;
        /**
         * 获取该userId在biz业务线下面已经生成了多少地址
         */
        if (!CollectionUtils.isEmpty(addressList)) {

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
        hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();
        final ECKey ecKey = this.pubKeyConfig.NODE2.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        final ECPoint point = ecKey.getPubKeyPoint();
        final org.ethereum.crypto.ECKey ethEcKey = org.ethereum.crypto.ECKey.fromPublicOnly(point);
        final String addressStr = ETH_ADDRESS_PREFIX + Hex.toHexString(ethEcKey.getAddress());

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
     * 返回当前币种钱包中的余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.getCurrency());
    }

    public BigDecimal getBalance(final CurrencyEnum currency) {
        final String currencyName = currency.getName();
        log.info("get {} Balance begin", currencyName);

        try {

            final AddressExample example = new AddressExample();
            final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
            final PageInfo page = new PageInfo();
            final int size = 100;
            page.setPageSize(size);
            page.setSortItem("id");
            page.setSortType(PageInfo.SORT_TYPE_ASC);
            page.setStartIndex(0);
            BigDecimal totalBalance = BigDecimal.ZERO;

            while (true) {
                final List<Address> addresses = this.addressService.getByPage(page, example, table);
                if (CollectionUtils.isEmpty(addresses)) {
                    break;
                }
                final BigDecimal balance = addresses.parallelStream().map((address) -> {
                    BigDecimal res = this.getBalance(address.getAddress());
                    address.setBalance(res);
                    return res;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
                totalBalance = totalBalance.add(balance);
                this.addressService.batchAddOnDuplicateKey(addresses, table);
                if (addresses.size() < size) {
                    break;
                }
                page.setStartIndex(page.getStartIndex() + size);
            }
            return totalBalance;
        } catch (final Throwable e) {
            AbstractEthLikeWallet.log.error("get {} Balance error", currencyName, e);

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
        final String height = this.command.getBlockNumber();
        this.height = EthereumUtil.hexToLong(height);
        return this.height;
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        final String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);
        final EthLikeBlock block = this.command.getBlockByHeight(ETH_ADDRESS_PREFIX + Long.toHexString(height), true);
        if (ObjectUtils.isEmpty(block)) {
            log.error("{} findRelatedTxs error, block is null", currencyName);
            return null;
        }
        final EthRawTransaction[] txListArray = block.getTransactions();
        if (txListArray.length == 0) {
            log.info("{} findRelatedTxs empty, height:{} end", currencyName, height);
            return new LinkedList<>();
        }

        //存储参与交易的地址，方便更新地址中的余额
        final Set<String> relatedAddress = new ConcurrentSet<>();
        final List<AccountTransaction> accountTransactions = Arrays.asList(txListArray).parallelStream().map((tx) -> {
            AccountTransaction accountTransaction = this.getAccountTx(tx, height);
            Address address = this.searchAddress(tx.getFrom(), this.getCurrency());
            //收集发送地址
            if (!ObjectUtils.isEmpty(address)) {
                relatedAddress.add(address.getAddress());
            }
            return accountTransaction;
        }).filter((acTx) -> {
            if (!ObjectUtils.isEmpty(acTx)) {
                //收集接收地址
                relatedAddress.add(acTx.getAddress());
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        //更新币种余额
        final AbstractEthLikeWallet selfReference = this.getSelf(this.getClass());
        selfReference.updateAddressBalance(relatedAddress);
        log.info("{} findRelatedTxs, height:{} end", currencyName, height);
        return this.getTransactionDTOS(accountTransactions);
    }

    protected List<TransactionDTO> getTransactionDTOS(final List<AccountTransaction> accountTransactions) {
        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(accountTransactions)) {
            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.accountTransactionService.batchAddOnDuplicateKey(accountTransactions, table);
            dtos = accountTransactions.parallelStream().map(this::convertAccountTxToDto).collect(Collectors.toList());
        }
        return dtos;
    }

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void updateAddressBalance(final Set<String> relatedAddress) {
        //更新币种余额
        BigDecimal deltaBalance = BigDecimal.ZERO;
        for (final String addressStr : relatedAddress) {
            final BigDecimal balance = this.getBalance(addressStr);
            final Address address = this.searchAddress(addressStr, this.getCurrency());
            deltaBalance = deltaBalance.add(balance.subtract(address.getBalance()));
            address.setBalance(balance);
            this.addressService.editById(address, ShardTable.builder().prefix(address.getCurrency()).build());
        }
        this.updateCurrencyDeltaBalance(this.getCurrency(), deltaBalance);
    }

    @Override
    protected void updateWithdrawTXId(final String txId, final CurrencyEnum currency) {
        super.updateWithdrawTXId(txId, currency);
        this.updateAccountTransaction(txId, currency);
    }

    protected AccountTransaction getAccountTx(final EthRawTransaction transaction, final Long height) {
        final String txId = transaction.getHash();
        //先检测是不是我们发出的交易
        this.updateWithdrawTXId(txId, this.getCurrency());

        final String addressStr = transaction.getTo();
        final Address address = this.searchAddress(addressStr, this.getCurrency());
        if (ObjectUtils.isEmpty(address)) {
            return null;
        }

        if (this.checkInternalTransferTx(transaction.getFrom(), txId)) {
            return null;
        }

        final Long confirm = this.height - height + 1;
        final BigDecimal txValue = new BigDecimal(EthereumUtil.hexToBigInteger(transaction.getValue()));
        final AccountTransaction acTx = AccountTransaction.builder()
                .address(addressStr)
                .balance(txValue.divide(this.getDecimal()))
                .biz(address.getBiz())
                .txId(txId)
                .blockHeight(height)
                .confirmNum(confirm)
                .status((byte) Constants.WAITING)
                .currency(this.getCurrency().getIndex())
                .createDate(Date.from(Instant.now()))
                .updateDate(Date.from(Instant.now()))
                .build();
        if (addressStr.equals(this.withdrawAddress)) {
            acTx.setStatus((byte) Constants.CONFIRM);
        }

        return acTx;
    }

    protected Address searchAddress(final String addressStr, final CurrencyEnum currency) {

        if (!StringUtils.hasText(addressStr)) {
            return null;
        }
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        final Address address = this.addressService.getAddress(addressStr, table);

        return address;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void transfer(final String address, final CurrencyEnum currency, final Date deadline) {

        final BigDecimal gasPrice = this.gasPrice(currency);
        final BigDecimal transferGasPrice = this.transferGasPrice(currency);

        if (gasPrice.compareTo(transferGasPrice) > 0) {
            log.info("gas price is too high, wait for a while");
            return;
        }

        BigDecimal balance = this.getBalance(address, currency);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andAddressEqualTo(address)
                .andConfirmNumGreaterThan(currency.getDepositConfirmNum())
                .andCreateDateLessThan(deadline);
        //balance > 2 * RESERVED
        if (balance.compareTo(this.RESERVED.add(this.RESERVED)) > 0) {
            final CurrencyEnum mainCurrency = CurrencyEnum.toMainCurrency(currency);
            if (mainCurrency == currency) {
                //地址中保留的币，用来付手续费
                balance = balance.subtract(this.RESERVED);
            }
            final WithdrawRecord record = WithdrawRecord.builder()
                    .address(this.withdrawAddress)
                    .balance(balance)
                    .currency(currency.getIndex())
                    .build();

            final WithdrawTransaction transferTransaction = this.buildTransaction(record, address, TRANSFER);
            if (transferTransaction == null) {
                log.error(" {} transfer error, buildTransaction failed", currency.getName());
                return;
            }
            //把交易推送到待签名队列
            final String val = JSONObject.toJSONString(transferTransaction);
            //eth 只支持单签，所以直接用第二台机器签名
            REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
            final AccountTransaction transaction = new AccountTransaction();
            transaction.setStatus((byte) Constants.SIGNING);
            this.accountTransactionService.editByExample(transaction, example, table);
            log.info("{} buildTransaction success, id:{}", currency.getName(), transferTransaction.getId());
        } else {
            log.info("{} balance of {} is {}", currency.getName(), address, balance);
            final AccountTransaction transaction = new AccountTransaction();
            transaction.setStatus((byte) Constants.SIGNING);
            this.accountTransactionService.editByExample(transaction, example, table);
        }
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {

        return this.buildTransaction(record, this.withdrawAddress, WITHDRAW);
    }

    protected BigDecimal gas() {
        return new BigDecimal("0.00000000000042");
    }

    protected BigDecimal gasPrice(final CurrencyEnum currency) {

        final BigDecimal minGasPrice = new BigDecimal("0.000000012");
        final BigDecimal maxGasPrice = new BigDecimal("0.0000001");
        final String gasPriceStr = this.command.getGasPrice();

        final BigDecimal decimal;

        if (CurrencyEnum.isErc20(currency)) {
            decimal = CurrencyEnum.ETH.getDecimal();
        } else {
            decimal = this.getDecimal();
        }
        BigDecimal gasPrice = new BigDecimal(EthereumUtil.hexToDouble(gasPriceStr)).divide(decimal);

        if (gasPrice.compareTo(minGasPrice) < 0) {
            gasPrice = minGasPrice;
        } else if (gasPrice.compareTo(maxGasPrice) > 0) {
            gasPrice = maxGasPrice;
        }
        return gasPrice;

    }

    protected BigDecimal transferGasPrice(final CurrencyEnum currency) {
        return new BigDecimal("0.00000003");
    }

    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from, final String type) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final BigDecimal gas = this.gas();

        final AddressExample addrExam = new AddressExample();
        addrExam.createCriteria().andAddressEqualTo(from);
        final ShardTable addressTable;
        addressTable = ShardTable.builder().prefix(CurrencyEnum.toMainCurrency(currency).getName()).build();

        final Address address = this.addressService.getAndLockOneByExample(addrExam, addressTable);
        final Long nonce = this.getAddressNonce(from);
        //校正nonce
        if (address.getNonce() < nonce) {
            address.setNonce(nonce.intValue());
        } else if (address.getNonce() > nonce) {
            log.error("{} database nonce is {} , and real nonce is {}", address.getAddress(), address.getNonce(), nonce);
            if (TRANSFER.equals(type)) {
                return null;
            }
        }

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("value", record.getBalance());
        signature.put("gas", gas);

        signature.put("nonce", address.getNonce());
        final BigDecimal gasPrice = this.gasPrice(currency);
        signature.put("gasPrice", gasPrice);
        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId(type)
                .signature(signature.toJSONString())
                .build();
        address.setNonce(address.getNonce() + 1);
        address.setUpdateDate(Date.from(Instant.now()));
        this.addressService.editById(address, addressTable);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);
        AbstractEthLikeWallet.log.info("{} buildTransaction end", currency.getName());
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
            result = this.command.sendRawTransaction(raw);
        } catch (final Throwable e) {
            AbstractEthLikeWallet.log.error("sendRawTransaction error", e);
            result = "";
        }
        return result;
    }

    /**
     * 获得区块hash
     *
     * @param height
     * @return
     */
    @Override
    public String getBlockHash(final Long height) {
        final EthLikeBlock block = this.command.getBlockByHeight(ETH_ADDRESS_PREFIX + Long.toHexString(height), true);
        final String hash = block.getHash();
        return hash;
    }

    public EthRawTransaction getTransaction(String txId) {
        final int dashIndex = txId.indexOf(StringUtil.DASH);
        if (dashIndex >= 0) {
            txId = txId.substring(0, dashIndex);
        }
        return this.command.getTransaction(txId);
    }

    public BigDecimal getBalance(final String address) {
        return this.getBalance(address, this.getCurrency());
    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        final String tranAmount = this.command.getBalance(address, "latest");
        final BigDecimal amount = new BigDecimal(EthereumUtil.hexToBigInteger(tranAmount));
        return amount.divide(this.getDecimal());
    }

    public Long getAddressNonce(final String address) {
        final String nonceStr = this.command.getAddressNonce(address, "pending");
        final long nonce = EthereumUtil.hexToLong(nonceStr);
        return nonce;
    }

    @Override
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                final String rex = "^0x[a-fA-F0-9]{40}$";
                if (addressStr.matches(rex)) {
                    valid = true;
                }
            } catch (final AddressFormatException e) {
                AbstractEthLikeWallet.log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

    @Override
    public int getConfirm(final String txId) {
        final EthRawTransaction transaction = this.getTransaction(txId);
        if (ObjectUtils.isEmpty(transaction)) {
            return -1;

        } else {
            final Long blockHeight = EthereumUtil.hexToLong(transaction.getBlockNumber());
            final Long bestHeight = this.getBestHeight();
            final Long confirm = bestHeight - blockHeight + 1;
            return confirm.intValue();
        }

    }

    @Override
    public String getTxId(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        String raw = signature.getString("rawTransaction");
        if (raw.startsWith(ETH_ADDRESS_PREFIX)) {
            raw = raw.replace(ETH_ADDRESS_PREFIX, "");
        }
        final Transaction ethTransaction = new Transaction(Hex.decode(raw));
        final String hash = ETH_ADDRESS_PREFIX + Hex.toHexString(ethTransaction.getHash());
        return hash;
    }
}
