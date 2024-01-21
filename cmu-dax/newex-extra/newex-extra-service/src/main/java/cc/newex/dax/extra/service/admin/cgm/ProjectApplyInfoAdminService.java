package cc.newex.dax.extra.service.admin.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectApplyInfoExample;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.dto.cgm.ProjectApplyInfoDTO;

/**
 * 项目信息表 服务接口
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:11
 */
public interface ProjectApplyInfoAdminService extends CrudService<ProjectApplyInfo, ProjectApplyInfoExample, Long> {

    /**
     * 获取Project apply分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<ProjectApplyInfoDTO> getProjectApplyPageInfo(DataGridPager<ProjectApplyInfoDTO> pager);


    ProjectApplyInfo getByTokenInfoId(Long tokenInfoId);

}