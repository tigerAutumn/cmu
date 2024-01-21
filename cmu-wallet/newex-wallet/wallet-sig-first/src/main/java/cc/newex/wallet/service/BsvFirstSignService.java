package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Component
@Slf4j
public class BsvFirstSignService extends BchFirstSignService implements ISignService {

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BSV;
    }


}
