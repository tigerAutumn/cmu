package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageWhitelistExample;
import cc.newex.dax.integration.data.msg.MessageWhitelistRepository;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;
import cc.newex.dax.integration.service.msg.MessageWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信与邮件发送白名单表 服务实现
 *
 * @author newex-team
 * @date 2018-08-13
 */
@Slf4j
@Service
public class MessageWhitelistServiceImpl
        extends AbstractCrudService<MessageWhitelistRepository, MessageWhitelist, MessageWhitelistExample, Integer>
        implements MessageWhitelistService {

    @Autowired
    private MessageWhitelistRepository messageWhitelistRepos;

    @Override
    protected MessageWhitelistExample getPageExample(final String fieldName, final String keyword) {
        final MessageWhitelistExample example = new MessageWhitelistExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}