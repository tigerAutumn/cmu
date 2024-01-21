package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-07-03
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/version-info")
public interface ExtraCmsVersionInfoAdminClient {

    /**
     * 保存VersionInfo
     *
     * @param versionInfoDTO
     * @return
     */
    @PostMapping(value = "/save")
    ResponseResult save(@RequestBody final VersionInfoDTO versionInfoDTO);

    /**
     * 更新VersionInfo
     *
     * @param versionInfoDTO
     * @return
     */
    @PostMapping(value = "/update")
    ResponseResult update(@RequestBody final VersionInfoDTO versionInfoDTO);

    /**
     * List VersionInfo
     *
     * @return
     */
    @PostMapping(value = "/list")
    ResponseResult list(@RequestBody final DataGridPager<VersionInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getById")
    ResponseResult getById(@RequestParam("id") final Integer id);

}
