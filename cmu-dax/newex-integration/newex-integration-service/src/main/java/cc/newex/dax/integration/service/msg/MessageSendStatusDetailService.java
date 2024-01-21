package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageSendStatusDetailExample;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;


/**
 * 信息发送状态明细表 服务接口
 *
 * @author newex-team
 * @date 2018-05-10
 */
public interface MessageSendStatusDetailService
        extends CrudService<MessageSendStatusDetail, MessageSendStatusDetailExample, Long> {

}
