package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.ECKey;
import org.spongycastle.math.ec.ECPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.tron.TronWalletApi;
import org.tron.protos.Protocol;
import org.tron.wallet.util.ByteArray;
import sdk.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
public class TronWallet extends AbstractEthLikeWallet implements IWallet {

    @Value("${newex.tron.withdraw.address}")
    private String withdrawAddress;

    protected Long height = 0L;

    @Value("${newex.tron.server}")
    private String tronServer;

    private Bip32Node tronNode;

    @Value("${newex.tron.pubkey}")
    private String tronPubkey;

    @PostConstruct
    public void init() {
        log.info("tronserver url = {}", this.tronServer);
        TronWalletApi.init(this.tronServer);

        if (StringUtils.isNotBlank(this.tronPubkey)) {
            this.tronNode = Bip32Node.decode(this.tronPubkey);
        }

    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.TRX;
    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.TRX.getDecimal();
    }

    @Override
    public Long getBestHeight() {
        this.height = TronWalletApi.getBlock(-1).getBlockHeader().getRawData().getNumber();
        return this.height;
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        return this.buildTransaction(record, this.withdrawAddress, Constants.WITHDRAW);
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record, final String from, final String type) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        final AddressExample addrExam = new AddressExample();
        addrExam.createCriteria().andAddressEqualTo(from);
        final ShardTable addressTable;
        addressTable = ShardTable.builder().prefix(CurrencyEnum.toMainCurrency(currency).getName()).build();

