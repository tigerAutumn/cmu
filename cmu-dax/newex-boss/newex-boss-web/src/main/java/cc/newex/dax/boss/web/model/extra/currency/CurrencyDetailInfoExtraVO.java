package cc.newex.dax.boss.web.model.extra.currency;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 全球数字货币详细信息表
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyDetailInfoExtraVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种唯一代码（币种简称）
     */
    @NotBlank
    private String code;

    /**
     * 语言(zh-cn/en-us等)
     */
    @NotBlank
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
    @NotBlank
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
     * 数字货币行情链接
     */
    private String coinMarketCapUrl;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}