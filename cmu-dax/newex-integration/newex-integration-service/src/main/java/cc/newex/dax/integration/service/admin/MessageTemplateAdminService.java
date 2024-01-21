package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageTemplateExample;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.dto.admin.MessageTemplateDTO;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageTemplateAdminService
        extends CrudService<MessageTemplate, MessageTemplateExample, Integer> {

    List<MessageTemplate> listByPage(PageInfo pageInfo, MessageTemplateDTO messageTemplateDTO);

}
