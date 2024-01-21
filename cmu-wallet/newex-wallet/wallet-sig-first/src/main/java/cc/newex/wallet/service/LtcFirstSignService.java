package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
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
public class LtcFirstSignService extends AbstractBtcLikeFirstSign implements ISignService {

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LTC;
    }

    @Override
    protected NetworkParameters getNetworkParameters() {
        return LiteMainNetParam.get();
    }
}
