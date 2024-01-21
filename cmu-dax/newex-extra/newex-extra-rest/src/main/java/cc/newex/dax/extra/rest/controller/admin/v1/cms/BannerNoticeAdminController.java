package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.BannerNotice;
import cc.newex.dax.extra.domain.cms.I18nLanguage;
import cc.newex.dax.extra.dto.cms.BannerNoticeDTO;
import cc.newex.dax.extra.rest.common.util.CacheKey;
import cc.newex.dax.extra.service.admin.cms.BannerNoticeAdminService;
import cc.newex.dax.extra.service.admin.cms.I18nLanguageAdminService;
import cc.newex.dax.extra.service.cache.AppCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author newex-team
 * @date: 2018-05-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/v1/extra/cms/banner-notices")
public class BannerNoticeAdminController {
    @Autowired
    private BannerNoticeAdminService bannerNoticeAdminService;
    @Autowired
    private AppCacheService appCacheService;
    @Autowired
    private I18nLanguageAdminService i18nLanguageAdminService;

    /**
     * @param params
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBannerNotice")
    public ResponseResult saveBannerNotice(@RequestBody final BannerNoticeDTO params,
                                           final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final BannerNotice bannerNotice = mapper.map(params, BannerNotice.class);
        final long save = this.bannerNoticeAdminService.save(bannerNotice);
        //移除key
        this.deleteKey(params.getLocale());
        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateBannerNotice")
    public ResponseResult updateBannerNotice(@RequestBody final BannerNoticeDTO params,
                                             final HttpServletRequest request) {
        final BannerNotice bannerNotice = this.bannerNoticeAdminService.getById(params.getId());
        params.setRndNum(bannerNotice.getRndNum());

        final ModelMapper mapper = new ModelMapper();
        final BannerNotice bannerNotice1 = mapper.map(params, BannerNotice.class);
        final Long save = this.bannerNoticeAdminService.update(bannerNotice1);
        //移除key
        this.deleteKey(params.getLocale());
        return ResultUtils.success(save);
    }

    @PostMapping(value = "/listBannerNotice")
    public ResponseResult listBannerNotice(@RequestBody final DataGridPager<BannerNoticeDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();
        final BannerNotice bannerNotice = mapper.map(pager.getQueryParameter(), BannerNotice.class);

        final List<BannerNotice> list = this.bannerNoticeAdminService.listByPage(pageInfo, bannerNotice);
        final List<BannerNoticeDTO> bannerNoticeDTOS = mapper.map(
                list, new TypeToken<List<BannerNoticeDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), bannerNoticeDTOS));
    }

    @PostMapping(value = "/updateStatus")
    public ResponseResult updateStatus(@RequestParam("id") final Long id,
                                       @RequestParam("status") final Integer status) {

        final BannerNotice bannerNotice = BannerNotice.builder()
                .status(status)
                .id(id)
                .build();
        this.bannerNoticeAdminService.update(bannerNotice);
        return ResultUtils.success("");
    }

    @GetMapping(value = "/getById")
    public ResponseResult getById(@RequestParam("id") final Long id, final HttpServletRequest request) {
        final BannerNotice bannerNotice = this.bannerNoticeAdminService.getById(id);
        final ModelMapper mapper = new ModelMapper();
        final BannerNoticeDTO bannerNoticeDTO = mapper.map(bannerNotice, BannerNoticeDTO.class);
        return ResultUtils.success(bannerNoticeDTO);
    }

    private void deleteKey(final String locale) {
        this.deleteAKey(locale);

        this.deleteAllKey();
    }

    private void deleteAllKey() {
        final List<I18nLanguage> i18nLanguages = this.i18nLanguageAdminService.getAll();

        if (CollectionUtils.isEmpty(i18nLanguages)) {
            return;
        }

        i18nLanguages.forEach(i18nLanguage -> {
            final String code = i18nLanguage.getCode();

            this.deleteAKey(code);
        });
    }

    private void deleteAKey(final String locale) {
        final String bannerCacheKey = StringUtils.join(CacheKey.EXTRA_BANNER_LIST, locale.toLowerCase());
        final String noticeCacheKey = StringUtils.join(CacheKey.EXTRA_NOTICE_LIST, locale.toLowerCase());
        final String splashCachekey = StringUtils.join(CacheKey.EXTRA_SPLASH_LIST, locale.toLowerCase());

        this.appCacheService.deleteKey(bannerCacheKey);
        this.appCacheService.deleteKey(noticeCacheKey);
        this.appCacheService.deleteKey(splashCachekey);
    }

}
