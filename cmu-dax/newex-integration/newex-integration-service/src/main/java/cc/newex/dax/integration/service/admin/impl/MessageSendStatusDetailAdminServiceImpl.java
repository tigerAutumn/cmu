package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageSendStatusDetailExample;
import cc.newex.dax.integration.data.msg.MessageSendStatusDetailRepository;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.dto.admin.MessageSendStatusDetailDTO;
import cc.newex.dax.integration.service.admin.MessageSendStatusDetailAdminService;
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
public class MessageSendStatusDetailAdminServiceImpl
        extends AbstractCrudService<MessageSendStatusDetailRepository, MessageSendStatusDetail, MessageSendStatusDetailExample, Long>
        implements MessageSendStatusDetailAdminService {


    @Override
    protected MessageSendStatusDetailExample getPageExample(final String fieldName, final String keyword) {
        final MessageSendStatusDetailExample example = new MessageSendStatusDetailExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 分页查询
     *
     * @param pageInfo
     * @param messageSendStatusDetailDTO
     * @return
     */
    @Override
    public List<MessageSendStatusDetail> listByPage(final PageInfo pageInfo, final MessageSendStatusDetailDTO messageSendStatusDetailDTO) {
        final MessageSendStatusDetailExample messageSendStatusDetailExample = new MessageSendStatusDetailExample();
        final MessageSendStatusDetailExample.Criteria criteria = messageSendStatusDetailExample.createCriteria();
        if (StringUtils.isNotEmpty(messageSendStatusDetailDTO.getChannel())) {
            criteria.andChannelEqualTo(messageSendStatusDetailDTO.getChannel());
        }
        if (StringUtils.isNotEmpty(messageSendStatusDetailDTO.getType())) {
            criteria.andTypeEqualTo(messageSendStatusDetailDTO.getType());
        }
        if (Objects.nonNull(messageSendStatusDetailDTO.getStatus())) {
            criteria.andStatusEqualTo(messageSendStatusDetailDTO.getStatus());
        }
        if (messageSendStatusDetailDTO.getBrokerId() != null) {
            criteria.andBrokerIdEqualTo(messageSendStatusDetailDTO.getBrokerId());
        }
        return this.getByPage(pageInfo, messageSendStatusDetailExample);
    }

}
