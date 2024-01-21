package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The interface System bill total admin client.
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}",
        path = "/admin/v1/perpetual/systembilltotal")
public interface SystemBillTotalAdminClient {

    /**
     * Query system bill total response result.
     *
     * @param currencyCode    the currency code
     * @param startDateMillis the start date millis
     * @param endDateMillis   the end date millis
     * @return the response result
     */
    @GetMapping(value = "/querySystemBillTotal")
    ResponseResult<List<SystemBillTotalDTO>> querySystemBillTotal(@RequestParam(value = "currencyCode", required = false) final String currencyCode,
                                                                  @RequestParam(value = "startDateMillis", required = false) final Long startDateMillis,
                                                                  @RequestParam(value = "endDateMillis", required = false) final Long endDateMillis);
}
