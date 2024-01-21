package cc.newex.dax.extra.service.wiki;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyScoreExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyScore;

/**
 * rt代币评分信息 服务接口
 *
 * @author mbg.generated
 * @date 2018 -12-13 16:34:00
 */
public interface RtCurrencyScoreService extends CrudService<RtCurrencyScore, RtCurrencyScoreExample, Long> {

    /**
     * Gets by cid and currency code.
     *
     * @param currencyCode the currency code
     * @param cid          the cid
     * @return the by cid and currency code
     */
    RtCurrencyScore getByCidAndCurrencyCode(String currencyCode, String cid);
}