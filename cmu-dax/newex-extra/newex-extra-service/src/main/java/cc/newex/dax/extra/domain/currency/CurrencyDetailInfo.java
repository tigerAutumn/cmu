package cc.newex.dax.extra.domain.currency;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全球数字货币详细信息表
 *
 * @author mbg.generated
 * @date 2019-01-17 16:07:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyDetailInfo {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种唯一代码（币种简称）
     */
    private String code;

    /**
     * 语言(zh-cn/en-us等)
     */
    private String locale;

    /**
     * 币种概念
     */
    private String concept;

    /**
     * 币种白皮书地址
     */
    private String whitePaper;

    /**
     * 币种详细介绍
     */
    private String introduction;

    /**
     * Telegram
     */
    private String telegram;

    /**
     * Facebook
     */
    private String facebook;

    /**
     * Twitter
     */
    private String twitter;

    /**
     * weibo
     */
    private String weibo;

    /**
     * 状态，1-待审核，2-已发布，3-已下架
     */
    private Integer status;

    /**
     * 发布用户id
     */
    private Integer publisherId;

    /**
     * 排序，可用于置顶，越小排序越靠前
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 数字货币行情链接
     */
    private String coinMarketCapUrl;
}