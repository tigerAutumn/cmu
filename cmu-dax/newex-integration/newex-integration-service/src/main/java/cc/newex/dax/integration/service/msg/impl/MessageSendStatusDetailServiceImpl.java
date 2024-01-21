package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageSendStatusDetailExample;
import cc.newex.dax.integration.data.msg.MessageSendStatusDetailRepository;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.service.msg.MessageSendStatusDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 信息发送状态明细表 服务实现
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Slf4j
@Service
public class MessageSendStatusDetailServiceImpl
        extends AbstractCrudService<MessageSendStatusDetailRepository, MessageSendStatusDetail, MessageSendStatusDetailExample, Long>
        implements MessageSendStatusDetailService {

    @Override
    protected MessageSendStatusDetailExample getPageExample(final String fieldName, final String keyword) {
        final MessageSendStatusDetailExample example = new MessageSendStatusDetailExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

}