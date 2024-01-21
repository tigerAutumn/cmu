package cc.newex.dax.extra.service.wiki;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.common.enums.RtCurrencyTypeEnum;
import cc.newex.dax.extra.common.enums.RtSocialCategoryEnum;
import cc.newex.dax.extra.dto.wiki.RtCurrencyBaseInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyDynamicInfoDTO.DynamicInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencySocialInfoDTO;

import java.util.List;

/**
 * The interface Currency wiki service.
 *
 * @author better
 * @date create in 2018/11/27 6:38 PM
 */
public interface CurrencyWikiService {

    /**
     * 获取wiki中币种的基础信息
     *
     * @param currencyCode 币对Code
     * @param locale       the locale
     * @return currency base info
     */
    RtCurrencyBaseInfoDTO getCurrencyBaseInfo(String currencyCode, String locale);

    /**
     * 获取wiki中币种的项目信息
     *
     * @param currencyCode 币对Code
     * @param locale       the locale
     * @return currency project info
     */
    RtCurrencyProjectInfoDTO getCurrencyProjectInfo(String currencyCode, String locale);

    /**
     * Gets rt currency by condition.
     *
     * @param currencyCode the currency name
     * @param locale       the locale
     * @param type         the type
     * @return the rt currency by condition
     */
    RtCurrencyInfoDTO getRtCurrencyByCondition(String currencyCode, String locale, RtCurrencyTypeEnum type);

    /**
     * Gets currency dynamic page info.
     *
     * @param currencyCode the currency code
     * @param locale       the locale
     * @param pageInfo     the page info
     * @return the currency dynamic info
     */
    List<DynamicInfoDTO> getCurrencyDynamicPageInfo(String currencyCode, String locale, PageInfo pageInfo);

    /**
     * Gets currency social info.
     *
     * @param currencyCode the currency code
     * @param category     the category
     * @param platform     the platform
     * @return the currency social info
     */
    RtCurrencySocialInfoDTO getCurrencySocialInfo(String currencyCode, RtSocialCategoryEnum category, String platform);
}
