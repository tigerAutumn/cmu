package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 项目动态信息表
 *
 * @author mbg.generated
 * @date 2018-08-21 11:44:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyTrendInfoDTO {
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
     * 标题
     */
    @NotBlank
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 链接
     */
    private String link;

    /**
     * 日期
     */
    @NotNull
    private Date publishDate;

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