package cc.newex.dax.boss.report.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.report.criteria.HistoryExample;
import cc.newex.dax.boss.report.domain.History;
import org.springframework.stereotype.Repository;

/**
 * 报表历史信息表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface HistoryRepository
        extends CrudRepository<History, HistoryExample, Integer> {
}