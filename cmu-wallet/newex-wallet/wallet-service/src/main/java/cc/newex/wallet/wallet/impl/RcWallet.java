package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.RcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.AbstractOnlineBtcWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;


/**
 * @author newex-team
 * @data 27/03/2018
 */
@Slf4j
@Component
public class RcWallet extends AbstractOnlineBtcWallet implements IWallet {

    @Autowired
    RcCommand command;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
    }

    @Override
    public BigDecimal getDecimal() {
        return this.getCurrency().getDecimal();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.RC;
    }


}
