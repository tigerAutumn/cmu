package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.DAppsAdminDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author newex-team
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/DApps")
public interface ExtraCmsDappsAdminClient {

    /**
     * listApps
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/getApps")
    ResponseResult listApps(@RequestBody final DataGridPager pager);

    /**
     * add
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/add")
    ResponseResult add(@RequestBody final DAppsAdminDTO dto);

    /**
     * edit
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/edit")
    ResponseResult edit(@RequestBody final DAppsAdminDTO dto);


}
