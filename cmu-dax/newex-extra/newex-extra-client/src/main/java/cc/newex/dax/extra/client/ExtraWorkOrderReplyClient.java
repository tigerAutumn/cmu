package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author newex-team
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/customer/work-order/replies")
public interface ExtraWorkOrderReplyClient {

    /**
     * 客服根据Id查看回复记录
     *
     * @param workOrderId
     * @return
     */
    @GetMapping(value = "/{workOrderId}")
    ResponseResult get(@PathVariable(value = "workOrderId") Long workOrderId);

    /**
     * 根据工单Id获取回复记录
     *
     * @param workOrderId
     * @return
     */
    @GetMapping(value = "/replies/{workOrderId}")
    ResponseResult getByWorkOrderId(@PathVariable(value = "workOrderId") Long workOrderId);

    /**
     * 客户发送回复
     *
     * @param workOrderReplyDTO
     * @return
     */
    @PostMapping(value = "/{workOrderId}")
    ResponseResult add(@PathVariable(value = "workOrderId") Long workOrderId, @RequestBody WorkOrderReplyDTO workOrderReplyDTO);
}
