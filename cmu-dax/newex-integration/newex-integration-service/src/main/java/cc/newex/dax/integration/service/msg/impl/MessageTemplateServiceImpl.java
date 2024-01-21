package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageTemplateExample;
import cc.newex.dax.integration.data.msg.MessageTemplateRepository;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.service.msg.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-13
 */
@Slf4j
@Service
public class MessageTemplateServiceImpl
        extends AbstractCrudService<MessageTemplateRepository, MessageTemplate, MessageTemplateExample, Integer>
        implements MessageTemplateService {

    @Autowired
    private MessageTemplateRepository messageTemplateRepos;

    @Override
    protected MessageTemplateExample getPageExample(final String fieldName, final String keyword) {
        final MessageTemplateExample example = new MessageTemplateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public MessageTemplate getTemplateByCode(final String locale, final String code) {
        final MessageTemplateExample example = new MessageTemplateExample();
        example.createCriteria()
                .andLocaleEqualTo(locale)
                .andCodeEqualTo(code);
        return this.messageTemplateRepos.selectOneByExample(example);
    }


}