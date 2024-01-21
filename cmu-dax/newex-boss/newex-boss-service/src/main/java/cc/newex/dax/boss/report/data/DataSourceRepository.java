package cc.newex.dax.boss.report.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.report.criteria.DataSourceExample;
import cc.newex.dax.boss.report.domain.DataSource;
import org.springframework.stereotype.Repository;

/**
 * 报表数据源配置信息表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface DataSourceRepository
        extends CrudRepository<DataSource, DataSourceExample, Integer> {
}