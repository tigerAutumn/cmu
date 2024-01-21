package cc.newex.dax.integration.service.cache.impl;

import cc.newex.dax.integration.common.consts.CacheKeyConsts;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.domain.msg.MessageWhitelist;
import cc.newex.dax.integration.service.cache.AppCacheService;
import cc.newex.dax.integration.service.msg.MessageBlacklistService;
import cc.newex.dax.integration.service.msg.MessageTemplateService;
import cc.newex.dax.integration.service.msg.MessageWhitelistService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author newex-team
 * @date 2018-08-12
 */
@Slf4j
@Service
public class AppCacheServiceImpl implements AppCacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MessageTemplateService templateService;
    @Autowired
    private MessageBlacklistService blacklistService;
    @Autowired
    private MessageWhitelistService whitelistService;

    @PostConstruct
    @Override
    public void loadAllMessageTemplates() {
        this.redisTemplate.delete(CacheKeyConsts.MESSAGE_TEMPLATE_CODE_KEY);
        final List<MessageTemplate> templates = this.templateService.getAll();
        if (CollectionUtils.isNotEmpty(templates)) {
            templates.forEach(e ->
                    this.redisTemplate.opsForHash().put(
                            CacheKeyConsts.MESSAGE_TEMPLATE_CODE_KEY,
                            this.getMessageTemplateKey(e.getLocale(), e.getCode()),
                            JSON.toJSONString(e))
            );
        }
        AppCacheServiceImpl.log.info("load message templates finished.");
    }

    @Override
    public MessageTemplate getMessageTemplate(final String locale, final String templateCode) {
        final Object value = this.redisTemplate.opsForHash().get(CacheKeyConsts.MESSAGE_TEMPLATE_CODE_KEY,
                this.getMessageTemplateKey(locale, templateCode));
        return value == null ? null : JSON.parseObject(value.toString(), MessageTemplate.class);
    }

    @PostConstruct
    @Override
    public void loadAllMessageBlacklists() {
        this.redisTemplate.delete(CacheKeyConsts.MESSAGE_BLACK_LIST_KEY);
        final List<MessageBlacklist> blacklists = this.blacklistService.getAll();
        if (CollectionUtils.isNotEmpty(blacklists)) {
            blacklists.forEach(e ->
                    this.redisTemplate.opsForHash().put(
                            CacheKeyConsts.MESSAGE_BLACK_LIST_KEY,
                            this.getBlackOrWhitelistKey(e.getType(), e.getName(), e.getBrokerId()),
                            e.getName())
            );
        }
        AppCacheServiceImpl.log.info("load message black list finished.");
    }

    @Override
    public boolean isInMessageBlacklist(final String type, final String name, final Integer brokerId) {
        return this.redisTemplate.opsForHash().hasKey(
                CacheKeyConsts.MESSAGE_BLACK_LIST_KEY,
                this.getBlackOrWhitelistKey(type, name, brokerId)
        );
    }

    @PostConstruct
    @Override
    public void loadAllMessageWhitelists() {
        this.redisTemplate.delete(CacheKeyConsts.MESSAGE_WHITE_LIST_KEY);
        final List<MessageWhitelist> whitelists = this.whitelistService.getAll();
        if (CollectionUtils.isNotEmpty(whitelists)) {
            whitelists.forEach(e ->
                    this.redisTemplate.opsForHash().put(
                            CacheKeyConsts.MESSAGE_WHITE_LIST_KEY,
                            this.getBlackOrWhitelistKey(e.getType(), e.getName(), e.getBrokerId()),
                            String.valueOf(e.getMaxRequestTimes()))
            );
        }
        AppCacheServiceImpl.log.info("load message white list finished.");
    }

    private String getMessageTemplateKey(final String locale, final String templateCode) {
        return StringUtils.joinWith("_", StringUtils.lowerCase(locale), templateCode);
    }

    private String getBlackOrWhitelistKey(final String type, final String name, final Integer brokerId) {
        return StringUtils.joinWith("_", StringUtils.lowerCase(type), name, brokerId);
    }
}
