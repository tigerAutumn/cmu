package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.OfCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.rpc.EthRawTransaction;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.ethereum.util.ByteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 17/04/2018
 */
@Slf4j
@Component
public class OrcTokenWallet extends Erc20Wallet implements IWallet {
    @Autowired
    OfCommand command;
    @Value("${newex.of.withdraw.address}")
    private String withdrawAddress;
    @Value("${newex.of.server}")
    private String rpcServerUrl;

    @Override
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
        OrcTokenWallet.log.info("updateTotalCurrencyBalance orcToken begin");
        CurrencyEnum.ORC_TOKEN_SET.parallelStream().forEach((currency) -> {
            OrcTokenWallet.log.info("update {} total Balance begin", currency.getName());
            final BigDecimal balance = this.getBalance(currency);
            this.updateTotalCurrencyBalance(currency, balance);
            OrcTokenWallet.log.info("update {} total Balance end", currency.getName());

        });

        OrcTokenWallet.log.info("updateTotalCurrencyBalance orcToken end");

    }

    @Override
    public BigDecimal getBalance(String address, final CurrencyEnum currency) {
        BigDecimal balance = BigDecimal.ZERO;

        try {
            final JSONObject param = new JSONObject();
            param.put("to", currency.getContractAddress());
            if (address.startsWith("0x")) {
                address = address.substring(2);
            }
            final String data = "0x70a0823100000000000000" + address;
            param.put("data", data);
            final String balanceStr = this.command.getTokenBalance(param, "latest");

            final BigInteger value = ByteUtil.bytesToBigInteger(ByteUtil.hexStringToBytes(balanceStr));
            balance = new BigDecimal(value).divide(currency.getDecimal());

        } catch (final Throwable e) {
            log.error("getBalance error, address:{}", address, e);
        }

        return balance;
    }

    //@Override
    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height, final Long bestHeight) {
        OrcTokenWallet.log.info("orcToken findRelatedTxs, height:{} begin", height);
        final List<AccountTransaction> txs = CurrencyEnum.ORC_TOKEN_SET.parallelStream()
                .map(currency -> this.findOrcTokenTxs(height, bestHeight, currency))
                .filter((transactions) -> !CollectionUtils.isEmpty(transactions))
                .flatMap(List::parallelStream).collect(Collectors.toList());

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

        OrcTokenWallet.log.info("orcToken findRelatedTxs, height:{} end,size:{}", height, txs.size());
        return dtos;
    }

    protected List<AccountTransaction> findOrcTokenTxs(final Long height, final Long bestHeight, final CurrencyEnum currency) {
        try {
            log.info("{} findRelatedTxs, height:{} begin", currency.getName(), height);
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
                transactions = resultLogs.parallelStream().map(resultLog -> {
                    Log lg = (Log) resultLog.get();
                    String txId = lg.getTransactionHash();
                    //先检测是不是我们发出的交易
                    super.updateWithdrawTXId(txId, currency);
                    EthRawTransaction ofTx = this.command.getTransaction(txId);
                    String input = ofTx.getInput();
                    String to = this.analyseAddress(input);
                    CurrencyEnum mainCurrency = CurrencyEnum.toMainCurrency(currency);
                    cc.newex.wallet.pojo.Address address = this.searchAddress(to, mainCurrency);
                    if (ObjectUtils.isEmpty(address)) {
                        return null;
                    }
                    BigInteger value = ByteUtil.bytesToBigInteger(ByteUtil.hexStringToBytes(input.substring(74)));

                    BigDecimal amount = new BigDecimal(value).divide(currency.getDecimal());
                    AccountTransaction transaction = AccountTransaction.builder()
                            .blockHeight(height)
                            .address(to)
                            .biz(address.getBiz())
                            .balance(amount)
                            .createDate(Date.from(Instant.now()))
                            .txId(txId)
                            .currency(currency.getIndex())
                            .status((byte) 0)
                            .confirmNum(bestHeight - height + 1)
                            .build();
                    return transaction;

                }).filter(tx -> !ObjectUtils.isEmpty(tx)).collect(Collectors.toList());

            }
            log.info("{} findRelatedTxs, height:{} end", currency.getName(), height);
            return transactions;

        } catch (final Throwable e) {
            log.error("findErc20Txs error", e);
            return null;
        }
    }

    @Override
    protected String analyseAddress(String origin) {
        if (origin.startsWith("0x")) {
            origin = origin.substring(2);
        }
        final String zeros = "00000000000000";
        final String address = origin.substring(8, 72).replace(zeros, "");
        return "0x" + address;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ORC_TOKEN;
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

    @Override
    protected BigDecimal gas() {
        return new BigDecimal("90000");
    }

    @Override
    protected BigDecimal gasPrice(final CurrencyEnum currency) {
        return new BigDecimal("5000000000000");
    }

    @Override
    protected BigDecimal transferGasPrice(final CurrencyEnum currency) {
        return new BigDecimal("5000000000000");
    }
}
