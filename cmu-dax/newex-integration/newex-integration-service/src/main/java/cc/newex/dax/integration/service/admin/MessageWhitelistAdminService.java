package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageWhitelistExample;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageWhitelistAdminService
        extends CrudService<MessageWhitelist, MessageWhitelistExample, Integer> {

    List<MessageWhitelist> listByPage(PageInfo pageInfo, MessageWhitelist messageWhitelist);

    List<MessageWhitelist> getByPage(PageInfo pageInfo, String fieldName, String keyword, Integer brokerId);
}
