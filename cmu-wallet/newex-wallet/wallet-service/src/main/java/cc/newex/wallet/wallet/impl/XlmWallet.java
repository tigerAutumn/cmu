package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractAccountWallet;
import com.alibaba.fastjson.JSONObject;
import com.ripple.core.coretypes.uint.UInt32;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.stellar.sdk.Account;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.MemoId;
import org.stellar.sdk.MemoText;
import org.stellar.sdk.Network;
import org.stellar.sdk.Server;
import org.stellar.sdk.requests.AccountsRequestBuilder;
import org.stellar.sdk.requests.PaymentsRequestBuilder;
import org.stellar.sdk.requests.RequestBuilder;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.LedgerResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import org.stellar.sdk.responses.TransactionResponse;
import org.stellar.sdk.responses.operations.CreateAccountOperationResponse;
import org.stellar.sdk.responses.operations.OperationResponse;
import org.stellar.sdk.responses.operations.PaymentOperationResponse;
import org.stellar.sdk.xdr.OperationType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cc.newex.wallet.utils.Constants.CONFIRM;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Slf4j
@Component
public class XlmWallet extends AbstractAccountWallet {

    @Value("${newex.xlm.server}")
    private String xlmServer;

    @Value("${newex.xlm.withdraw.address}")
    private String withdrawAddress;

    Server server;
    OkHttpClient httpClient;

    private Account ACCOUNT;

