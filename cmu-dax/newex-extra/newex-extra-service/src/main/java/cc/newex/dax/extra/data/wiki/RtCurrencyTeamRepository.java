package cc.newex.dax.extra.data.wiki;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyTeamExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyTeam;
import org.springframework.stereotype.Repository;

/**
 * rt代币团队信息 数据访问类
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:25
 */
@Repository
public interface RtCurrencyTeamRepository extends CrudRepository<RtCurrencyTeam, RtCurrencyTeamExample, Long> {
}