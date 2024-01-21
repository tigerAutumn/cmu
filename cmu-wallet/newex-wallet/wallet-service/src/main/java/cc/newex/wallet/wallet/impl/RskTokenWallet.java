package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.RbtcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 17/04/2018
 */
@Slf4j
@Component
public class RskTokenWallet extends Erc20Wallet implements IWallet {
    @Autowired
    RbtcCommand command;
    @Value("${newex.rbtc.withdraw.address}")
    private String withdrawAddress;
    @Value("${newex.rbtc.server}")
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
        log.info("updateTotalCurrencyBalance rskToken begin");
        CurrencyEnum.RSK_TOKEN_ASSET.parallelStream().forEach((currency) -> {
            log.info("update {} total Balance begin", currency.getName());
            final BigDecimal balance = this.getBalance(currency);
            this.updateTotalCurrencyBalance(currency, balance);
            log.info("update {} total Balance end", currency.getName());
        });
        log.info("updateTotalCurrencyBalance rskToken end");

    }

    @Override
    public List<TransactionDTO> findRelatedTxs(final Long height, final Long bestHeight) {
        RskTokenWallet.log.info("rskToken findRelatedTxs, height:{} begin", height);
        final List<AccountTransaction> txs = CurrencyEnum.RSK_TOKEN_ASSET.stream()
                .map(currency -> super.findErc20Txs(height, bestHeight, currency))
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

        RskTokenWallet.log.info("rskToken findRelatedTxs, height:{} end,size:{}", height, txs.size());
        return dtos;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.RSK_TOKEN;
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
        return new BigDecimal("60000").divide(CurrencyEnum.RBTC.getDecimal());
    }

    @Override
    protected BigDecimal gasPrice(final CurrencyEnum currency) {
        return new BigDecimal("0.00000000005924");
    }

    @Override
    protected BigDecimal transferGasPrice(final CurrencyEnum currency) {
        return new BigDecimal("0.00000000005924");
    }
}
