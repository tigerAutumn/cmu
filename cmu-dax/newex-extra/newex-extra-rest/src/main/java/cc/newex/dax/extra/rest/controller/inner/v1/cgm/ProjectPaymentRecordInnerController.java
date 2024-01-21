package cc.newex.dax.extra.rest.controller.inner.v1.cgm;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cgm.ProjectPaymentRecord;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectPaymentRecordAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author liutiejun
 * @date 2018-08-13
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/extra/project/payment-record")
public class ProjectPaymentRecordInnerController {

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

}
