package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageBlacklistExample;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;

/**
 * 短信与邮件黑名单表 服务接口
 *
 * @author newex-team
 * @date 2018-08-12
 */
public interface MessageBlacklistService
        extends CrudService<MessageBlacklist, MessageBlacklistExample, Integer> {
}
