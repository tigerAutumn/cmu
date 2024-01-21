package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageBlacklistExample;
import cc.newex.dax.integration.data.msg.MessageBlacklistRepository;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;
import cc.newex.dax.integration.service.msg.MessageBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信与邮件黑名单表 服务实现
 *
 * @author newex-team
 * @date 2018-08-12
 */
@Slf4j
@Service
public class MessageBlacklistServiceImpl
        extends AbstractCrudService<MessageBlacklistRepository, MessageBlacklist, MessageBlacklistExample, Integer>
        implements MessageBlacklistService {

    @Autowired
    private MessageBlacklistRepository messageBlacklistRepos;

    @Override
    protected MessageBlacklistExample getPageExample(final String fieldName, final String keyword) {
        final MessageBlacklistExample example = new MessageBlacklistExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}