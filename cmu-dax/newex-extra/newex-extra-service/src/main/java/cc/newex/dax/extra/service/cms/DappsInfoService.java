package cc.newex.dax.extra.service.cms;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.DappsInfoExample;
import cc.newex.dax.extra.domain.cms.DappsInfo;
import cc.newex.dax.extra.dto.cms.DAppsAdminDTO;

/**
 * dapps应用程序表 服务接口
 *
 * @author mbg.generated
 * @date 2018-09-12 17:14:02
 */
public interface DappsInfoService extends CrudService<DappsInfo, DappsInfoExample, Long> {

    /**
     * 获取DApp的分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<DAppsAdminDTO> getDAppPagerInfo(DataGridPager<DAppsAdminDTO> pager);
}