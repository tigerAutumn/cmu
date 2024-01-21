package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageBlacklistExample;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;
import org.springframework.stereotype.Repository;

/**
 * 短信与邮件黑名单表 数据访问类
 *
 * @author newex-team
 * @date 2018-08-12
 */
@Repository
public interface MessageBlacklistRepository
        extends CrudRepository<MessageBlacklist, MessageBlacklistExample, Integer> {
}