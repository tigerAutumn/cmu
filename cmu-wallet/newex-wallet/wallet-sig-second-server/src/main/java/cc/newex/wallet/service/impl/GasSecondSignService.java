package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.service.ISignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 2018/6/11
 */
@Component
@Slf4j
public class GasSecondSignService extends NeoSecondSignService implements ISignService {


    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.GAS;
    }


}
