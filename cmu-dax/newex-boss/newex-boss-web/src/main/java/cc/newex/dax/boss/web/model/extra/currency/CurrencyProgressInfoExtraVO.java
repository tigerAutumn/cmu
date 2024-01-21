package cc.newex.dax.boss.web.model.extra.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyProgressInfoExtraVO {

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
     * 日期
     */
    @NotBlank
    private String publishDate;

    /**
     * 更新内容
     */
    @NotBlank
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
