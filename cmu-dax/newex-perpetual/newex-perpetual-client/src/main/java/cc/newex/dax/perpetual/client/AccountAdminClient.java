package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.UserBalanceDTO;
import cc.newex.dax.perpetual.dto.UserBalanceParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/admin/v1/perpetual/account")
public interface AccountAdminClient {

    /**
     * 获取用户资产信息
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/balance")
    ResponseResult<DataGridPagerResult<UserBalanceDTO>> account(
            @RequestBody DataGridPager<UserBalanceParam> param);
}
