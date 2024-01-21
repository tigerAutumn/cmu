package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageSendStatusDetailExample;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import org.springframework.stereotype.Repository;

/**
 * 信息发送状态明细表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Repository
public interface MessageSendStatusDetailRepository
        extends CrudRepository<MessageSendStatusDetail, MessageSendStatusDetailExample, Long> {
}