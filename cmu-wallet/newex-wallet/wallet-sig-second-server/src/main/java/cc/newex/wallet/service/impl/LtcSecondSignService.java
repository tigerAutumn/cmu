package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.service.ISignService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.NetworkParameters;
import org.springframework.stereotype.Component;
import sdk.header.LiteMainNetParam;

/**
 * @author newex-team
 * @data 10/04/2018
 */
@Component
@Slf4j
public class LtcSecondSignService extends AbstractBtcLikeSecondSign implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LTC;
    }

    @Override
    protected NetworkParameters getNetworkParameters() {
        return LiteMainNetParam.get();
    }
}
