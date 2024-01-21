package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.conf.WarningConfigExample;
import cc.newex.dax.integration.domain.conf.WarningConfig;
import cc.newex.dax.integration.dto.admin.WarningConfigDTO;

import java.util.List;

/**
 * 报警配置表 服务接口
 *
 * @author mbg.generated
 * @date 2018-07-23 16:10:04
 */
public interface WarningConfigAdminService extends CrudService<WarningConfig, WarningConfigExample, Long> {

    List<WarningConfig> listByPage(PageInfo pageInfo, WarningConfigDTO dto);
}