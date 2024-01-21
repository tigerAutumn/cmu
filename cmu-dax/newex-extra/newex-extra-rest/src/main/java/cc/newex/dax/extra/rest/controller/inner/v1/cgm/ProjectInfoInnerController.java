package cc.newex.dax.extra.rest.controller.inner.v1.cgm;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectTokenInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2018-08-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/inner/v1/extra/project/info")
public class ProjectInfoInnerController {

    @Autowired
    private ProjectTokenInfoAdminService projectTokenInfoAdminService;

    /**
     * 查看上币申请详情
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/getTokenInfo")
    public ResponseResult<ProjectTokenInfoDTO> getTokenInfo(@RequestParam("tokenInfoId") @NotNull final Long tokenInfoId) {
        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoAdminService.getById(tokenInfoId);

        final ModelMapper mapper = new ModelMapper();

        ProjectTokenInfoDTO projectTokenInfoDTO = null;
        if (projectTokenInfo != null) {
            projectTokenInfoDTO = mapper.map(projectTokenInfo, ProjectTokenInfoDTO.class);
        }

        return ResultUtils.success(projectTokenInfoDTO);
    }

}
