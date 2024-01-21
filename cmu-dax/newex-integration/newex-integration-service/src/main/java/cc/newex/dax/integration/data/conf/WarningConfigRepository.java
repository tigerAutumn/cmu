package cc.newex.dax.integration.data.conf;


import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.conf.WarningConfigExample;
import cc.newex.dax.integration.domain.conf.WarningConfig;
import org.springframework.stereotype.Repository;

/**
 * 报警配置表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-07-23 16:10:04
 */
@Repository
public interface WarningConfigRepository extends CrudRepository<WarningConfig, WarningConfigExample, Long> {
}