package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.BannerNoticeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author newex-team
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/banner-notices")
public interface ExtraCmsBannerNoticeAdminClient {

    /**
     * 保存banner
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/saveBannerNotice")
    ResponseResult saveBannerNotice(@RequestBody final BannerNoticeDTO params);


    /**
     * 更新banner
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/updateBannerNotice")
    ResponseResult updateBannerNotice(@RequestBody final BannerNoticeDTO params);

    /**
     * list banner
     * @param pager
     * @return
     */
    @PostMapping(value = "/listBannerNotice")
    ResponseResult listBannerNotice(@RequestBody final DataGridPager<BannerNoticeDTO> pager);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping(value = "/updateStatus")
    ResponseResult updateStatus(@RequestParam("id") final Long id,
                                @RequestParam("status") final Integer status);


    /**
     * 通过id 获取信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getById")
    ResponseResult getById(@RequestParam("id") final Long id);


}
