package cc.newex.dax.extra.rest.controller.admin.v1.cgm;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectApplyInfoDTO;
import cc.newex.dax.extra.dto.cgm.ProjectDetailInfoDTO;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectApplyInfoAdminService;
import cc.newex.dax.extra.service.admin.cgm.ProjectTokenInfoAdminService;
import cc.newex.dax.extra.valid.cgm.PassGroup;
import cc.newex.dax.extra.valid.cgm.ScheduleGroup;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/project/token-info")
public class ProjectTokenInfoAdminController {

    @Autowired
    private ProjectTokenInfoAdminService projectTokenInfoAdminService;

    @Autowired
    private ProjectApplyInfoAdminService projectApplyInfoAdminService;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 保存ProjectTokenInfo
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/saveProjectTokenInfo")
    public ResponseResult saveProjectTokenInfo(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectTokenInfo projectTokenInfo = mapper.map(projectTokenInfoDTO, ProjectTokenInfo.class);

        final int save = this.projectTokenInfoAdminService.add(projectTokenInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新ProjectTokenInfo
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/updateProjectTokenInfo")
    public ResponseResult updateProjectTokenInfo(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectTokenInfo projectTokenInfo = mapper.map(projectTokenInfoDTO, ProjectTokenInfo.class);

        final int update = this.projectTokenInfoAdminService.editById(projectTokenInfo);

        return ResultUtils.success(update);
    }

    /**
     * 删除ProjectTokenInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeProjectTokenInfo")
    public ResponseResult removeProjectTokenInfo(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

    /**
     * List ProjectTokenInfo
     *
     * @return
     */
    @PostMapping(value = "/listProjectTokenInfo")
    public ResponseResult listProjectTokenInfo(@RequestBody final DataGridPager<ProjectTokenInfoDTO> pager) {

        final DataGridPagerResult<ProjectTokenInfoDTO> result = this.projectTokenInfoAdminService.getProjectTokenPageInfo(pager);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getProjectTokenInfoById")
    public ResponseResult getProjectTokenInfoById(@RequestParam("id") @NotNull final Long id) {
        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final ProjectTokenInfoDTO projectTokenInfoDTO = mapper.map(projectTokenInfo, ProjectTokenInfoDTO.class);

        return ResultUtils.success(projectTokenInfoDTO);
    }

    /**
     * 查看上币申请详情
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/getDetailInfo")
    public ResponseResult getDetailInfo(@RequestParam("tokenInfoId") @NotNull final Long tokenInfoId) {
        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoAdminService.getById(tokenInfoId);
        final ProjectApplyInfo projectApplyInfo = this.projectApplyInfoAdminService.getByTokenInfoId(tokenInfoId);

        final ModelMapper mapper = new ModelMapper();

        ProjectTokenInfoDTO projectTokenInfoDTO = null;
        if (projectTokenInfo != null) {
            // 设置logo的绝对路径
            projectTokenInfo.setImgName(this.fileUploadService.getSignedUrl(projectTokenInfo.getImgName()));

            projectTokenInfoDTO = mapper.map(projectTokenInfo, ProjectTokenInfoDTO.class);
        }

        ProjectApplyInfoDTO projectApplyInfoDTO = null;
        if (projectApplyInfo != null) {
            // 设置白皮书的绝对路径
            projectApplyInfo.setWhitePaper(this.fileUploadService.getSignedUrl(projectApplyInfo.getWhitePaper()));

            projectApplyInfoDTO = mapper.map(projectApplyInfo, ProjectApplyInfoDTO.class);
        }

        final ProjectDetailInfoDTO projectDetailInfoDTO = ProjectDetailInfoDTO.builder()
                .tokenInfoId(tokenInfoId)
                .projectTokenInfoDTO(projectTokenInfoDTO)
                .projectApplyInfoDTO(projectApplyInfoDTO)
                .build();

        return ResultUtils.success(projectDetailInfoDTO);
    }

    /**
     * 通过初始审核
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/pass")
    public ResponseResult pass(@RequestBody @Validated({PassGroup.class}) final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final int update = this.projectTokenInfoAdminService.pass(projectTokenInfoDTO);

        return ResultUtils.success(update);
    }

    /**
     * 排期：给初始审核状态的数据设置上线时间
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/schedule")
    public ResponseResult schedule(@RequestBody @Validated({ScheduleGroup.class}) final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final int update = this.projectTokenInfoAdminService.schedule(projectTokenInfoDTO);

        return ResultUtils.success(update);
    }

    /**
     * 确认完成上线
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/online")
    public ResponseResult online(@RequestBody @Valid final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final int update = this.projectTokenInfoAdminService.online(projectTokenInfoDTO);

        return ResultUtils.success(update);
    }

}
