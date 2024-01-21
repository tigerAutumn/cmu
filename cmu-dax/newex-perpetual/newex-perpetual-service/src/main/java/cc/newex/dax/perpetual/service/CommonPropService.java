package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.CommonPropExample;
import cc.newex.dax.perpetual.domain.CommonProp;

import java.util.List;

/**
 * 公共配置表 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:30:57
 */
public interface CommonPropService extends CrudService<CommonProp, CommonPropExample, Long> {

    /**
     * 获取配置信息
     * @param brokerId 券商 id
     * @param key key
     * @param clazz 返回对象类型
     * @param <T> 范型
     * @return
     */
    <T> T getConfigObject(Integer brokerId, String key, Class<T> clazz);

    /**
     * 获取配置列表信息
     * @param brokerId 券商
     * @param key key
     * @param clazz 返回对象类型
     * @param <T> 范型
     * @return
     */
    <T> List<T> getConfigList(Integer brokerId, String key, Class<T> clazz);

    /**
     * 设置配置信息
     * @param brokerId
     * @param key
     * @param jsonStr
     */
    void setConfig(Integer brokerId, String key, String jsonStr);

    /**
     * 刷新缓存数据
     */
    void refreshConfig();
}