        final Address address = this.addressService.getAndLockOneByExample(addrExam, addressTable);
        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", from);
        signature.put("to", record.getAddress());
        signature.put("value", record.getBalance());
        signature.put("block", ByteArray.toHexString(TronWalletApi.getBlock(-1).toByteArray()));
        final WithdrawTransaction transaction = WithdrawTransaction.builder()
                .balance(record.getBalance())
                .currency(currency.getIndex())
                .status(Constants.SIGNING)
                .txId(type)
                .signature(signature.toJSONString())
                .build();
        address.setUpdateDate(Date.from(Instant.now()));
        this.addressService.editById(address, addressTable);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        this.withdrawTransactionService.add(transaction, table);
        log.info("{} buildTransaction end", currency.getName());
        return transaction;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void transfer(final String address, final CurrencyEnum currency, final Date deadline) {
        final BigDecimal balance = this.getBalance(address, currency);

        //tron地址中的钱如果小于0.1个，会转账失败
        if (balance.compareTo(new BigDecimal("0.1")) > 0) {
            final WithdrawRecord record = WithdrawRecord.builder()
                    .address(this.withdrawAddress)
                    .balance(balance)
                    .currency(currency.getIndex())
                    .build();
            final WithdrawTransaction transferTransaction = this.buildTransaction(record, address, Constants.TRANSFER);
            if (transferTransaction == null) {
                log.error(" {} transfer error, buildTransaction failed", currency.getName());
                return;
            }
            //把交易推送到待签名队列
            final String val = JSONObject.toJSONString(transferTransaction);
            //tron 只支持单签，所以直接用第二台机器签名
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
    public boolean checkAddress(final String addressStr) {
        return TronWalletApi.addressValid(addressStr);
    }

    @Override
    public String sendRawTransaction(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        final String raw = signature.getString("rawTransaction");
        try {
            final Protocol.Transaction signedTx = Protocol.Transaction.parseFrom(ByteArray.fromHexString(raw));
            final boolean sendResult = TronWalletApi.broadcastTransaction(signedTx.toByteArray());
            if (sendResult) {
                return TronWalletApi.getTransactionHash(signedTx);
            }
            log.info("tron broadcast transaction result = {}", sendResult);
            return "";
        } catch (final Throwable e) {
            log.error("");
            return "";
        }
    }

    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;

        //获取该userId在biz业务线下面已经生成了多少地址
        if (!CollectionUtils.isEmpty(addressList)) {

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
         * tron 的前缀是41
         * hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();
        final ECKey ecKey = this.tronNode.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        final ECPoint point = ecKey.getPubKeyPoint();

        final String addressStr = TronWalletApi.getAddress(point);

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
     * 这个方法的名字太笼统，主要干了下面几件事：
     * 1. 通过区块头查询当前区块交易列表
     * 2. 遍历交易列表：查询充值记录，用txid查询是不是系统发出的交易，更新状态为已确认，更新系统划转的交易记录为已确认
     * 3. 更新平台币种余额
     *
     * @param height
     * @return
     */
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        log.info("tron findRelatedTxs height = {} ", height);
        final Protocol.Block block = TronWalletApi.getBlock(height);
        final List<Protocol.Transaction> transactionsList = block.getTransactionsList();
        if (CollectionUtils.isEmpty(transactionsList)) {
            log.error("tron findRelatedTxs height={} is empty", height);
            return new ArrayList<>();
        }
        //记录是不是系统中的地址的提现记录
        final Set<String> relatedAddress = new ConcurrentSet<>();
        //查询区块中和系统地址相关的充值交易
        final List<AccountTransaction> accountTransactions = transactionsList.parallelStream().map(transaction -> {
            //查询充值记录
            AccountTransaction accountTx = this.getAccountTx(transaction, height);
            //查询提现地址列表
            Address address = this.searchAddress(TronWalletApi.getFrom(transaction), this.getCurrency());
            //收集提现地址
            if (!ObjectUtils.isEmpty(address)) {
                relatedAddress.add(address.getAddress());
            }
            return accountTx;
        }).filter((acTx) -> {
            if (!ObjectUtils.isEmpty(acTx)) {
                //收集接收地址
                relatedAddress.add(acTx.getAddress());
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        //更新币种余额
        this.updatePlatformWalletBalance(relatedAddress);
        return super.getTransactionDTOS(accountTransactions);
    }

    private void updatePlatformWalletBalance(final Set<String> relatedAddress) {
        final TronWallet selfReference = this.getSelf(this.getClass());
        selfReference.updateAddressBalance(relatedAddress);
    }

    private AccountTransaction getAccountTx(final Protocol.Transaction transaction, final Long height) {
        final String txId = TronWalletApi.getTransactionHash(transaction);
        //更新已有的自己发出去的提现记录，更新状态为已确认
        this.updateWithdrawTXId(txId, this.getCurrency());
        final String toAddress = TronWalletApi.getToAddress(transaction);
        if (StringUtils.isEmpty(toAddress)) {
            return null;
        }
        final Address address = this.searchAddress(toAddress, this.getCurrency());
        if (ObjectUtils.isEmpty(address)) {
            return null;
        }
        if (this.checkInternalTransferTx(TronWalletApi.getFrom(transaction), txId)) {
            return null;
        }

        final Long confirm = this.height - height + 1;
        final BigDecimal txValue = new BigDecimal(TronWalletApi.getAmount(transaction));
        final AccountTransaction acTx = AccountTransaction.builder()
                .address(toAddress)
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
        if (toAddress.equals(this.withdrawAddress)) {
            acTx.setStatus((byte) Constants.CONFIRM);
        }
        return acTx;

    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        final Long tranAmount = TronWalletApi.queryAccount(address).getBalance();
        final BigDecimal amount = new BigDecimal(tranAmount);
        return amount.divide(this.getDecimal());
    }

    @Override
    public int getConfirm(final String txId) {
        final Optional<Protocol.TransactionInfo> transactionOptional = TronWalletApi.getTransactionInfoById(txId);
        if (!transactionOptional.isPresent()) {
            return -1;
        }
        final Protocol.TransactionInfo transaction = transactionOptional.get();
        final long blockHeight = transaction.getBlockNumber();
        final Long bestHeight = this.getBestHeight();
        final Long confirm = bestHeight - blockHeight + 1;
        return confirm.intValue();
    }
}
