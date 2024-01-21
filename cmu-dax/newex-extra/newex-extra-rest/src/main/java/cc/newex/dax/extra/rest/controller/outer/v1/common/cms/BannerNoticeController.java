package cc.newex.dax.extra.rest.controller.outer.v1.common.cms;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.BannerNoticeTypeEnum;
import cc.newex.dax.extra.domain.cms.BannerNotice;
import cc.newex.dax.extra.rest.common.util.BrokerUtils;
import cc.newex.dax.extra.rest.common.util.CacheKey;
import cc.newex.dax.extra.rest.model.PostVo;
import cc.newex.dax.extra.service.cache.AppCacheService;
import cc.newex.dax.extra.service.cms.BannerNoticeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * 首页banner广告表 控制器类
 *
 * @author newex-team
 * @date 2018-04-09
 */
@RestController
@RequestMapping(value = "/v1/extra/cms/banner-notices")
public class BannerNoticeController {

    @Resource
    private BannerNoticeService bannerNoticeService;

    @Resource
    private FileUploadService fileUploadService;

    @Autowired
    private AppCacheService appCacheService;

    @GetMapping("/home/banners")
    public ResponseResult<List<PostVo>> getHomePageBanners(final HttpServletRequest request) {
        final String locale = LocaleUtils.getLocale(request);
        final Integer brokerId = BrokerUtils.getBrokerId(request);

        final String cacheKey = StringUtils.join(CacheKey.EXTRA_BANNER_LIST, locale.toLowerCase());

        final List<PostVo> bannerNotices = this.getBannerNoticesFromCache(cacheKey, BannerNoticeTypeEnum.BANNER, locale.toLowerCase(), brokerId);

        return ResultUtils.success(bannerNotices);
    }

    @GetMapping("/home/news")
    public ResponseResult<List<PostVo>> getHomePageNews(final HttpServletRequest request) {
        final String locale = LocaleUtils.getLocale(request);
        final Integer brokerId = BrokerUtils.getBrokerId(request);
        final String cacheKey = StringUtils.join(CacheKey.EXTRA_NOTICE_LIST, locale.toLowerCase());

        final List<PostVo> bannerNotices = this.getBannerNoticesFromCache(cacheKey, BannerNoticeTypeEnum.NOTICE, locale.toLowerCase(), brokerId);

        return ResultUtils.success(bannerNotices);
    }

    @GetMapping("/home/splashes")
    public ResponseResult<List<PostVo>> getHomePageSplashes(final HttpServletRequest request) {
        final String locale = LocaleUtils.getLocale(request);
        final Integer brokerId = BrokerUtils.getBrokerId(request);
        final String cacheKey = StringUtils.join(CacheKey.EXTRA_SPLASH_LIST, locale.toLowerCase());

        final List<PostVo> bannerNotices = this.getBannerNoticesFromCache(cacheKey, BannerNoticeTypeEnum.SPLASH, locale.toLowerCase(), brokerId);

        return ResultUtils.success(bannerNotices);
    }

    private List<PostVo> getBannerNoticesFromCache(final String cacheKey, final BannerNoticeTypeEnum type, String locale, final Integer brokerId) {
        final List<PostVo> posts = this.appCacheService.getList(cacheKey, PostVo.class);
        if (CollectionUtils.isNotEmpty(posts)) {
            return posts;
        }

        List<BannerNotice> bannerNoticeList = this.bannerNoticeService.getByType(locale, type.getCode(), brokerId);

        if (CollectionUtils.isEmpty(bannerNoticeList)) {
            locale = Locale.US.toLanguageTag().toLowerCase();

            // 如果没有数据，默认返回en-us对应的数据
            bannerNoticeList = this.bannerNoticeService.getByType(locale, type.getCode(),brokerId);
        }

        final List<PostVo> postVoList = this.toPostVoList(bannerNoticeList, type);
        this.appCacheService.setList(cacheKey, postVoList);

        return postVoList;
    }

    private List<PostVo> toPostVoList(final List<BannerNotice> bannerNotices, final BannerNoticeTypeEnum type) {
        if (CollectionUtils.isEmpty(bannerNotices)) {
            return Lists.newArrayListWithCapacity(0);
        }

        final List<PostVo> postVos = Lists.newArrayListWithCapacity(bannerNotices.size());

        bannerNotices.forEach(item -> postVos.add(this.toPostVo(item)));

        return postVos;
    }

    private PostVo toPostVo(final BannerNotice bannerNotice) {
        final PostVo.PostVoBuilder builder = PostVo.builder();

        builder.id(bannerNotice.getId());
        builder.title(bannerNotice.getTitle());
        builder.redirectUrl(bannerNotice.getLink());

        String imageUrl = bannerNotice.getImageUrl();
        if (StringUtils.isNotBlank(imageUrl) && !StringUtils.startsWithIgnoreCase(imageUrl, "http://")) {
            imageUrl = this.fileUploadService.getUrl(imageUrl);
        }
        builder.imageUrl(imageUrl);

        return builder.build();
    }

}