package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.MessagePushExample;
import cc.newex.dax.extra.domain.cms.MessagePush;
import cc.newex.dax.extra.dto.cms.MessagePushDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Message push service.
 *
 * @author huxingkong
 * @date 2018 /10/19 4:54 PM
 */
@Service
public interface MessagePushService extends CrudService<MessagePush, MessagePushExample, Integer> {

    String PUSH_STATUS = "已推送";
    String WAIT_PUSH_STATUS = "等待推送";


    /**
     * 获取未推送切推送时间在当前时间之前的消息
     *
     * @param now 当前时间
     * @return list
     */
    List<MessagePush> listNotPushMessageAndPushTimeBeforeNow(LocalDateTime now);

    /**
     * 条件和分页查询
     *
     * @param pageInfo
     * @param queryParameter
     * @return
     */
    List<MessagePushDTO> listByConditionAndPage(PageInfo pageInfo, MessagePushDTO queryParameter);
}
