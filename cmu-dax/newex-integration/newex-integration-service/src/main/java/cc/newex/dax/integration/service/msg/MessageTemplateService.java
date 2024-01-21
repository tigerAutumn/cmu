package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageTemplateExample;
import cc.newex.dax.integration.domain.msg.MessageTemplate;


/**
 * 信息模板服务接口
 *
 * @author newex-team
 * @date 2018-04-13
 */
public interface MessageTemplateService
        extends CrudService<MessageTemplate, MessageTemplateExample, Integer> {
    /**
     * @param locale
     * @param code
     * @return
     */
    MessageTemplate getTemplateByCode(String locale, String code);


}
