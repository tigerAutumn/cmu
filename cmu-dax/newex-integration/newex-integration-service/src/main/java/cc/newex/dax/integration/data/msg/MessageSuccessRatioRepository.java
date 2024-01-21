package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageSuccessRatioExample;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;
import org.springframework.stereotype.Repository;

/**
 * 信息息发送成功率统计表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Repository
public interface MessageSuccessRatioRepository
        extends CrudRepository<MessageSuccessRatio, MessageSuccessRatioExample, Integer> {
}