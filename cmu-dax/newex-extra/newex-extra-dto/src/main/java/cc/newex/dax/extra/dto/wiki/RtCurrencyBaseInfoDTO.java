package cc.newex.dax.extra.dto.wiki;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币种基础信息DTO
 *
 * @author better
 * @date create in 2018/11/27 6:24 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RtCurrencyBaseInfoDTO implements RtCurrencyInfoDTO {

    /**
     * 流通数量
     */
    private Long circulatingSupply;

    /**
     * 发行时间
     */
    private String issueTime;

    /**
     * 发行价格
     */
    private String issuePrice;

    /**
     * 上交易所数量
     */
    private Short exchangeNum;

    /**
     * 总发行量
     */
    private String totalCirculation;

    /**
     * 币种简称
     */
    private String symbol;

    /**
     * 币种全称
     */
    private String currency;

    /**
     * 图标
     */
    private String logoUrl;

    /**
     * 币种概念
     */
    private String concept;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 币种官网url
     */
    private String officialWebsiteUrl;

    /**
     * 币种区块浏览器Url
     */
    private String browserUrl;

    /**
     * 源代码Url
     */
    private String sourceCodeUrl;

    /**
     * 币种白皮书地址
     */
    private String whitePaperUrl;

    /**
     * Telegram
     */
    private String telegramUrl;

    /**
     * Facebook
     */
    private String facebookUrl;

    /**
     * Twitter
     */
    private String twitterUrl;

    /**
     * weiBo
     */
    private String weiBoUrl;

    /**
     * 数字货币行情链接
     */
    private String coinMarketCapUrl;
}
