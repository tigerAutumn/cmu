package cc.newex.dax.extra.rest.controller.admin.v1.cgm;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cgm.ProjectPaymentRecord;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectPaymentRecordAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/project/payment-record")
public class ProjectPaymentRecordAdminController {

    @Autowired
    private ProjectPaymentRecordAdminService projectPaymentRecordAdminService;

    /**
     * 保存ProjectPaymentRecord
     *
     * @param projectPaymentRecordDTO
     * @return
     */
    @PostMapping(value = "/saveProjectPaymentRecord")
    public ResponseResult saveProjectPaymentRecord(@RequestBody @Valid final ProjectPaymentRecordDTO projectPaymentRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectPaymentRecord projectPaymentRecord = mapper.map(projectPaymentRecordDTO, ProjectPaymentRecord.class);

        final int save = this.projectPaymentRecordAdminService.add(projectPaymentRecord);

        return ResultUtils.success(save);
    }

    /**
     * 更新ProjectPaymentRecord
     *
     * @param projectPaymentRecordDTO
     * @return
     */
    @PostMapping(value = "/updateProjectPaymentRecord")
    public ResponseResult updateProjectPaymentRecord(@RequestBody final ProjectPaymentRecordDTO projectPaymentRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProjectPaymentRecord projectPaymentRecord = mapper.map(projectPaymentRecordDTO, ProjectPaymentRecord.class);

        final int update = this.projectPaymentRecordAdminService.editById(projectPaymentRecord);

        return ResultUtils.success(update);
    }

    /**
     * 删除ProjectPaymentRecord
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeProjectPaymentRecord")
    public ResponseResult removeProjectPaymentRecord(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

    /**
     * List ProjectPaymentRecord
     *
     * @return
     */
    @PostMapping(value = "/listProjectPaymentRecord")
    public ResponseResult listProjectPaymentRecord(@RequestBody final DataGridPager<ProjectPaymentRecordDTO> pager) {

        final DataGridPagerResult<ProjectPaymentRecordDTO> result = this.projectPaymentRecordAdminService.getProjectPaymentRecordPageInfo(pager);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getProjectPaymentRecordById")
    public ResponseResult getProjectPaymentRecordById(@RequestParam("id") final Long id) {
        final ProjectPaymentRecord projectPaymentRecord = this.projectPaymentRecordAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final ProjectPaymentRecordDTO projectPaymentRecordDTO = mapper.map(projectPaymentRecord, ProjectPaymentRecordDTO.class);

        return ResultUtils.success(projectPaymentRecordDTO);
    }

    /**
     * 用于查询保证金支付记录
     *
     * @param tokenInfoId
     * @param currencyType
     * @return
     */
    @GetMapping(value = "/getProjectPaymentRecordByTokenInfoId")
    public ResponseResult getProjectPaymentRecordByTokenInfoId(@RequestParam("tokenInfoId") @NotNull final Long tokenInfoId,
                                                               @RequestParam("currencyType") @NotNull final Byte currencyType) {
        final List<ProjectPaymentRecord> list = this.projectPaymentRecordAdminService.getByTokenInfoId(tokenInfoId, currencyType);

        final ModelMapper mapper = new ModelMapper();

        List<ProjectPaymentRecordDTO> projectPaymentRecordDTOS = null;

        if (list != null) {
            projectPaymentRecordDTOS = mapper.map(
                    list, new TypeToken<List<ProjectPaymentRecordDTO>>() {
                    }.getType()
            );
        }

        return ResultUtils.success(projectPaymentRecordDTOS);
    }

}
