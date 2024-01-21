package cc.newex.dax.extra.rest.controller.admin.v1.cgm;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.util.DateUtils;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectInfo2DTO;
import cc.newex.dax.extra.dto.cgm.ProjectInfoDTO;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;
import cc.newex.dax.extra.service.cgm.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * The type Project info admin controller.
 *
 * @author newex -team
 * @date 2018 /9/29 18:03
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/project/info")
public class ProjectInfoAdminController {

    @Autowired
    private ProjectService projectService;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Project info admin controller.
     */
    public ProjectInfoAdminController() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);

    }

    /**
     * Save project info response result.
     *
     * @param projectInfoDTO the project info dto
     * @return the response result
     */
    @PostMapping("/saveProjectInfo")
    public ResponseResult saveProjectInfo(@RequestBody final ProjectInfoDTO projectInfoDTO) {

        final ProjectApplyInfo projectApplyInfo = this.modelMapper.map(projectInfoDTO, ProjectApplyInfo.class);
        final ProjectTokenInfo projectTokenInfo = this.modelMapper.map(projectInfoDTO, ProjectTokenInfo.class);

        this.setDefaultValue(projectTokenInfo);

        final int save = this.projectService.save(projectTokenInfo, projectApplyInfo);

        if (save <= 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.ADD_ERROR);
        }

        return ResultUtils.success(save);

    }

    private void setDefaultValue(final ProjectTokenInfo projectTokenInfo) {
        // 发币时间
        Date startTime = projectTokenInfo.getStartTime();
        if (startTime == null) {
            // TIMESTAMP has a range of '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC.
            startTime = DateUtils.getADate(2037, 12, 31, 0, 0, 0);
            projectTokenInfo.setStartTime(startTime);
        }

        // 上线时间
        Date onlineTime = projectTokenInfo.getOnlineTime();
        if (onlineTime == null) {
            // TIMESTAMP has a range of '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC.
            onlineTime = DateUtils.getADate(2037, 12, 31, 0, 0, 0);
            projectTokenInfo.setOnlineTime(onlineTime);
        }

        // 设置待审核状态
        projectTokenInfo.setStatus(ProjectStatusEnum.CHECK.getCode().byteValue());
    }


    /**
     * Update project info response result.
     *
     * @param projectInfo2DTO the project info 2 dto
     * @return the response result
     */
    @PostMapping("/updateProjectInfo")
    public ResponseResult updateProjectInfo(@RequestBody final ProjectInfo2DTO projectInfo2DTO) {

        final ProjectApplyInfo projectApplyInfo = this.modelMapper.map(projectInfo2DTO, ProjectApplyInfo.class);
        final ProjectTokenInfo projectTokenInfo = this.modelMapper.map(projectInfo2DTO, ProjectTokenInfo.class);
        projectApplyInfo.setId(projectInfo2DTO.getApplyInfoId());
        projectTokenInfo.setId(projectInfo2DTO.getTokenInfoId());
        final int update = this.projectService.update(projectTokenInfo, projectApplyInfo);
        if (update <= 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.ADD_ERROR);
        }

        return ResultUtils.success(update);
    }

}