    @PostConstruct
    public void init() {
        this.server = new Server(this.xlmServer);
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor((chain) -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("referer", this.xlmServer);

                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();

        final OkHttpClient submitHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(65, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor((chain) -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("referer", this.xlmServer);

                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();

        this.server.setHttpClient(this.httpClient);
        this.server.setSubmitHttpClient(submitHttpClient);

    }

    static {
        Network.usePublicNetwork();
    }

    @Override
    public String getWithdrawAddress() {
        return this.withdrawAddress;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XLM;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.XLM.getDecimal();
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
            final AccountsRequestBuilder accountsRequest = this.server.accounts();
            final AccountResponse response = accountsRequest.account(KeyPair.fromAccountId(address));
            final AccountResponse.Balance[] balances = response.getBalances();
            for (final AccountResponse.Balance b : balances) {
                if (b.getAssetType().equals("native")) {
                    amount = new BigDecimal(b.getBalance());
                    break;
                }
            }
            return amount;
        } catch (final Throwable e) {
            log.error("get balance error, currency: {}, address: {}", this.getCurrency().getName(), address, e);
            return amount;
        }
    }

    @Override
    public Long getBestHeight() {
        try {
            final Page<LedgerResponse> ledgerResponsePage = this.server.ledgers().limit(1).order(RequestBuilder.Order.DESC).execute();
            final ArrayList<LedgerResponse> records = ledgerResponsePage.getRecords();
            if (records.size() == 0) {
                throw new RuntimeException("XLM can not get current page");
            }
            return Long.valueOf(records.get(0).getPagingToken());
        } catch (final Throwable e) {
            log.error("getBestHeight error, currency: {}", this.getCurrency().getName(), e);
            return 0L;
        }

    }

    public Long getSequence() {
        final long current;
        try {
            final Page<LedgerResponse> ledgerResponsePage = this.server.ledgers().limit(1).order(RequestBuilder.Order.DESC).execute();
            final ArrayList<LedgerResponse> records = ledgerResponsePage.getRecords();
            if (records.size() == 0) {
                throw new RuntimeException("XLM can not get current page");
            }
            current = records.get(0).getSequence();

        } catch (final Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("XLM can not get current page");
        }
        return current;
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(Long height) {
        final String currencyName = this.getCurrency().getName();
        log.info("{} findRelatedTxs, height:{} begin", currencyName, height);
        final int pageSize = 100;
        final List<AccountTransaction> accountTransactions = new LinkedList<>();
        List<TransactionDTO> dtos = new LinkedList<>();
        int offset = 0;
        try {
            do {
                final PaymentsRequestBuilder paymentsRequestBuilder = this.server.payments();
                final Page<OperationResponse> responsePage = paymentsRequestBuilder.cursor(height.toString()).forAccount(
                        KeyPair.fromAccountId(this.withdrawAddress)).limit(pageSize).execute();
                final ArrayList<OperationResponse> records = responsePage.getRecords();
                offset = records.size();
                if (offset == 0) {
                    break;
                }
                final List<AccountTransaction> tmp = records.parallelStream()
                        .map((object) -> this.convertFromResponse(object))
                        .filter(tx -> tx != null)
                        .collect(Collectors.toList());
                accountTransactions.addAll(tmp);

                //得到最新一条的pageinToken
                height = Long.valueOf(records.get(records.size() - 1).getPagingToken());
                //offset % 100 == 0说明交易可能没有获取完毕。
            } while (offset > 0 && offset % 100 == 0);

            if (!CollectionUtils.isEmpty(accountTransactions)) {
                final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
                this.accountTransactionService.batchAddOnDuplicateKey(accountTransactions, table);
                dtos = accountTransactions.parallelStream().map(this::convertAccountTxToDto).collect(Collectors.toList());
            }
            log.info("{} findRelatedTxs, height:{} end", currencyName, height);
            return dtos;
        } catch (final Throwable e) {
            log.error("{} findRelatedTxs, height:{} err:", currencyName, height, e);
            return dtos;
        }
    }

    private AccountTransaction convertFromResponse(final OperationResponse response) {
        if (response == null) {
            return null;
        }

        try {
            //先检测是不是我们发出的交易
            final String txId = response.getTransactionHash();
            this.updateWithdrawTXId(txId, this.getCurrency());

            final BigDecimal amount;
            if (response instanceof CreateAccountOperationResponse) {
                final CreateAccountOperationResponse createResponse = (CreateAccountOperationResponse) response;
                if (!this.withdrawAddress.equals(createResponse.getAccount().getAccountId())) {
                    return null;
                }
                amount = BigDecimal.valueOf(Double.valueOf(createResponse.getStartingBalance()));
            } else if (response instanceof PaymentOperationResponse) {
                final PaymentOperationResponse paymentResponse = (PaymentOperationResponse) response;
                if (!this.withdrawAddress.equals(paymentResponse.getTo().getAccountId())) {
                    return null;
                }
                amount = BigDecimal.valueOf(Double.valueOf(paymentResponse.getAmount()));
            } else {
                return null;
            }

            final TransactionResponse txResponse = this.server.transactions().transaction(txId);
            if (txResponse == null) {
                log.error("xlm request TransactionHash :{}", txId);
                throw null;
            }

            String memoStr = "";
            final Memo memo = txResponse.getMemo();
            if (memo instanceof MemoText) {
                memoStr = ((MemoText) memo).getText();
            } else if (memo instanceof MemoId) {
                memoStr = String.valueOf(((MemoId) memo).getId());
            }
            final String toAddrStr = this.withdrawAddress + ":" + memoStr;

            final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            final Address address = this.addressService.getAddress(toAddrStr, table);
            if (ObjectUtils.isEmpty(address)) {
                log.error("xlm address is null,toAddrStr :{}", toAddrStr);
                return null;
            }

            final AccountTransaction transaction = AccountTransaction.builder()
                    .address(toAddrStr)
                    .balance(amount)
                    .biz(address.getBiz())
                    .txId(txId)
                    .blockHeight(Long.valueOf(response.getPagingToken()))
                    .confirmNum(this.getSequence() - txResponse.getLedger() + 1)
                    .status((byte) Constants.WAITING)
                    .currency(this.getCurrency().getIndex())
                    .createDate(Date.from(Instant.now()))
                    .updateDate(Date.from(Instant.now()))
                    .build();

            return transaction;
        } catch (final Throwable e) {
            log.error("xlm request TransactionHash :{}", response.getTransactionHash(), e);
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
            final SubmitTransactionResponse submitTransactionResponse = this.server.submitTransactionEnvelopeXdr(raw);
            if (submitTransactionResponse.isSuccess()) {
                txId = submitTransactionResponse.getHash();
            } else {
                log.error("currency {},sendRawTransaction error {}", this.getCurrency().getName(), submitTransactionResponse.getExtras().getResultCodes().getTransactionResultCode());
            }
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
            final String address = addressAndTag[0];

            final String tag = addressAndTag[1];
            final BigInteger tagInteger = new BigInteger(tag);
            if (tagInteger.compareTo(UInt32.Max32) > 0) {
                return false;
            }
            //通过转化来判断地址是否合法，如果不合法会抛异常
            final KeyPair keyPair = KeyPair.fromAccountId(address);
            return true;

        } catch (final Throwable e) {
            log.error("checkAddress address:{} error", addressWithTag, e);
            return false;
        }

    }

    @Override
    public int getConfirm(final String txId) {
        try {
            final String uri = this.xlmServer + "/transactions/" + txId;
            final Request request = (new Request.Builder()).get().url(uri).build();
            final Response response = this.httpClient.newCall(request).execute();
            if (response.code() != 200) {
                return 0;
            }
            final String content = response.body().string();
            final JSONObject txJson = JSONObject.parseObject(content);

            final Long height = txJson.getLong("ledger");
            final Long bestHeight = this.getSequence();
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

        final Long sequenceNumber = this.getSequenceNumber();
        signature.put("sequenceNumber", sequenceNumber);

        final String[] addressAndTag = record.getAddress().split(":");
        if (addressAndTag.length != 2) {
            log.info("{} buildTransaction error,record.getAddress !=2 {}", this.getCurrency().getName(), record.getAddress());
            return null;
        }
        final String toAddress = addressAndTag[0];

        final BigDecimal toBalance = this.getBalance(toAddress, this.getCurrency());
        if (toBalance.compareTo(BigDecimal.ZERO) > 0) {
            signature.put("xlmType", OperationType.PAYMENT.getValue());
        } else {
            //创建账号，balance必须大于21
            if (amountFloor.compareTo(BigDecimal.valueOf(21)) < 0) {
                log.info("{} buildTransaction error,create account balance must > 21! toBalance :{}", this.getCurrency().getName(), amountFloor);
                return null;
            }
            signature.put("xlmType", OperationType.CREATE_ACCOUNT.getValue());
        }

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

    private Account getAccountInstance() {
        if (this.ACCOUNT == null) {
            this.ACCOUNT = new Account(KeyPair.fromAccountId(this.withdrawAddress), 100L);
            final AccountsRequestBuilder accountsRequest = this.server.accounts();

            try {
                final AccountResponse response = accountsRequest.account(KeyPair.fromAccountId(this.withdrawAddress));
                this.ACCOUNT = new Account(KeyPair.fromAccountId(this.withdrawAddress), response.getSequenceNumber());

            } catch (final IOException e) {
                log.error("there is a error when get account detail", e);
            }
        }
        return this.ACCOUNT;
    }

    private Long getSequenceNumber() {
        try {
            final AccountsRequestBuilder accountsRequest = this.server.accounts();
            final AccountResponse response = accountsRequest.account(KeyPair.fromAccountId(this.withdrawAddress));
            return response.getSequenceNumber();

        } catch (final IOException e) {
            log.error("getSequenceNumber err {}", e);
            return 0L;
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final WithdrawTransactionExample withExam = new WithdrawTransactionExample();
        withExam.setOrderByClause("id desc");

        final WithdrawTransaction withdrawTransaction = this.withdrawTransactionService.getOneByExample(withExam, table);

        //stellar 发送交易必须一笔一笔发送，所以要确保最后一笔交易已经确认了
        if (!ObjectUtils.isEmpty(withdrawTransaction) && withdrawTransaction.getStatus() < CONFIRM) {
            log.error("There are still deals to be confirmed: {}", this.getCurrency().getName());

            return false;
        } else {
            return super.withdraw(record);
        }
    }

}
