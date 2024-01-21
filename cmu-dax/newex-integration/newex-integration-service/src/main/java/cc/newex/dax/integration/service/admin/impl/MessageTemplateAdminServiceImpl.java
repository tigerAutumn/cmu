package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageTemplateExample;
import cc.newex.dax.integration.data.msg.MessageTemplateRepository;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.dto.admin.MessageTemplateDTO;
import cc.newex.dax.integration.service.admin.MessageTemplateAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
@Service
public class MessageTemplateAdminServiceImpl
        extends AbstractCrudService<MessageTemplateRepository, MessageTemplate, MessageTemplateExample, Integer>
        implements MessageTemplateAdminService {

    @Override
    protected MessageTemplateExample getPageExample(final String fieldName, final String keyword) {
        final MessageTemplateExample example = new MessageTemplateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<MessageTemplate> listByPage(final PageInfo pageInfo, final MessageTemplateDTO messageTemplateDTO) {
        final MessageTemplateExample messageTemplateExample = new MessageTemplateExample();
        final MessageTemplateExample.Criteria criteria = messageTemplateExample.createCriteria();
        if (StringUtils.isNotEmpty(messageTemplateDTO.getSubject())) {
            criteria.andSubjectLike("%" + messageTemplateDTO.getSubject() + "%");
        }
        if (StringUtils.isNotEmpty(messageTemplateDTO.getLocale())) {
            criteria.andLocaleEqualTo(messageTemplateDTO.getLocale());
        }
        if (StringUtils.isNotEmpty(messageTemplateDTO.getType())) {
            criteria.andTypeEqualTo(messageTemplateDTO.getType());
        }
        if (StringUtils.isNotEmpty(messageTemplateDTO.getCode())) {
            criteria.andCodeLike("%" + messageTemplateDTO.getCode() + "%");
        }
        return this.getByPage(pageInfo, messageTemplateExample);
    }
}
