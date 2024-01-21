package cc.newex.wallet.service;

import cc.newex.wallet.config.PubKeyConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

/**
 * @author newex-team
 * @data 02/04/2018
 */
@Component
@Slf4j
public class BtcFirstSignService extends AbstractBtcLikeFirstSign implements ISignService {

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BTC;
    }

}
