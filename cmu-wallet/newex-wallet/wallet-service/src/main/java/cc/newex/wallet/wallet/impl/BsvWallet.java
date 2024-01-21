package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.BsvCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//import sdk.bitcoincashj.core.MultiSignAddressGenerator;
//import sdk.core.MultiSignAddressGenerator;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Slf4j
@Component
public class BsvWallet extends BchWallet implements IWallet {
    @Autowired
    BsvCommand bsvCommand;

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BSV;
    }

    @Override
    @PostConstruct
    public void init() {
        super.setCommand(this.bsvCommand);
    }


}
