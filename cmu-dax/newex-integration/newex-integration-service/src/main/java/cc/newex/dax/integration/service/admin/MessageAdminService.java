package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageExample;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.dto.admin.MessageDTO;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageAdminService
        extends CrudService<Message, MessageExample, Long> {

    List<Message> listByPage(PageInfo pageInfo, MessageDTO messageDTO);
}
