package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.VersionInfoExample;
import cc.newex.dax.extra.domain.cms.VersionInfo;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;

import java.util.List;

/**
 * 版本信息 服务接口
 *
 * @author liutiejun
 * @date 2018-07-03
 */
public interface VersionInfoAdminService
        extends CrudService<VersionInfo, VersionInfoExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, VersionInfoExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(VersionInfoExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param versionInfo 查询条件参数
     * @return 总记录数
     */
    int count(VersionInfo versionInfo);

    /**
     * 根据platform查询
     *
     * @param platform
     */
    List<VersionInfo> getByPlatform(Integer platform, Integer brokerId);

    /**
     * 获取version info 的分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<VersionInfoDTO> getVersionInfoPagerInfo(DataGridPager<VersionInfoDTO> pager);
}
