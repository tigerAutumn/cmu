package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageWhitelistExample;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;
import org.springframework.stereotype.Repository;

/**
 * 短信与邮件发送白名单表 数据访问类
 *
 * @author newex-team
 * @date 2018-08-13
 */
@Repository
public interface MessageWhitelistRepository
        extends CrudRepository<MessageWhitelist, MessageWhitelistExample, Integer> {
}