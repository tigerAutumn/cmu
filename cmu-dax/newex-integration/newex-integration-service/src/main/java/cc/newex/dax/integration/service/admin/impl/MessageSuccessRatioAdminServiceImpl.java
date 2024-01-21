package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageSuccessRatioExample;
import cc.newex.dax.integration.data.msg.MessageSuccessRatioRepository;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;
import cc.newex.dax.integration.dto.admin.MessageSuccessRatioDTO;
import cc.newex.dax.integration.service.admin.MessageSuccessRatioAdminService;
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
public class MessageSuccessRatioAdminServiceImpl
        extends AbstractCrudService<MessageSuccessRatioRepository, MessageSuccessRatio, MessageSuccessRatioExample, Integer>
        implements MessageSuccessRatioAdminService {


    @Override
    protected MessageSuccessRatioExample getPageExample(final String fieldName, final String keyword) {
        final MessageSuccessRatioExample example = new MessageSuccessRatioExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 分页查询
     *
     * @param pageInfo
     * @param messageSuccessRatioDTO
     * @return
     */
    @Override
    public List<MessageSuccessRatio> listByPage(final PageInfo pageInfo, final MessageSuccessRatioDTO messageSuccessRatioDTO) {
        final MessageSuccessRatioExample messageSuccessRatioExample = new MessageSuccessRatioExample();
        final MessageSuccessRatioExample.Criteria criteria = messageSuccessRatioExample.createCriteria();
        if (StringUtils.isNotEmpty(messageSuccessRatioDTO.getChannel())) {
            criteria.andChannelEqualTo(messageSuccessRatioDTO.getChannel());
        }
        if (StringUtils.isNotEmpty(messageSuccessRatioDTO.getType())) {
            criteria.andTypeEqualTo(messageSuccessRatioDTO.getType());
        }
        if (Objects.nonNull(messageSuccessRatioDTO.getRatio())) {
            criteria.andRatioEqualTo(messageSuccessRatioDTO.getRatio());
        }
        return this.getByPage(pageInfo, messageSuccessRatioExample);
    }

}
