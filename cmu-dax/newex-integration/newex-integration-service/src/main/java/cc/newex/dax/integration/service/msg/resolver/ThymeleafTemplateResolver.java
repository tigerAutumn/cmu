package cc.newex.dax.integration.service.msg.resolver;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.service.msg.enums.MessageStatusEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

/**
 * @author newex-team
 * @date 2018-05-14
 */
@Slf4j
@Component("thymeleafTemplateResolver")
public class ThymeleafTemplateResolver
        extends AbstractMessageTemplateResolver implements MessageTemplateResolver {

    @Resource
    private TemplateEngine templateEngine;

    @Override
    public String render(final Message message, final MessageTemplate template) {
        return this.renderByThymeleaf(message, template);
    }

    protected String renderByThymeleaf(final Message message, final MessageTemplate template) {
        String templateContent = template.getContent();
        final Context ctx = new Context();
        try {
            ctx.setVariables(JSON.parseObject(message.getTemplateParams()));
            templateContent = this.templateEngine.process(template.getContent(), ctx);
        } catch (final Exception ex) {
            log.error("message template render error", ex);
            message.setRetryTimes(MessageStatusEnum.MESSAGE_TEMPLATE_RENDER_ERROR.getValue());
        }
        return templateContent;
    }
}
