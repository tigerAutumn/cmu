package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.EtcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class EtcWallet extends AbstractEthLikeWallet implements IWallet {
    @Autowired
    EtcCommand command;

    @Value("${newex.etc.withdraw.address}")
    private String etcWithdrawAddress;

    @PostConstruct
    public void init() {
        this.RESERVED = new BigDecimal("0.1");
        super.setCommand(this.command);
        super.setWithdrawAddress(this.etcWithdrawAddress);

    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ETC;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.ETC.getDecimal();
    }
}
