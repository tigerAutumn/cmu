package cc.newex.dax.extra.domain.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 项目进展信息表
 *
 * @author mbg.generated
 * @date 2018-08-21 11:45:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyProgressInfo {
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
     * 日期
     */
    private Date publishDate;

    /**
     * 更新内容
     */
    private String content;

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
}