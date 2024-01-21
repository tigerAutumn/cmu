package cc.newex.dax.extra.service.cgm.impl;

import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.service.cgm.ProjectApplyInfoService;
import cc.newex.dax.extra.service.cgm.ProjectService;
import cc.newex.dax.extra.service.cgm.ProjectTokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:36
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectTokenInfoService projectTokenInfoService;

    @Autowired
    private ProjectApplyInfoService projectApplyInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(final ProjectTokenInfo projectTokenInfo, final ProjectApplyInfo projectApplyInfo) {
        final int save = this.projectTokenInfoService.add(projectTokenInfo);

        if (save > 0) {
            projectApplyInfo.setTokenInfoId(projectTokenInfo.getId());

            this.projectApplyInfoService.add(projectApplyInfo);
        }

        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(final ProjectTokenInfo projectTokenInfo,final ProjectApplyInfo projectApplyInfo) {
        final int update = projectTokenInfoService.editById(projectTokenInfo);
        if(update > 0){
            this.projectApplyInfoService.editById(projectApplyInfo);
        }
        return update;
    }

}
