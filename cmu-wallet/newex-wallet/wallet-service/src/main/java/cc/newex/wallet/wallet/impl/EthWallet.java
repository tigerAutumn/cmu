package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.EthCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class EthWallet extends AbstractEthLikeWallet implements IWallet {
    @Autowired
    EthCommand command;

    @Autowired
    Erc20Wallet erc20Wallet;

    @Value("${newex.eth.withdraw.address}")
    private String ethWithdrawAddress;

    @PostConstruct
    public void init() {
        this.RESERVED = new BigDecimal("0.1");
        super.setCommand(this.command);
        super.setWithdrawAddress(this.ethWithdrawAddress);
    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        super.updateTXConfirmation(CurrencyEnum.ETH);
        CurrencyEnum.ERC20_SET.parallelStream().forEach(super::updateTXConfirmation);
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(Long height) {
        List<TransactionDTO> ethTransactions = super.findRelatedTxs(height);
        if (CollectionUtils.isEmpty(ethTransactions)) {
            ethTransactions = new LinkedList<>();
        }
        List<TransactionDTO> erc20Transactions = this.erc20Wallet.findRelatedTxs(height, this.height);
        if (!CollectionUtils.isEmpty(erc20Transactions)) {
            ethTransactions.addAll(erc20Transactions);
        }
        return ethTransactions;
    }

    //更新钱包中的币余额
    @Override
    public void updateTotalCurrencyBalance() {
        super.updateTotalCurrencyBalance();
        this.erc20Wallet.updateTotalCurrencyBalance();
    }


    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ETH;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.ETH.getDecimal();
    }
}
