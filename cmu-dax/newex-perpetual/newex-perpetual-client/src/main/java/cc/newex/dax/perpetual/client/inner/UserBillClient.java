package cc.newex.dax.perpetual.client.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.InnerUserBillDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/inner/v1/perpetual/userBill")
public interface UserBillClient {

    /**
     * 获取账单信息
     *
     * @param userIds       用户 id 数组
     * @param brokerIds     broker id 数组
     * @param contractCodes 合约 code 数组
     * @param startTime     起始时间
     * @param endTime       结束时间
     * @param id            起始id 将会获取 > id 之后的数据
     * @param size          每次请求返回的最大条数
     * @return
     */
    @GetMapping("/getUserBill")
    ResponseResult<List<InnerUserBillDTO>> getUserbill(@RequestParam(value = "userId", required = false) final Long[] userIds,
                                                       @RequestParam(value = "brokerId", required = false) final Integer[] brokerIds,
                                                       @RequestParam(value = "contractCode", required = false) final String[] contractCodes,
                                                       @RequestParam(value = "startTime", required = false) final Long startTime,
                                                       @RequestParam(value = "endTime", required = false) final Long endTime,
                                                       @RequestParam(value = "id", required = false, defaultValue = "0") final Long id,
                                                       @RequestParam(value = "size", required = false, defaultValue = "1000") final Integer size);

}
