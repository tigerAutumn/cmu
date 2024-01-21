package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.customer.WorkOrderAttachmentDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/v1/extra/customer")
public interface ExtraWorkOrderUploadClient {

    /**
     * 客户上传附件
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/uploadFile")
    ResponseResult upload(HttpServletRequest request);


    @PostMapping(value = "/save")
    ResponseResult save(@RequestBody WorkOrderAttachmentDTO workOrderAttachmentDTO);
}
