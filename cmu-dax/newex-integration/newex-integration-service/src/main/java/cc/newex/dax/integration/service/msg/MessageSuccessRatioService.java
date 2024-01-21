package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageSuccessRatioExample;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;


/**
 * 信息息发送成功率统计表 服务接口
 *
 * @author newex-team
 * @date 2018-05-10
 */
public interface MessageSuccessRatioService
        extends CrudService<MessageSuccessRatio, MessageSuccessRatioExample, Integer> {
}
