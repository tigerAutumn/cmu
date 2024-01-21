package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.vlink.VlinkAdminDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author gi
 * @date 11/16/18
 */

@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/vlink/contract")
public interface ExtraVlinkCmsAdminClient {

    /**
     * 获取算力购买转账记录
     * @param pager
     * @return
     */
    @PostMapping(value = "/list")
    ResponseResult list(@RequestBody DataGridPager<VlinkAdminDTO> pager);
}
