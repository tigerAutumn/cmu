package cc.newex.dax.extra.service.wiki;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyRankExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyRank;

/**
 * rt代币排名信息 服务接口
 *
 * @author mbg.generated
 * @date 2018 -11-28 14:55:21
 */
public interface RtCurrencyRankService extends CrudService<RtCurrencyRank, RtCurrencyRankExample, Long> {

    /**
     * 根据币种名称获取代币ID
     *
     * @param currencyCode the currency code
     * @return cid by currency code
     */
    RtCurrencyRank getCidByCurrencyCode(String currencyCode);
}