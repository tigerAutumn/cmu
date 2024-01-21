package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.OntCommand;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.OntBalance;
import cc.newex.wallet.pojo.rpc.OntEvent;
import cc.newex.wallet.pojo.rpc.OntStates;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.WithdrawTransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.crypto.bip32.Deserializer;
import com.github.ontio.crypto.bip32.ExtendedPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class OntWallet extends AbstractAccountWallet implements IWallet {

    private ExtendedPublicKey PUB_KEY;

    @Value("${newex.ont.pubKey}")
    private String ontPubKey;

    @Autowired
    OntCommand command;

    @Value("${newex.ont.withdraw.address}")
    private String withdrawAddress;

    @Value("${newex.ong.claim.to.address}")
    private String claimToAddress;

    @Autowired
    AddressService addressService;
    @Autowired
    AccountTransactionService accountTransactionService;
    @Autowired
    WithdrawTransactionService withdrawTransactionService;

    public static String ONT_NATIVE_STATE_TYPE = "transfer";

    @PostConstruct
    public void init() {
        final Deserializer publicKeyDeserializer = ExtendedPublicKey.deserializer();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(this.ontPubKey)) {
            this.PUB_KEY = (ExtendedPublicKey) publicKeyDeserializer.deserialize(this.ontPubKey);
        }

    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ONT;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.ONT.getDecimal();
    }

    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());

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

        final com.github.ontio.common.Address addressOnt = com.github.ontio.common.Address.addressFromPubKey(this.PUB_KEY.cKDpub(44).cKDpub(currency.getIndex()).cKDpub(biz).cKDpub(userId.intValue()).cKDpub(index).hdKey.key);
        final String addressStr = addressOnt.toBase58();

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

    @Override
    public BigDecimal getBalance() {
        return this.getBalance(this.getCurrency());
    }

    @Override
    public Long getBestHeight() {
        final Integer height = this.command.getBlockCount("latest");
        return height.longValue();
    }

    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from, final boolean claim) {
        final ShardTable ontTable = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final BigDecimal gas = this.gas();

        final AddressExample addrExam = new AddressExample();
        addrExam.createCriteria().andAddressEqualTo(from);
        final ShardTable addressTable = ShardTable.builder().prefix(currency.getName()).build();
        final Address address = this.addressService.getAndLockOneByExample(addrExam, ontTable);
        //由于ontAsset都是由ont生成的私钥，所以都用ont
        address.setCurrency(this.getCurrency().getName());

        final AddressExample payerExam = new AddressExample();
        payerExam.createCriteria().andAddressEqualTo(this.withdrawAddress);
        final Address payer = this.addressService.getAndLockOneByExample(payerExam, ontTable);
        //由于ontAsset都是由ont生成的私钥，所以都用ont
        payer.setCurrency(this.getCurrency().getName());

        final JSONObject signature = new JSONObject();
        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("value", record.getBalance());
        signature.put("gas", gas);
        final BigDecimal gasPrice = this.gasPrice(currency);
        signature.put("gasPrice", gasPrice);
        signature.put("payer", payer);
        signature.put("claim", claim);

        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId("transfer")
                .signature(signature.toJSONString())
                .build();
        address.setNonce(address.getNonce() + 1);
        address.setUpdateDate(Date.from(Instant.now()));
        this.addressService.editById(address, ontTable);
        this.withdrawTransactionService.add(transaction, addressTable);
        OntWallet.log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        String result;
        try {
            final JSONObject signature = JSONObject.parseObject(transaction.getSignature());

            final String raw = signature.getString("rawTransaction");
            result = this.command.sendRawTransaction(raw, 0);
        } catch (final Throwable e) {
            OntWallet.log.error("sendRawTransaction error", e);
            result = "";
        }
        return result;
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        List<TransactionDTO> dtos = new LinkedList<>();
        log.info("ONT findRelatedTxs start,height:{}", height);
        final JSONArray event = this.command.getSmartcodeevent(height, 1);
        if (ObjectUtils.isEmpty(event)) {
            log.error("findRelatedTxs error, event is null,height:{}", height);
            return dtos;
        }
        //去除不规则的States
        for (final Object eventObject : event) {
            final LinkedHashMap eventMap = (LinkedHashMap) eventObject;
            final ArrayList<LinkedHashMap> notifyList = (ArrayList) eventMap.get("Notify");
            //使用 Iterator.remove 防止出现ConcurrentModificationException
            final Iterator<LinkedHashMap> notifyIterator = notifyList.iterator();
            while (notifyIterator.hasNext()) {
                final LinkedHashMap statesMap = notifyIterator.next();
                final Object statesObject = statesMap.get("States");
                if (!(statesObject instanceof ArrayList)) {
                    notifyIterator.remove();
                }
            }
        }
        final List<OntEvent> events = event.toJavaList(OntEvent.class);
        if (events == null) {
            log.error("findRelatedTxs error, events is null,height:{}", height);
            return dtos;
        }

        final Long lastBlockNum = this.getBestHeight();

        final List<AccountTransaction> accountTransactions = events.parallelStream().map((tx) -> {

            int state = tx.getState();
            //如果不成功，则不返回
            if (state != 1) {
                return null;
            }

            OntStates[] notifys = tx.getNotify();
            if (ObjectUtils.isEmpty(notifys)) {
                return null;
            }
            String toAddr = null;
            Long balance = null;
            OntStates notify = notifys[0];
            if (ObjectUtils.isEmpty(notify)) {
                return null;
            }
            CurrencyEnum currency = CurrencyEnum.parseContract(notify.getContractAddress());
            if (ObjectUtils.isEmpty(currency)) {
                return null;
            }

            //判断合约地址类型 数组长度 转账类型
            if (notify.getStates().length == 4
                    && ONT_NATIVE_STATE_TYPE.equals(notify.getStates()[0].toString())) {
                toAddr = (String) notify.getStates()[2];
                balance = Long.valueOf(String.valueOf(notify.getStates()[3]));
            }

            if (ObjectUtils.isEmpty(toAddr) || ObjectUtils.isEmpty(balance)) {
                return null;
            }

            String txId = tx.getTxHash();
            //先检测是不是我们发出的交易
            this.updateWithdrawTXId(txId, currency);
            this.updateAccountTransaction(txId, currency);

            ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

            Address address = this.addressService.getAddress(toAddr, table);
            if (ObjectUtils.isEmpty(address)) {
                return null;
            }

            AccountTransaction accountTransaction = AccountTransaction.builder()
                    .txId(txId)
                    .blockHeight(height)
                    .address(toAddr)
                    .balance(new BigDecimal(balance).divide(currency.getDecimal()))
                    .confirmNum(lastBlockNum - height + 1)
                    .biz(address.getBiz())
                    .currency(currency.getIndex())
                    .status((byte) Constants.WAITING)
                    .createDate(Date.from(Instant.now()))
                    .updateDate(Date.from(Instant.now())).build();

            return accountTransaction;
        }).filter((acTx) -> !ObjectUtils.isEmpty(acTx)).collect(Collectors.toList());

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
        log.info("ONT findRelatedTxs end,height:{}", height);

        return dtos;
    }

    @Override
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.isNotBlank(addressStr)) {
            try {
                com.github.ontio.common.Address.decodeBase58(addressStr);
                valid = true;
            } catch (final Exception e) {
                log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

    public BigDecimal getBalance(final CurrencyEnum currency) {
        final String currencyName = currency.getName();
        log.info("get {} Balance begin", currencyName);

        try {
            final AccountTransactionExample example = new AccountTransactionExample();
            example.createCriteria().andStatusLessThan((byte) Constants.CONFIRM).andAddressNotEqualTo(this
                    .getWithdrawAddress())
                    .andConfirmNumGreaterThanOrEqualTo(currency.getDepositConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

            final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
            final List<AccountTransaction> accountTransactions = this.accountTransactionService.getByExample(example, table);
            final Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
            addresses.add(this.withdrawAddress);

            final BigDecimal total = addresses.parallelStream().map((address) -> this.getBalance(address, currency)).reduce(BigDecimal.ZERO, BigDecimal::add);
            log.info("get {} Balance end", currencyName);

            return total;
        } catch (final Throwable e) {
            log.error("get {} Balance error", currencyName, e);
        }
        return BigDecimal.ZERO;

    }

    @Override
    public BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        BigDecimal balance = new BigDecimal("0");
        try {
            final OntBalance ontBalance = this.command.getBalance(address);
            if (ObjectUtils.isEmpty(ontBalance)) {
                return balance;
            }
            final String balanceStr = this.getOntBalance(ontBalance, currency);
            balance = new BigDecimal(balanceStr).divide(currency.getDecimal());
        } catch (final Throwable e) {
            OntWallet.log.error("getBalance error, address:{}", address, e);
        }
        return balance;
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        return this.buildTransaction(record, this.withdrawAddress, false);
    }

    protected BigDecimal gas() {
        return new BigDecimal("20000");
    }

    protected BigDecimal gasPrice(final CurrencyEnum currency) {
        return new BigDecimal("500");
    }

    @Override
    public int getConfirm(final String txId) {
        final Long blockHeight = this.getHeightByTxHash(txId);
        final Long bestHeight = this.getBestHeight();
        final Long confirm = bestHeight - blockHeight + 1;
        return confirm.intValue();
    }

    public Long getHeightByTxHash(final String txId) {
        return Long.valueOf(this.command.getblockheightbytxhash(txId));
    }

    /**
     * 更新钱包中的币余额
     */
    @Override
    public void updateTotalCurrencyBalance() {
        OntWallet.log.info("updateTotalCurrencyBalance ont begin");
        CurrencyEnum.ONT_ASSET.parallelStream().forEach((currency) -> {
            OntWallet.log.info("update {} total Balance begin", currency.getName());
            final BigDecimal balance = this.getBalance(currency);
            this.updateTotalCurrencyBalance(currency, balance);
            OntWallet.log.info("update {} total Balance end", currency.getName());

        });

        OntWallet.log.info("updateTotalCurrencyBalance ont end");

    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    //ont or ong
    public String getOntBalance(final OntBalance ontBalance, final CurrencyEnum currency) {
        if (currency == CurrencyEnum.ONT) {
            return ontBalance.getOnt();
        }
        return ontBalance.getOng();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void transfer(final String address, final CurrencyEnum currency, final Date deadline) {
        final BigDecimal balance = this.getBalance(address, currency);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            final WithdrawRecord record = WithdrawRecord.builder()
                    .address(this.withdrawAddress)
                    .balance(balance)
                    .currency(currency.getIndex())
                    .build();
            final WithdrawTransaction transferTransaction = this.buildTransaction(record, address, false);
            if (transferTransaction == null) {
                log.error(" {} transfer error, buildTransaction failed", currency.getName());
                return;
            }
            //把交易推送到待签名队列
            final String val = JSONObject.toJSONString(transferTransaction);
            //eth 只支持单签，所以直接用第二台机器签名
            REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
        }

        final AccountTransaction transaction = new AccountTransaction();
        transaction.setStatus((byte) Constants.SIGNING);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andAddressEqualTo(address)
                .andConfirmNumGreaterThan(currency.getDepositConfirmNum())
                .andCreateDateLessThan(deadline);

        this.accountTransactionService.editByExample(transaction, example, table);
        log.info("{} transfer success, id:{}", currency.getName(), transaction.getId());

    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        CurrencyEnum.ONT_ASSET.parallelStream().forEach(super::updateTXConfirmation);
    }

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void claimOng(final CurrencyEnum currency) {

        final String claimerAddr = this.withdrawAddress;
        final String recvAddr = this.claimToAddress;

        final String uOngAmt = this.command.getUnboundong(claimerAddr);
        if (ObjectUtils.isEmpty(uOngAmt)) {
            log.info("claimOng fail, uOngAmt is null !");
            return;
        }

        if (new BigInteger(uOngAmt).compareTo(new BigInteger("0")) > 0) {

            this.sendWithdraw(recvAddr, uOngAmt, currency, claimerAddr, true);
            log.info("claimOng success, unclaimed ong is:{}", uOngAmt);
        } else {
            log.info("claimOng fail, uOngAmt less than 0 !");
        }

    }

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void localWithdrawOnt() {
        //转账1个ont
        final String localWithdrawBalance = "1";
        this.sendWithdraw(this.withdrawAddress, localWithdrawBalance, this.getCurrency(), this.withdrawAddress, false);
        log.info("localWithdrawOnt success");

    }

    //发送一笔提现交易
    private void sendWithdraw(final String toAddress, final String balance, final CurrencyEnum currency, final String fromAddress, final boolean claim) {
        final WithdrawRecord record = WithdrawRecord.builder()
                .address(toAddress)
                .balance(new BigDecimal(balance).divide(currency.getDecimal()))
                .currency(currency.getIndex())
                .build();
        final WithdrawTransaction transferTransaction = this.buildTransaction(record, fromAddress, claim);
        if (transferTransaction == null) {
            log.error(" {} sendWithdraw error, buildTransaction failed", currency.getName());
            return;
        }
        //把交易推送到待签名队列
        final String val = JSONObject.toJSONString(transferTransaction);
        //提取Ong,直接用第二台机器签名
        REDIS.lPush(Constants.WALLET_WITHDRAW_SIG_SECOND_KEY, val);
        log.info("sendWithdraw success");
    }
}
