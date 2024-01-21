package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageBlacklistExample;
import cc.newex.dax.integration.data.msg.MessageBlacklistRepository;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;
import cc.newex.dax.integration.service.admin.MessageBlacklistAdminService;
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
public class MessageBlacklistAdminServiceImpl
        extends AbstractCrudService<MessageBlacklistRepository, MessageBlacklist, MessageBlacklistExample, Integer>
        implements MessageBlacklistAdminService {

    @Override
    protected MessageBlacklistExample getPageExample(final String fieldName, final String keyword) {
        final MessageBlacklistExample example = new MessageBlacklistExample();
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
    public List<MessageBlacklist> listByPage(final PageInfo pageInfo, final MessageBlacklist messageBlacklist) {
        final MessageBlacklistExample example = new MessageBlacklistExample();
        final MessageBlacklistExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(messageBlacklist.getType())) {
            criteria.andTypeEqualTo(messageBlacklist.getType());
        }
        if (StringUtils.isNotBlank(messageBlacklist.getName())) {
            criteria.andNameLike("%" + messageBlacklist.getName() + "%");
        }
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<MessageBlacklist> getByPage(final PageInfo pageInfo, final String fieldName, final String keyword, final Integer brokerId) {

        final MessageBlacklistExample example = new MessageBlacklistExample();
        final MessageBlacklistExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }


        return this.getByPage(pageInfo, example);
    }


}
