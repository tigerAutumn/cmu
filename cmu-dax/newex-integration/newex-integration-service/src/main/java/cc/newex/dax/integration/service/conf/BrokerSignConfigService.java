package cc.newex.dax.integration.service.conf;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.conf.BrokerSignConfigExample;
import cc.newex.dax.integration.domain.conf.BrokerSignConfig;

import java.util.List;

/**
 * @author mbg.generated
 * @date 2018-09-12 15:04:54
 */
public interface BrokerSignConfigService extends CrudService<BrokerSignConfig, BrokerSignConfigExample, Long> {

    /**
     * 通过 brokerId 获取
     *
     * @param brokerId
     * @return
     */
    String takeSignByBroker(Integer brokerId);

    /**
     * 通过 brokerId 获取配置信息
     *
     * @param brokerId
     * @return
     */
    BrokerSignConfig getByBrokerId(Integer brokerId);

    /**
     * 通过 brokerId 数组获取配置信息
     *
     * @param brokerId
     * @return
     */
    List<BrokerSignConfig> getByBrokerId(Integer[] brokerId);

    /**
     * 通过 brokerId 更新 sign 签名配置
     *
     * @param brokerId
     * @param sign
     */
    void updateByBrokerId(Integer brokerId, String sign);

    /**
     * 通过 brokerId 删除 sign 签名配置
     *
     * @param brokerId
     */
    void removeByBrokerId(Integer brokerId);
}