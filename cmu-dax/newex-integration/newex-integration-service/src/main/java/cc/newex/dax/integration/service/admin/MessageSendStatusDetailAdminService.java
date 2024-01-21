package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageSendStatusDetailExample;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.dto.admin.MessageSendStatusDetailDTO;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageSendStatusDetailAdminService
        extends CrudService<MessageSendStatusDetail, MessageSendStatusDetailExample, Long> {

    List<MessageSendStatusDetail> listByPage(PageInfo pageInfo, MessageSendStatusDetailDTO messageSendStatusDetailDTO);

}
