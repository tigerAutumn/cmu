package cc.newex.dax.extra.service.cgm;

import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:36
 */
public interface ProjectService {

    /**
     * 项目申请
     *
     * @param projectTokenInfo
     * @param projectApplyInfo
     */
    int save(ProjectTokenInfo projectTokenInfo, ProjectApplyInfo projectApplyInfo);

    /**
     * 更新申请项目的信息
     * @param projectTokenInfo
     * @param projectApplyInfo
     * @return
     */
    int update(ProjectTokenInfo projectTokenInfo, ProjectApplyInfo projectApplyInfo);

}
