package cc.newex.dax.integration.service.msg.resolver;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.domain.msg.MessageTemplate;

/**
 * @author newex-team
 * @date 2018-05-14
 */
public interface MessageTemplateResolver {
    /**
     * @param message  消息对象
     * @param template 消息模板对象
     * @return 返回渲染后的模板文本字符串
     */
    String render(final Message message, final MessageTemplate template);
}
