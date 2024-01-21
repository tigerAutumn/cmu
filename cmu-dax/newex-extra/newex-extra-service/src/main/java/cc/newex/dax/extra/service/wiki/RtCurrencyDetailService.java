package cc.newex.dax.extra.service.wiki;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyDetailExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyDetail;

/**
 * rt代币详情信息 服务接口
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:17
 */
public interface RtCurrencyDetailService extends CrudService<RtCurrencyDetail, RtCurrencyDetailExample, Long> {

    /**
     * 根据cid获取代币详情
     *
     * @param cid
     * @return
     */
    RtCurrencyDetail getDetailByCid(String cid);
}