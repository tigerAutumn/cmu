package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageSuccessRatioExample;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;
import cc.newex.dax.integration.dto.admin.MessageSuccessRatioDTO;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface MessageSuccessRatioAdminService
        extends CrudService<MessageSuccessRatio, MessageSuccessRatioExample, Integer> {

    List<MessageSuccessRatio> listByPage(PageInfo pageInfo, MessageSuccessRatioDTO messageSuccessRatioDTO);
}
