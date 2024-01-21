package cc.newex.wallet.wallet.impl;
import cc.newex.wallet.command.RbtcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class RbtcWallet extends AbstractEthLikeWallet implements IWallet {
    @Autowired
    RbtcCommand command;
    @Value("${newex.rbtc.server}")
    private String serverUrl;

    @Autowired
    private RskTokenWallet rskTokenWallet;

    @Value("${newex.rbtc.withdraw.address}")
    private String rbtcWithdrawAddress;

    @PostConstruct
    public void init() {
        this.RESERVED = new BigDecimal("0.0001");
        super.setCommand(this.command);
        super.setWithdrawAddress(this.rbtcWithdrawAddress);
    }

    @Override
    public void updateTXConfirmation(CurrencyEnum currency) {
        super.updateTXConfirmation(CurrencyEnum.RBTC);
        CurrencyEnum.RSK_TOKEN_ASSET.parallelStream().forEach(super::updateTXConfirmation);
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(Long height) {
        List<TransactionDTO> relatedTxs = super.findRelatedTxs(height);
        List<TransactionDTO> orcTokenTransactions = this.rskTokenWallet.findRelatedTxs(height, this.height);
        if (!org.springframework.util.CollectionUtils.isEmpty(orcTokenTransactions)) {
            relatedTxs.addAll(orcTokenTransactions);
        }
        return relatedTxs;
    }

    /**
     * 提现方法
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        if (CurrencyEnum.RSK_TOKEN_ASSET.contains(currency)) {
            return this.rskTokenWallet.withdraw(record);
        } else {
            return super.withdraw(record);
        }
    }

    //更新钱包中的币余额
    @Override
    public void updateTotalCurrencyBalance() {
        super.updateTotalCurrencyBalance();
        this.rskTokenWallet.updateTotalCurrencyBalance();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.RBTC;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.RBTC.getDecimal();
    }

    @Override
    protected BigDecimal gasPrice(CurrencyEnum currency) {
        return new BigDecimal("0.00000000005924");
    }

    @Override
    protected BigDecimal gas() {
        //https://stats.rsk.co/
        return BigDecimal.valueOf(21000).divide(CurrencyEnum.RBTC.getDecimal());
    }
}
