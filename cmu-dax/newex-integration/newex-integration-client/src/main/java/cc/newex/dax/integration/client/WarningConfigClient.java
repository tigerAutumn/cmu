package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.admin.WarningConfigDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "${newex.feignClient.dax.integration}", path = "/admin/v1/integration/warning/config")
public interface WarningConfigClient {

    @PostMapping("/list")
    ResponseResult getWarningConfig(@RequestBody DataGridPager<WarningConfigDTO> pager);

    @PostMapping(value = "/addConfig")
    ResponseResult addConfig(@RequestBody WarningConfigDTO dto);

    @PostMapping(value = "/editConfig")
    ResponseResult editConfig(@RequestBody WarningConfigDTO dto);

    @PostMapping(value = "/deleteConfig")
    ResponseResult deleteConfig(@RequestParam(value = "id") Long id);
}
