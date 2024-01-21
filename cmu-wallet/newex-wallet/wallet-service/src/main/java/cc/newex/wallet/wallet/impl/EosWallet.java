package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.EosCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.block.ActionData;
import io.eblock.eos4j.api.vo.block.BlockTransaction;
import io.eblock.eos4j.api.vo.block.TranscationTrx;
import io.eblock.eos4j.api.vo.block.TrxTranscation;
import io.eblock.eos4j.api.vo.block.TrxTranscationAction;
import io.eblock.eos4j.api.vo.transaction.GetTransaction;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EosWallet extends AbstractAccountWallet implements IWallet {

    @Autowired
    protected AccountTransactionService accountTransactionService;

    @Autowired
    AddressService addressService;

    @Autowired
    EosCommand command;

    Rpc rpc;

    @Value("${newex.eos.server}")
    private String eosServer;

    @Value("${newex.eos.withdraw.address}")
    private String withdrawAddress;

    public void setCommand(final EosCommand com) {
        this.command = com;
    }

    @PostConstruct
    public void init() {
        this.rpc = new Rpc(this.eosServer);
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.EOS;
    }

    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.EOS.getDecimal();
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

        final String addressStr = this.getWithdrawAddress() + ":" + address.getId();
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
        return this.getBalance(this.getWithdrawAddress(), this.getCurrency());
    }

    @Override
    protected BigDecimal getBalance(final String address, final CurrencyEnum currency) {
        try {
            final String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("getBalance err,currency {},account is null", currency.getName());
                return BigDecimal.ZERO;
            }
            final BigDecimal amount = this.rpc.getCurrencyBalance(account, address, this.getSymbol(currency.getContractAddress()));
            return amount;
        } catch (final Throwable e) {
            log.error("get currency:{} balance error", currency, e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public Long getBestHeight() {
        try {
            final ChainInfo chainInfo = this.rpc.getChainInfo();

            final Long height = chainInfo.getLastIrreversibleBlockNum();

            return height;
        } catch (final Throwable e) {
            log.error("getBestHeight currency:{} error", this.getCurrency(), e);

            return 0L;
        }

    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height) {
        List<TransactionDTO> dtos = new LinkedList<>();

        log.info("{} findRelatedTxs start, height:{}", this.getCurrency(), height);

        final Block block = this.rpc.getBlock(height.toString());
        if (ObjectUtils.isEmpty(block)) {
            log.error("findRelatedTxs error, block is null, height:{}", height);
            return dtos;
        }

        final List<BlockTransaction> blockTransactions = block.getBlockTransactions();
        if (blockTransactions == null) {
            log.error("findRelatedTxs error, tx is null, height:{}", height);
            return dtos;
        }

        final Long lastBlockNum = this.getBestHeight();

        final List<AccountTransaction> accountTransactions = blockTransactions.parallelStream()
                .map((blockTransaction) -> {

                    String state = blockTransaction.getStatus();
                    //如果不成功，则不返回
                    if (!state.equals("executed")) {
                        log.error("{} findRelatedTxs error, status is not executed, height:{}", this.getCurrency(), height);
                        return null;
                    }

                    TranscationTrx transcationTrx = blockTransaction.getTranscationTrx();
                    if (ObjectUtils.isEmpty(transcationTrx)) {
                        log.error("{} findRelatedTxs error, transcationTrx is null, height:{}", this.getCurrency(), height);
                        return null;
                    }

                    String txId = transcationTrx.getId();
                    if (ObjectUtils.isEmpty(txId)) {
                        log.error("{} findRelatedTxs error, txId is null, height:{}", this.getCurrency(), height);
                        return null;
                    }

                    TrxTranscation trxTranscation = transcationTrx.getTrxTranscation();
                    if (ObjectUtils.isEmpty(trxTranscation)) {
                        log.error("{} findRelatedTxs error, trxTranscation is null, height:{}", this.getCurrency(), height);
                        return null;
                    }

                    List<TrxTranscationAction> trxTranscationActions = trxTranscation.getTrxTranscationAction();
                    if (ObjectUtils.isEmpty(trxTranscationActions)) {
                        log.error("{} findRelatedTxs error, trxTranscationActions is null, height:{}", this.getCurrency(), height);
                        return null;
                    }

                    List<AccountTransaction> tmp = this.getAllActive(trxTranscationActions, txId, height, lastBlockNum);

                    return tmp;

                }).filter((tmp) -> !CollectionUtils.isEmpty(tmp))
                .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);

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

        log.info("{} findRelatedTxs end,height:{}", this.getCurrency(), height);

        return dtos;
    }

    /**
     * 获取所有action
     *
     * @param trxTranscationActions
     * @param txId
     * @param height
     * @param lastBlockNum
     * @return
     */
    private List<AccountTransaction> getAllActive(final List<TrxTranscationAction> trxTranscationActions, final String txId, final Long height, final Long lastBlockNum) {
        final List<AccountTransaction> accountTransactionActions = trxTranscationActions.parallelStream().map((action) -> {
            try {
                String actionName = action.getName();
                String actionAccount = action.getAccount();
                Object obj = action.getActionData();
                ActionData actionData = null;
                if (obj instanceof ActionData) {
                    actionData = (ActionData) obj;
                } else {
                    return null;
                }

                if (ObjectUtils.isEmpty(actionData) || ObjectUtils.isEmpty(actionData.getQuantity())) {
                    return null;
                }

                // 币种标识
                String mark = this.quantity2mark(actionData.getQuantity().toString());

                // 根据动态获取currency
                CurrencyEnum currency = CurrencyEnum.parseContract(this.getCurrency().getName() + "-" + actionAccount + "-" + mark);
                // 判断是否为已上线的eos合约
                if (ObjectUtils.isEmpty(currency)) {
                    return null;
                }

                // 判断是transfer
                if (!actionName.equals("transfer")) {
                    return null;
                }

                // 先检测是不是我们发出的交易
                this.updateWithdrawTXId(txId, currency);

                BigDecimal balance = this.quantity2balance(actionData.getQuantity().toString());

                // 如果Quantity的标示，与币种名称不一致，则为假币！
                if (!mark.equals(this.getSymbol(currency.getContractAddress()))) {
                    log.error("eos counterfeit money, mark: {}, currencyName: {}", mark, this.getSymbol(this.getCurrency().getContractAddress()));
                    return null;
                }

                String to = actionData.getTo().toString();
                if (ObjectUtils.isEmpty(to)) {
                    return null;
                }

                // 判断接受者为主地址
                if (ObjectUtils.isEmpty(actionData) || !to.equals(this.getWithdrawAddress())) {
                    return null;
                }

                String memo = actionData.getMemo();

                final Address address = this.getAddress(to, memo);

                if (ObjectUtils.isEmpty(address)) {
                    return null;
                }

                AccountTransaction accountTransaction = AccountTransaction.builder()
                        .txId(txId)
                        .blockHeight(height)
                        .address(address.getAddress())
                        .balance(balance)
                        .confirmNum(lastBlockNum - height + 1)
                        .biz(address.getBiz())
                        .currency(currency.getIndex())
                        .status((byte) Constants.WAITING)
                        .createDate(Date.from(Instant.now()))
                        .updateDate(Date.from(Instant.now()))
                        .build();

                return accountTransaction;
            } catch (Exception e) {
                log.error("eos trxTranscationActions error", e);
                return null;
            }
        }).filter((acTx) -> !ObjectUtils.isEmpty(acTx)).collect(Collectors.toList());

        return accountTransactionActions;
    }

    /**
     * 如果memo错误，默认转入10号账户，但是10号账户的地址需要提前生成
     *
     * @param toAddress
     * @param memo
     * @return
     */
    private Address getAddress(final String toAddress, final String memo) {
        final Long userId = 10L;

        final String addressStr = toAddress + ":" + memo;

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();

        // 验证eos地址
        Address address = this.addressService.getAddress(addressStr, table);

        // memo错误，默认转入10号账户
        if (ObjectUtils.isEmpty(address)) {
            log.error("invalid memo, toAddress: {}, memo: {}, transfer in No.10", toAddress, memo);

            address = this.addressService.getAddress(userId, this.getCurrency());
        }

        if (!ObjectUtils.isEmpty(address)) {
            final String mainAddress = address.getAddress().split(":")[0];

            if (!toAddress.equals(mainAddress)) {
                log.error("invalid address, toAddress: {}, memo: {}, mainAddress: {}", toAddress, memo, mainAddress);

                return null;
            }
        }

        return address;
    }

    protected BigDecimal quantity2balance(final String quantity) {
        final String split = " ";
        String balanceStr = null;
        if (quantity.contains(split)) {
            balanceStr = quantity.substring(0, quantity.indexOf(split));
        }
        return new BigDecimal(balanceStr);
    }

    protected String quantity2mark(final String quantity) {
        final String split = " ";
        String balanceStr = null;
        if (quantity.contains(split)) {
            balanceStr = quantity.substring(quantity.indexOf(split) + 1);
        }
        return balanceStr;
    }

    /**
     * 更新钱包中的币余额
     */
    @Override
    public void updateTotalCurrencyBalance() {
        log.info("updateTotalCurrencyBalance {} begin", this.getCurrency().getName());
        super.updateTotalCurrencyBalance();
        if (this.getCurrency() == CurrencyEnum.EOS) {
            CurrencyEnum.EOS_ASSET.parallelStream().forEach((currency) -> {
                log.info("update {} total Balance begin", currency.getName());
                final BigDecimal balance = this.getBalance(this.getWithdrawAddress(), currency);
                this.updateTotalCurrencyBalance(currency, balance);
                log.info("update {} total Balance end", currency.getName());

            });
        }

        log.info("updateTotalCurrencyBalance {} end", this.getCurrency().getName());

    }

    @Override
    public int getConfirm(final String txId) {
        final GetTransaction transaction = this.rpc.getTransaction(txId);
        final Long blockHeight = transaction.getBlockNum();
        final Long bestHeight = this.getBestHeight();
        final Long confirm = bestHeight - blockHeight + 1;
        return confirm.intValue();
    }

    @Override
    protected WithdrawTransaction buildTransaction(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

        //获取根账户地址
        final ShardTable addressTable = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final Address address = this.addressService.getAddress(this.getWithdrawAddress(), addressTable);

        final JSONObject signature = new JSONObject();

        signature.put("address", address);
        signature.put("from", this.getWithdrawAddress());
        signature.put("to", record.getAddress());
        final DecimalFormat fnum = new DecimalFormat("##0.0000");
        fnum.setRoundingMode(RoundingMode.CEILING);
        final String amount = fnum.format(record.getBalance());
        signature.put("amount", amount);
        final BigDecimal amountDB = new BigDecimal(amount);
        record.setBalance(amountDB);

        final ChainInfo info = this.rpc.getChainInfo();
        final Block block = this.rpc.getBlock(info.getLastIrreversibleBlockNum().toString());
        signature.put("headBlockTime", info.getHeadBlockTime().getTime());
        signature.put("lastIrreversibleBlockNum", info.getLastIrreversibleBlockNum());
        signature.put("refBlockPrefix", block.getRefBlockPrefix());
        signature.put("chainId", info.getChainId());

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

            final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());

            final String rawTransaction = signature.getString("rawTransaction");
            final String from = signature.getString("from");
            final String toAddr = signature.getString("to");
            final String[] addressAndTag = toAddr.split(":");
            if (addressAndTag.length != 2) {
                log.error("{} signTransaction error,addressAndTag.Len !=2 ", currency);
                return result;
            }
            final String to = addressAndTag[0];
            final String memo = addressAndTag[1];
            final String quantity = transaction.getBalance() + " " + currency.name();

            final Long headBlockTime = signature.getLong("headBlockTime");
            final Long lastIrreversibleBlockNum = signature.getLong("lastIrreversibleBlockNum");
            final Long refBlockPrefix = signature.getLong("refBlockPrefix");

            final String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("sendRawTransaction err,currency {},account is null", currency.getName());
                return "";
            }

            final Transaction t1 = this.rpc.transferLocalAsset(account, from, to, quantity, memo,
                    rawTransaction, headBlockTime, lastIrreversibleBlockNum, refBlockPrefix);
            if (!ObjectUtils.isEmpty(t1)) {
                result = t1.getTransactionId();
            }

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
            //不可自己给自己转账
            if (address.equals(this.getWithdrawAddress())) {
                return false;
            }
            //通过转化来判断地址是否合法，如果不合法会抛异常
            this.rpc.getAccount(address);
            return true;

        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", addressWithTag, e);
            return false;
        }

    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        super.updateTXConfirmation(this.getCurrency());
        if (this.getCurrency() == CurrencyEnum.EOS) {
            CurrencyEnum.EOS_ASSET.parallelStream().forEach(super::updateTXConfirmation);
        }
    }

    /**
     * 获取币种账户
     *
     * @param contractAddress
     * @return
     */
    protected String getAccount(final String contractAddress) {
        final String[] eosContranct = contractAddress.split("-");
        if (eosContranct.length != 3) {
            log.error("{} eosContranct.Len !=3，contractAddress: {} ", this.getCurrency(), contractAddress);
            return "";
        }
        return eosContranct[1];
    }

    /**
     * 获取币种标识
     *
     * @param contractAddress
     * @return
     */
    protected String getSymbol(final String contractAddress) {
        final String[] eosContranct = contractAddress.split("-");
        if (eosContranct.length != 3) {
            log.error("{} eosContranct.Len !=3，contractAddress: {} ", this.getCurrency(), contractAddress);
            return "";
        }
        return eosContranct[2];
    }

}
