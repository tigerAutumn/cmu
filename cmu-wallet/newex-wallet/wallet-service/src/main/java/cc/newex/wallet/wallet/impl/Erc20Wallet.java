package cc.newex.wallet.wallet.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.EthCommand;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.service.CurrencyBalanceService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.utils.EthereumUtil;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 17/04/2018
 */
@Slf4j
@Component
public class Erc20Wallet extends AbstractEthLikeWallet implements IWallet {
    @Autowired
    EthCommand command;
    @Autowired
    protected CurrencyBalanceService balanceService;
    protected Web3j web3j;
    @Value("${newex.eth.withdraw.address}")
    protected String withdrawAddress;
    @Value("${newex.eth.server}")
    private String rpcServerUrl;

    @Override
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
            addresses.add(this.getWithdrawAddress());

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

        final Function function = new Function("balanceOf", Arrays
                .asList(new Address(address)), Arrays.asList(TypeReference.create(Uint256.class)));

        final String encodedFunction = FunctionEncoder.encode(function);

        try {
            final EthCall response = this.web3j.ethCall(
                    Transaction.createEthCallTransaction(address, currency.getContractAddress(), encodedFunction),
                    DefaultBlockParameterName.LATEST)
                    .sendAsync().get();

            final List<Type> returnTypes = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
            if (!CollectionUtils.isEmpty(returnTypes)) {
                final String value = response.getValue();
                balance = new BigDecimal(EthereumUtil.hexToBigInteger(value)).divide(currency.getDecimal());

            }
        } catch (final Throwable e) {
            Erc20Wallet.log.error("getBalance error, address:{}", address, e);
        }

        return balance;
    }

    @PostConstruct
    public void init() {
        this.RESERVED = BigDecimal.ZERO;
        super.setCommand(this.command);
        super.setWithdrawAddress(this.withdrawAddress);
        final Web3jService web3jService = new HttpService(this.rpcServerUrl);
        this.web3j = Web3j.build(web3jService);
    }

    @Override
    public void updateTotalCurrencyBalance() {
        Erc20Wallet.log.info("updateTotalCurrencyBalance erc20 begin");
        CurrencyEnum.ERC20_SET.parallelStream().forEach((currency) -> {
            Erc20Wallet.log.info("update {} total Balance begin", currency.getName());
            final BigDecimal balance = this.getBalance(currency);
            this.updateTotalCurrencyBalance(currency, balance);
            Erc20Wallet.log.info("update {} total Balance end", currency.getName());

        });

        Erc20Wallet.log.info("updateTotalCurrencyBalance erc20 end");

    }

    public List<TransactionDTO> findRelatedTxs(final Long height, final Long bestHeight) {
        Erc20Wallet.log.info("erc20 findRelatedTxs, height:{} begin", height);
        final List<AccountTransaction> txs = CurrencyEnum.ERC20_SET.parallelStream().map(currency -> this.findErc20Txs(height, bestHeight, currency))
                .filter((transactions) -> !CollectionUtils.isEmpty(transactions)).flatMap(List::parallelStream).collect(Collectors.toList());

        List<TransactionDTO> dtos = new LinkedList<>();
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(txs)) {
            dtos = txs.parallelStream()
                    .map(tx -> {
                        CurrencyEnum currency = CurrencyEnum.parseValue(tx.getCurrency());
                        ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
                        this.accountTransactionService.addOnDuplicateKey(tx, table);
                        return this.convertAccountTxToDto(tx);
                    })
                    .collect(Collectors.toList());
        }

        Erc20Wallet.log.info("erc20 findRelatedTxs, height:{} end,size:{}", height, txs.size());
        return dtos;
    }

    protected String analyseAddress(final String origin) {
        final String zeros = "000000000000000000000000";
        final String address = origin.replace(zeros, "");
        return address;
    }

    protected List<AccountTransaction> findErc20Txs(final Long height, final Long bestHeight, final CurrencyEnum currency) {
        try {
            Erc20Wallet.log.info("{} findRelatedTxs, height:{} begin", currency.getName(), height);
            final Event event = new Event("Transfer", Arrays.asList(new TypeReference<Address>() {
                                                                    },
                    new TypeReference<Address>() {
                    },
                    new TypeReference<Uint256>() {
                    }),
                    Collections.emptyList());
            final String encodedEventSignature = EventEncoder.encode(event);
            final EthFilter filter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(height)),
                    DefaultBlockParameter.valueOf(BigInteger.valueOf(height)), currency.getContractAddress());
            filter.addSingleTopic(encodedEventSignature);
            final EthLog ethLog = this.web3j.ethGetLogs(filter).send();
            final List<EthLog.LogResult> resultLogs = ethLog.getLogs();
            List<AccountTransaction> transactions = null;
            if (!CollectionUtils.isEmpty(resultLogs)) {
                final int len = resultLogs.size();
                transactions = new ArrayList<>();
                final Map<String, Integer> txIdCnt = new HashMap<>();
                for (int i = 0; i < len; i++) {
                    final EthLog.LogResult resultLog = resultLogs.get(i);
                    final Log lg = (Log) resultLog.get();
                    final String txId = lg.getTransactionHash();
                    //先检测是不是我们发出的交易
                    super.updateWithdrawTXId(txId, currency);
                    final String to = this.analyseAddress(lg.getTopics().get(2));
                    final CurrencyEnum mainCurrency = CurrencyEnum.toMainCurrency(currency);
                    final cc.newex.wallet.pojo.Address address = this.searchAddress(to, mainCurrency);
                    if (ObjectUtils.isEmpty(address)) {
                        continue;
                    }
                    final int decimal = currency.getDecimal().precision();
                    final String sciNotation = "1.0E" + (decimal - 1);
                    final BigInteger data = EthereumUtil.hexToBigInteger(
                            lg.getData().replace(sciNotation, ""));
                    final BigDecimal amount = new BigDecimal(EthereumUtil.hexToBigInteger("0x" + data.toString(16)));
                    final AccountTransaction transaction = AccountTransaction.builder()
                            .blockHeight(height)
                            .address(to)
                            .biz(address.getBiz())
                            .balance(amount.divide(currency.getDecimal()))
                            .createDate(Date.from(Instant.now()))
                            .txId(txId)
                            .currency(currency.getIndex())
                            .status((byte) 0)
                            .confirmNum(bestHeight - height + 1)
                            .build();

                    txIdCnt.put(txId, txIdCnt.getOrDefault(txId, 0) + 1);
                    if (txIdCnt.get(txId) > 1) {
                        transaction.setTxId(transaction.getTxId() + StringUtil.DASH + txIdCnt.get(txId));
                    }
                    transactions.add(transaction);
                }
            }
            Erc20Wallet.log.info("{} findRelatedTxs, height:{} end", currency.getName(), height);
            return transactions;
        } catch (final Throwable e) {
            Erc20Wallet.log.error("findErc20Txs error", e);
            return null;
        }
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ERC20;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        throw new RuntimeException("not support this getDecimal method");
    }
}
