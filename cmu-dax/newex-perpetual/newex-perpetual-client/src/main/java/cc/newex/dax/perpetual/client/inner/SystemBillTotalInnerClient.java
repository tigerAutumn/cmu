package cc.newex.dax.perpetual.client.inner;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;

@FeignClient(value = "${newex.feignClient.dax.perpetual}",
    path = "/inner/v1/perpetual/systembilltotal")
public interface SystemBillTotalInnerClient {

  @GetMapping(value = "/querySystemBillTotal")
  ResponseResult<List<SystemBillTotalDTO>> querySystemBillTotal(
      @RequestParam(value = "currencyCode", required = false) final String currencyCode,
      @RequestParam(value = "startDateMillis", required = false) final Long startDateMillis,
      @RequestParam(value = "endDateMillis", required = false) final Long endDateMillis);
}
