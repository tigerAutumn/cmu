package cc.newex.dax.extra.rest.controller.admin.v1.cgm;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.dto.cgm.ProjectApplyInfoDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectApplyInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/project/apply-info")
public class ProjectApplyInfoAdminController {

    @Autowired
    private ProjectApplyInfoAdminService projectApplyInfoAdminService;

    /**
     * 保存ProjectApplyInfo
     *
     * @param projectApplyInfoDTO
     * @return
     */
    @PostMapping(value = "/saveProjectApplyInfo")
    public ResponseResult saveProjectApplyInfo(@RequestBody final ProjectApplyInfoDTO projectApplyInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectApplyInfo projectApplyInfo = mapper.map(projectApplyInfoDTO, ProjectApplyInfo.class);

        final int save = this.projectApplyInfoAdminService.add(projectApplyInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新ProjectApplyInfo
     *
     * @param projectApplyInfoDTO
     * @return
     */
    @PostMapping(value = "/updateProjectApplyInfo")
    public ResponseResult updateProjectApplyInfo(@RequestBody final ProjectApplyInfoDTO projectApplyInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectApplyInfo projectApplyInfo = mapper.map(projectApplyInfoDTO, ProjectApplyInfo.class);

        final int update = this.projectApplyInfoAdminService.editById(projectApplyInfo);

        return ResultUtils.success(update);
    }

    /**
     * 删除ProjectApplyInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeProjectApplyInfo")
    public ResponseResult removeProjectApplyInfo(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

    /**
     * List ProjectApplyInfo
     *
     * @return
     */
    @PostMapping(value = "/listProjectApplyInfo")
    public ResponseResult listProjectApplyInfo(@RequestBody final DataGridPager<ProjectApplyInfoDTO> pager) {

        final DataGridPagerResult<ProjectApplyInfoDTO> result = this.projectApplyInfoAdminService.getProjectApplyPageInfo(pager);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getProjectApplyInfoById")
    public ResponseResult getProjectApplyInfoById(@RequestParam("id") final Long id) {
        final ProjectApplyInfo projectApplyInfo = this.projectApplyInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final ProjectApplyInfoDTO projectApplyInfoDTO = mapper.map(projectApplyInfo, ProjectApplyInfoDTO.class);

        return ResultUtils.success(projectApplyInfoDTO);
    }

}
