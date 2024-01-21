package cc.newex.dax.integration.service.msg.resolver;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.service.msg.enums.MessageStatusEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author newex-team
 * @date 2018-05-14
 */
@Slf4j
@Component("stringReplaceTemplateResolver")
public class StringReplaceTemplateResolver
        extends AbstractMessageTemplateResolver implements MessageTemplateResolver {

    @Override
    public String render(final Message message, final MessageTemplate template) {
        return this.renderByStringReplace(message, template);
    }

    protected String renderByStringReplace(final Message message, final MessageTemplate template) {
        String templateContent = template.getContent();
        try {
            final Map<String, Object> params = MapUtils.emptyIfNull(JSON.parseObject(message.getTemplateParams()));
            for (final String key : params.keySet()) {
                final Object value = params.get(key);
                templateContent = StringUtils.replace(templateContent, "${" + key + "}", value.toString());
            }
        } catch (final Exception ex) {
            log.error("message template render error", ex);
            message.setRetryTimes(MessageStatusEnum.MESSAGE_TEMPLATE_RENDER_ERROR.getValue());
        }
        return templateContent;
    }
}
