package cc.newex.dax.perpetual.client.inner;

import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.UserActivityRewardDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-12-21
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/inner/v1/perpetual/activity-reward")
public interface UserActivityRewardInnerClient {

    @GetMapping(value = "/list")
    ResponseResult<DataGridPagerResult<UserActivityRewardDTO>> list(
            @RequestParam(value = "brokerId", required = false) final Integer brokerId,
            @RequestParam(value = "userIds", required = false) final Long[] userIds,
            @RequestParam(value = "currencyCodes", required = false) final String[] currencyCodes,
            @RequestParam(value = "startTime", required = false) final Long startTimeMills,
            @RequestParam(value = "endTime", required = false) final Long endTimeMills,
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize);

}
