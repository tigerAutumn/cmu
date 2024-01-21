package cc.newex.dax.integration.service.cache;

import cc.newex.dax.integration.domain.msg.MessageTemplate;

/**
 * @author newex-team
 * @date 2018-08-12
 */
public interface AppCacheService {

    void loadAllMessageTemplates();

    MessageTemplate getMessageTemplate(final String locale, final String templateCode);

    void loadAllMessageBlacklists();

    boolean isInMessageBlacklist(String type, String name, Integer brokerId);

    void loadAllMessageWhitelists();
}
