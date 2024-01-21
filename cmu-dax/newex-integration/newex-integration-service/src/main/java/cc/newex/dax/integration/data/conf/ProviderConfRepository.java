package cc.newex.dax.integration.data.conf;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.conf.ProviderConfExample;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-06-02
 */
@Repository
public interface ProviderConfRepository
        extends CrudRepository<ProviderConf, ProviderConfExample, Integer> {
}