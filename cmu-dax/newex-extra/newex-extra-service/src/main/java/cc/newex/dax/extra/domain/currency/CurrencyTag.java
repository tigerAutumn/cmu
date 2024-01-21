package cc.newex.dax.extra.domain.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 币种标签表
 *
 * @author newex-team
 * @date 2018-09-26 12:07:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyTag {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种唯一代码（币种简称）
     */
    private String code;

    /**
     * 标签分类编码
     */
    private String tagCategoryCode;

    /**
     * 标签编码
     */
    private String tagInfoCode;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}