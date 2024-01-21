package cc.newex.dax.market.common.consts;

import cc.newex.dax.market.dto.enums.RateConvertEnum;

import java.util.Arrays;
import java.util.List;

/**
 * MarketAllRateConst中的常量定义。
 *
 * @author newex-team
 */
public interface MarketAllRateConst {

    /**
     * 状态：可用。
     */
    int STATUS_YES = 1;
    /**
     * 状态：不可用。
     */
    int STATUS_NO = 0;

    /**
     * 汇率
     */
    List<String> RATE_LIST = Arrays.asList(RateConvertEnum.USD_EUR.getCode(), RateConvertEnum.USD_JPY.getCode(), RateConvertEnum.USD_RUB.getCode(), RateConvertEnum.USD_CNY
            .getCode(), RateConvertEnum.USD_KRW.getCode());

}
