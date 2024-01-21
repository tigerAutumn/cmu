package cc.newex.dax.extra.client.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-08-13
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/inner/v1/extra/project")
public interface ProjectInnerClient {

    /**
     * 保存ProjectPaymentRecord
     *
     * @param projectPaymentRecordDTO
     * @return
     */
    @PostMapping(value = "/payment-record/saveProjectPaymentRecord")
    ResponseResult saveProjectPaymentRecord(@RequestBody final ProjectPaymentRecordDTO projectPaymentRecordDTO);

    /**
     * 查看上币申请详情
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/info/getTokenInfo")
    ResponseResult<ProjectTokenInfoDTO> getTokenInfo(@RequestParam("tokenInfoId") final Long tokenInfoId);

}
