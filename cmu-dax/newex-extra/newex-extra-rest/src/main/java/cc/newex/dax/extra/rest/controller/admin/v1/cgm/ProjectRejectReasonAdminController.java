package cc.newex.dax.extra.rest.controller.admin.v1.cgm;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;
import cc.newex.dax.extra.dto.cgm.ProjectRejectReasonDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectRejectReasonAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/project/reject-reason")
public class ProjectRejectReasonAdminController {

    @Autowired
    private ProjectRejectReasonAdminService projectRejectReasonAdminService;

    /**
     * 保存ProjectRejectReason
     *
     * @param projectRejectReasonDTO
     * @return
     */
    @PostMapping(value = "/saveProjectRejectReason")
    public ResponseResult saveProjectRejectReason(@RequestBody @Valid final ProjectRejectReasonDTO projectRejectReasonDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectRejectReason projectRejectReason = mapper.map(projectRejectReasonDTO, ProjectRejectReason.class);

        final int save = this.projectRejectReasonAdminService.add(projectRejectReason);

        return ResultUtils.success(save);
    }

    /**
     * 更新ProjectRejectReason
     *
     * @param projectRejectReasonDTO
     * @return
     */
    @PostMapping(value = "/updateProjectRejectReason")
    public ResponseResult updateProjectRejectReason(@RequestBody final ProjectRejectReasonDTO projectRejectReasonDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectRejectReason projectRejectReason = mapper.map(projectRejectReasonDTO, ProjectRejectReason.class);

        final int update = this.projectRejectReasonAdminService.editById(projectRejectReason);

        return ResultUtils.success(update);
    }

    /**
     * 删除ProjectRejectReason
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeProjectRejectReason")
    public ResponseResult removeProjectRejectReason(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

    /**
     * List ProjectRejectReason
     *
     * @return
     */
    @PostMapping(value = "/listProjectRejectReason")
    public ResponseResult listProjectRejectReason(@RequestBody final DataGridPager<ProjectRejectReasonDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final ProjectRejectReason projectRejectReason = mapper.map(pager.getQueryParameter(), ProjectRejectReason.class);
        final ProjectRejectReasonExample example = ProjectRejectReasonExample.toExample(projectRejectReason);

        final List<ProjectRejectReason> list = this.projectRejectReasonAdminService.getByPage(pageInfo, example);
        final List<ProjectRejectReasonDTO> projectRejectReasonDTOS = mapper.map(
                list, new TypeToken<List<ProjectRejectReasonDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), projectRejectReasonDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getProjectRejectReasonById")
    public ResponseResult getProjectRejectReasonById(@RequestParam("id") final Long id) {
        final ProjectRejectReason projectRejectReason = this.projectRejectReasonAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        ProjectRejectReasonDTO projectRejectReasonDTO = null;
        if (Objects.nonNull(projectRejectReason)) {
            projectRejectReasonDTO = mapper.map(projectRejectReason, ProjectRejectReasonDTO.class);
        }

        return ResultUtils.success(projectRejectReasonDTO);
    }

    /**
     * 查询拒绝理由
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/getProjectRejectReasonByTokenInfoId")
    public ResponseResult getProjectRejectReasonByTokenInfoId(@RequestParam("tokenInfoId") @NotNull final Long tokenInfoId) {
        final ProjectRejectReason projectRejectReason = this.projectRejectReasonAdminService.getByTokenInfoId(tokenInfoId);

        final ModelMapper mapper = new ModelMapper();

        ProjectRejectReasonDTO projectRejectReasonDTO = null;
        if (Objects.nonNull(projectRejectReason)) {
            projectRejectReasonDTO = mapper.map(projectRejectReason, ProjectRejectReasonDTO.class);
        }

        return ResultUtils.success(projectRejectReasonDTO);
    }

}
