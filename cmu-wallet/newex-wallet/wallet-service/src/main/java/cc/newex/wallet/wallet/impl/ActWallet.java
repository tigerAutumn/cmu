package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.ActCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.AbstractActLikeWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/5/10
 */
@Slf4j
@Component
public class ActWallet extends AbstractActLikeWallet {
    @Autowired
    ActCommand command;
    @Value("${newex.act.withdraw.address}")
    private String actWithdrawAddress;

    @Value("${newex.act.wallet.name}")
    private String walletName;

    @Value("${newex.act.wallet.password}")
    private String walletPassword;

    @Value("${newex.act.account.name}")
    private String accountName;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
        super.setWithdrawAddress(this.actWithdrawAddress);
    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        super.updateTXConfirmation(CurrencyEnum.ACT);
        CurrencyEnum.ATP_SET.parallelStream().forEach(super::updateTXConfirmation);
    }

//    @Override
//    public void updateTotalCurrencyBalance() {
//        super.updateTotalCurrencyBalance();
//        CurrencyEnum.ATP_SET.parallelStream().forEach((currency) -> {
//            ActWallet.log.info("update {} total Balance begin", currency.getName());
//            final BigDecimal balance = this.getATPBalance(currency);
//            this.updateTotalCurrencyBalance(currency, balance);
//            ActWallet.log.info("update {} total Balance end", currency.getName());
//
//        });
//
//    }
//
//    public BigDecimal getATPBalance(final CurrencyEnum currency) {
//        ActWallet.log.info("get {} Balance begin", this.getCurrency().getName());
//        try {
//
//            this.command.walletUnlock(10000, this.walletPassword);
//            JSONArray result = this.command.getContractBalance(currency.getContractAddress(), this.accountName, "balanceOf", this.actWithdrawAddress);
//            BigDecimal balance = result.getJSONObject(0).getBigDecimal("event_param").divide(currency.getDecimal());
//
//            ActWallet.log.info("get {} Balance end", this.getCurrency().getName());
//
//            return balance;
//        } catch (final Exception e) {
//            ActWallet.log.error("get {} Balance error", this.getCurrency().getName(), e);
//
//        }
//        return BigDecimal.ZERO;
//
//    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ACT;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.ACT.getDecimal();
    }
}
