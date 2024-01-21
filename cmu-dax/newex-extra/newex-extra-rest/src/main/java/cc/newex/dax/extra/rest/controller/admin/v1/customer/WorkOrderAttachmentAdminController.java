package cc.newex.dax.extra.rest.controller.admin.v1.customer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.customer.WorkOrderAttachment;
import cc.newex.dax.extra.dto.customer.WorkOrderAttachmentDTO;
import cc.newex.dax.extra.service.customer.WorkOrderAttachmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单附件表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-order-attachments")
public class WorkOrderAttachmentAdminController {

    @Autowired
    private WorkOrderAttachmentService workOrderAttachmentService;


    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final WorkOrderAttachmentDTO po) {
        final ModelMapper map = new ModelMapper();
        final WorkOrderAttachment workOrderAttachment = map.map(po, WorkOrderAttachment.class);
        this.workOrderAttachmentService.add(workOrderAttachment);
        return ResultUtils.success(po);
    }


    @GetMapping(value = "")
    public ResponseResult get(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<WorkOrderAttachment> list = this.workOrderAttachmentService.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }


    @GetMapping(value = "/{id}")
    public ResponseResult get(final Long id) {
        return ResultUtils.success(this.workOrderAttachmentService.getById(id));
    }

    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final WorkOrderAttachment po) {
        return ResultUtils.success(this.workOrderAttachmentService.editById(po));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(final Long id) {
        return ResultUtils.success(this.workOrderAttachmentService.removeById(id));
    }
}



