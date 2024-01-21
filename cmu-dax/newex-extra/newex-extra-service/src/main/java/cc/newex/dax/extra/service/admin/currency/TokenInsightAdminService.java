package cc.newex.dax.extra.service.admin.currency;

import cc.newex.dax.extra.domain.tokenin.CurrencyLevelInfo;
import cc.newex.dax.extra.domain.tokenin.CurrencySupplyInfo;

/**
 * TokenInsight API封装
 *
 * @author liutiejun
 * @date 2018-07-18
 */
public interface TokenInsightAdminService {

    /**
     * 获取评级相关数据
     *
     * @param shortName 币种的唯一标识
     * @param locale    zh-cn：中文，en-us：英文
     * @return
     */
    CurrencyLevelInfo getLevelInfo(String shortName, String locale);

    /**
     * 获取币种流通量和流通市值等相关信息
     *
     * @param shortName
     * @return
     */
    CurrencySupplyInfo getSupplyInfo(String shortName);

}
