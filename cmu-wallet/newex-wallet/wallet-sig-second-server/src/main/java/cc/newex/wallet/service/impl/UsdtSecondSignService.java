package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.service.ISignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 11/04/2018
 */
@Component
@Slf4j
public class UsdtSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.USDT;
    }
}
