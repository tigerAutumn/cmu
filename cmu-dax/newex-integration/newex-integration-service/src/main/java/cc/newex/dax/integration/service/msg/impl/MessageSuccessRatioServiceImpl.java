package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageSuccessRatioExample;
import cc.newex.dax.integration.data.msg.MessageSuccessRatioRepository;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;
import cc.newex.dax.integration.service.msg.MessageSuccessRatioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 信息息发送成功率统计表 服务实现
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Slf4j
@Service
public class MessageSuccessRatioServiceImpl
        extends AbstractCrudService<MessageSuccessRatioRepository, MessageSuccessRatio, MessageSuccessRatioExample, Integer>
        implements MessageSuccessRatioService {


    @Override
    protected MessageSuccessRatioExample getPageExample(final String fieldName, final String keyword) {
        final MessageSuccessRatioExample example = new MessageSuccessRatioExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}