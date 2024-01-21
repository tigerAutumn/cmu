package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageWhitelistExample;
import cc.newex.dax.integration.data.msg.MessageWhitelistRepository;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;
import cc.newex.dax.integration.service.admin.MessageWhitelistAdminService;
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
public class MessageWhitelistAdminServiceImpl
        extends AbstractCrudService<MessageWhitelistRepository, MessageWhitelist, MessageWhitelistExample, Integer>
        implements MessageWhitelistAdminService {

    @Override
    protected MessageWhitelistExample getPageExample(final String fieldName, final String keyword) {
        final MessageWhitelistExample example = new MessageWhitelistExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<MessageWhitelist> listByPage(final PageInfo pageInfo, final MessageWhitelist messageWhitelist) {
        final MessageWhitelistExample example = new MessageWhitelistExample();
        final MessageWhitelistExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(messageWhitelist.getType())) {
            criteria.andTypeEqualTo(messageWhitelist.getType());
        }
        if (StringUtils.isNotBlank(messageWhitelist.getName())) {
            criteria.andNameLike("%" + messageWhitelist.getName() + "%");
        }
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<MessageWhitelist> getByPage(final PageInfo pageInfo, final String fieldName, final String keyword, final Integer brokerId) {

        final MessageWhitelistExample example = new MessageWhitelistExample();
        final MessageWhitelistExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }


        return this.getByPage(pageInfo, example);
    }


}
