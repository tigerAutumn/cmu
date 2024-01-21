package cc.newex.dax.extra.data.cgm;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cgm.ProjectPaymentRecordExample;
import cc.newex.dax.extra.domain.cgm.ProjectPaymentRecord;
import org.springframework.stereotype.Repository;

/**
 * 项目支付记录，记录支付CT、糖果活动币的记录 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-13 15:28:20
 */
@Repository
public interface ProjectPaymentRecordRepository extends CrudRepository<ProjectPaymentRecord, ProjectPaymentRecordExample, Long> {
}