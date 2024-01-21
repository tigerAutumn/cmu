package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageExample;
import cc.newex.dax.integration.data.msg.MessageRepository;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.dto.admin.MessageDTO;
import cc.newex.dax.integration.service.admin.MessageAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
@Service
public class MessageAdminServiceImpl
        extends AbstractCrudService<MessageRepository, Message, MessageExample, Long>
        implements MessageAdminService {

    @Override
    protected MessageExample getPageExample(final String fieldName, final String keyword) {
        final MessageExample example = new MessageExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @return 分页记录列表
     */
    @Override
    public List<Message> listByPage(final PageInfo pageInfo, final MessageDTO messageDTO) {
        final MessageExample messageExample = new MessageExample();
        final MessageExample.Criteria criteria = messageExample.createCriteria();
        if (StringUtils.isNotEmpty(messageDTO.getPhoneNumber())) {
            criteria.andPhoneNumberLike(messageDTO.getPhoneNumber() + "%");
        }
        if (StringUtils.isNotEmpty(messageDTO.getEmailAddress())) {
            criteria.andEmailAddressLike(messageDTO.getEmailAddress() + "%");
        }
        if (StringUtils.isNotEmpty(messageDTO.getLocale())) {
            criteria.andLocaleEqualTo(messageDTO.getLocale());
        }
        if (Objects.nonNull(messageDTO.getSent())) {
            criteria.andIsSentEqualTo(messageDTO.getSent() ? (byte) 1 : (byte) 0);
        }
        if (Objects.nonNull(messageDTO.getBrokerId())) {
            criteria.andBrokerIdEqualTo(messageDTO.getBrokerId());
        }
        return this.getByPage(pageInfo, messageExample);
    }
}
