package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.service.ISignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Component
@Slf4j
public class BsvSecondSignService extends BchSecondSignService implements ISignService {

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BSV;
    }

}
