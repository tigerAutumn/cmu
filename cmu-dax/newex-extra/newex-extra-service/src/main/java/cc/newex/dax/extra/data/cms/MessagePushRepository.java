package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.MessagePushExample;
import cc.newex.dax.extra.domain.cms.MessagePush;

/**
 * @author huxingkong
 * @date 2018/10/19 4:52 PM
 */
public interface MessagePushRepository extends CrudRepository<MessagePush, MessagePushExample,Integer> {
}
