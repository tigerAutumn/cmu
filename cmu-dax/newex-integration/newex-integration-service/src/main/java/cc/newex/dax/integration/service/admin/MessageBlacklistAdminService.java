package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageBlacklistExample;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageBlacklistAdminService
        extends CrudService<MessageBlacklist, MessageBlacklistExample, Integer> {

    List<MessageBlacklist> listByPage(PageInfo pageInfo, MessageBlacklist messageBlacklist);

    List<MessageBlacklist> getByPage(PageInfo pageInfo, String fieldName, String keyword, Integer brokerId);

}
