package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.CurrencyPairBrokerRelationExample;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.dto.CurrencyPairBrokerRelationDTO;

import java.util.List;

/**
 * 币对 券商 对应关系表 服务接口
 *
 * @author newex -team
 * @date 2018 -11-16 11:18:52
 */
public interface CurrencyPairBrokerRelationService extends CrudService<CurrencyPairBrokerRelation, CurrencyPairBrokerRelationExample, Long> {
    /**
     * load缓存
     */
    void loadDb();

    /**
     * 获取列表
     *
     * @param pairCode the pair code
     * @param brokerId brokerId
     * @return by pair code
     */
    CurrencyPairBrokerRelation getPairCodeByCache(String pairCode, Integer brokerId);

    /**
     * 获取列表
     *
     * @param brokerId the broker id
     * @return by broker id
     */
    List<CurrencyPairBrokerRelation> getByBrokerId(Integer brokerId);

    /**
     * 获取列表
     *
     * @param pairCode the pair code
     * @return by pair code
     */
    List<CurrencyPairBrokerRelation> getByPairCode(String... pairCode);

    /**
     * List currency pair broker relation by conditon list.
     *
     * @param pageInfo       the page info
     * @param queryParameter the query parameter
     * @return the list
     */
    List<CurrencyPairBrokerRelationDTO> listCurrencyPairBrokerRelationByConditon(PageInfo pageInfo, CurrencyPairBrokerRelationDTO queryParameter);
}