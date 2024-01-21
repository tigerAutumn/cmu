package cc.newex.dax.extra.rest.controller.outer.v1.common.wiki;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.RtCurrencyTypeEnum;
import cc.newex.dax.extra.common.enums.RtSocialCategoryEnum;
import cc.newex.dax.extra.common.util.SimpleLocaleUtils;
import cc.newex.dax.extra.dto.wiki.RtCurrencyBaseInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyDynamicInfoDTO.DynamicInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyInfoDTO;
import cc.newex.dax.extra.rest.model.page.front.FrontPagerResult;
import cc.newex.dax.extra.service.wiki.CurrencyWikiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

/**
 * 币对wiki的控制器
 *
 * @author better
 * @date create in 2018/11/27 6:21 PM
 */
@RestController
@RequestMapping(value = "/v1/extra/public/wiki/currency")
@Slf4j
public class CurrencyWikiController {

    private final CurrencyWikiService currencyWikiService;
    private final FileUploadService fileUploadService;

    /**
     * Instantiates a new Currency wiki controller.
     *
     * @param currencyWikiService the currency wiki service
     * @param fileUploadService
     */
    @Autowired
    public CurrencyWikiController(final CurrencyWikiService currencyWikiService, final FileUploadService fileUploadService) {
        this.currencyWikiService = currencyWikiService;
        this.fileUploadService = fileUploadService;
    }

    /**
     * Gets currency base info.
     *
     * @param currencyCode the currency name
     * @param type         the type
     * @param request      the request
     * @return the currency base info
     */
    @OpLog(name = "获取币种WIKI中的币种基础信息")
    @GetMapping(value = "/{currencyCode}/info/{type}")
    public ResponseResult<? extends RtCurrencyInfoDTO> getCurrencyBaseInfo(@PathVariable(value = "currencyCode") final String currencyCode,
                                                                           @PathVariable(value = "type") final String type, final HttpServletRequest request) {

        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        final RtCurrencyInfoDTO rtCurrencyInfo = this.currencyWikiService.getRtCurrencyByCondition(currencyCode, locale, RtCurrencyTypeEnum.valueOf(type.toUpperCase()));

        if (rtCurrencyInfo instanceof RtCurrencyBaseInfoDTO) {
            final RtCurrencyBaseInfoDTO baseInfo = (RtCurrencyBaseInfoDTO) rtCurrencyInfo;
            baseInfo.setLogoUrl(this.fileUploadService.getUrl(baseInfo.getLogoUrl()));

            return ResultUtils.success(baseInfo);
        } else {
            return ResultUtils.success(rtCurrencyInfo);
        }
    }

    /**
     * Gets currency social emotion info.
     *
     * @param currencyCode the currency code
     * @param category     社交信息分类, 分类: EMOTION(情感), COMMUNITY(社交), ALL(全部)
     * @param platform     平台分类, 平台: facebook, telegram,  twitter
     * @return the currency social info
     */
    @OpLog(name = "获取币种WIKI中的币种社交信息")
    @GetMapping(value = "/{currencyCode}/info/social/{category}/{platform}")
    public ResponseResult<? extends RtCurrencyInfoDTO> getCurrencySocialInfo(@PathVariable(value = "currencyCode") final String currencyCode,
                                                                             @PathVariable(value = "category") final String category,
                                                                             @PathVariable(value = "platform") final String platform) {
        final RtCurrencyInfoDTO rtCurrencyInfo = this.currencyWikiService.getCurrencySocialInfo(currencyCode, RtSocialCategoryEnum.valueOf(category.toUpperCase()), platform);
        return ResultUtils.success(rtCurrencyInfo);
    }

    /**
     * Gets currency dynamic page info.
     *
     * @param currencyCode the currency code
     * @param page         the page
     * @param size         the size
     * @param request      the request
     * @return the currency social info
     */
    @OpLog(name = "获取币种WIKI中的币种动态信息")
    @GetMapping(value = "/{currencyCode}/info/dynamic")
    public ResponseResult<FrontPagerResult<DynamicInfoDTO>> getCurrencyDynamicPageInfo(@PathVariable(value = "currencyCode") final String currencyCode,
                                                                                       @RequestParam("page") @NotNull @Min(1) final Integer page,
                                                                                       @RequestParam("size") @NotNull @Min(1) final Integer size,
                                                                                       final HttpServletRequest request) {

        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());
        final PageInfo pageInfo = new PageInfo((page - 1) * size, size, "publish_date", PageInfo.SORT_TYPE_DES);
        final List<DynamicInfoDTO> queryData = this.currencyWikiService.getCurrencyDynamicPageInfo(currencyCode, locale, pageInfo);
        return ResultUtils.success(new FrontPagerResult<>(queryData, page, size, pageInfo.getTotals()));
    }
}
