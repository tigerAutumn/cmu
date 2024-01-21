package cc.newex.dax.integration.service.conf;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.conf.ProviderConfExample;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface ProviderConfService
        extends CrudService<ProviderConf, ProviderConfExample, Integer> {

    /**
     * 获取所有邮件与短信提供者配置信息
     *
     * @return 所有邮件与短信提供者配置信息
     */
    List<ProviderConf> getAllMessageProviderConf();

    List<ProviderConf> getByType(String type, String region);

    /**
     * 获取ProviderConf Map
     * key:{@link ProviderConf#getName()}-{@link ProviderConf#getRegion()}
     *
     * @return {@link Map<String,ProviderConf> }
     */
    Map<String, ProviderConf> getProviderConfMap();

    /**
     * 创建按Region为key的提供者Map
     * key为{@link cc.newex.dax.integration.service.conf.enums.RegionEnum }的name值
     * value为Provider List集合
     *
     * @param <Provider>
     * @return {@link Map<String, List<Pair<Provider, Double>>> }
     */
    <Provider> Map<String, List<Pair<Provider, Double>>> newRegionProviderMap();

    /**
     * 向Region分组提供者Map增加数据
     *
     * @param provider
     * @param conf
     * @param map
     * @param <Provider>
     */
    <Provider> void putRegionProviderMap(Provider provider, ProviderConf conf,
                                         Map<String, List<Pair<Provider, Double>>> map);
}
