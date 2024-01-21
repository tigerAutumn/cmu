package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.BtcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author newex-team
 * @data 27/03/2018
 */
@Slf4j
@Component
public class BtcWallet extends AbstractBtcLikeWallet implements IWallet {


    @Autowired
    BtcCommand command;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BTC;
    }


    @Override
    public NetworkParameters getNetworkParameters() {
        if (this.CONSTANT.NETWORK.equals("test")) {
            return TestNet3Params.get();
        }

        return MainNetParams.get();
    }


}
