package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.customer.WorkOrderCustomerDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderOpLogDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author newex-team
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/customer/work-orders")
public interface ExtraWorkOrderCustomerClient {
    /**
     * 工单列表查询
     *
     * @return
     */
    @PostMapping("/workOrderList")
    ResponseResult getListByPage(@RequestBody final DataGridPager<WorkOrderDTO> pager);

    /**
     * 客服转发工单
     *
     * @param id
     * @param workOrderCustomerDTO
     * @return
     */
    @PostMapping(value = "/transfer")
    ResponseResult transfer(@RequestParam(value = "id") Long id,
                            @RequestBody WorkOrderCustomerDTO workOrderCustomerDTO);

    /**
     * 获取工作页工单列表
     *
     * @param filedName
     * @param keyword
     * @param adminUserId
     * @param status
     * @return
     */
    @GetMapping(value = "/workOrderStatusList")
    ResponseResult getListByStatus(@RequestBody final DataGridPager pager,
                                   @RequestParam(value = "filedName", required = false) String filedName,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "adminUserId") Integer adminUserId,
                                   @RequestParam(value = "status") Integer status);

    /**
     * 工单状态列表
     *
     * @return
     */
    @GetMapping("/status")
    ResponseResult status();

    /**
     * 工单置为等待
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/wait")
    ResponseResult setWait(@RequestParam(value = "id") Long id);

    /**
     * 工单设置为已解决
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/complete")
    ResponseResult setComplete(@RequestParam(value = "id") Long id);

    /**
     * 工单日志
     *
     * @param opType
     * @param po
     * @return
     */
    @PostMapping(value = "/oplogs/log")
    ResponseResult log(@RequestParam(value = "opType") Integer opType, @RequestBody WorkOrderOpLogDTO po);

    /**
     * 客服工单数据整合
     *
     * @param adminUserId
     * @return
     */
    @GetMapping(value = "/data")
    ResponseResult evaluation(@RequestParam(value = "adminUserId") Long adminUserId);

}
