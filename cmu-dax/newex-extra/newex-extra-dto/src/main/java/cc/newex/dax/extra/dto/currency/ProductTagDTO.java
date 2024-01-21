package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 币对标签表
 *
 * @author newex-team
 * @date 2018-09-26 12:07:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductTagDTO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币对id
     */
    private Long productId;

    /**
     * 币对唯一代码
     */
    private String productCode;

    /**
     * 币种唯一代码（币种简称）
     */
    private String currencyCode;

    /**
     * 标签分类编码
     */
    private String tagCategoryCode;

    /**
     * 标签编码
     */
    private String tagInfoCode;

    /**
     * 类别，1-币种标签（继承过来的），2-币对标签
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}
