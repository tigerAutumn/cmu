package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageTemplateExample;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import org.springframework.stereotype.Repository;

/**
 * 信息模板表数据访问类
 *
 * @author newex-team
 * @date 2018-04-13
 */
@Repository
public interface MessageTemplateRepository
        extends CrudRepository<MessageTemplate, MessageTemplateExample, Integer> {
}