package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageWhitelistExample;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;

/**
 * 短信与邮件发送白名单表 服务接口
 *
 * @author newex-team
 * @date 2018-08-13
 */
public interface MessageWhitelistService
        extends CrudService<MessageWhitelist, MessageWhitelistExample, Integer> {
}
