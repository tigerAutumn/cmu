package cc.newex.dax.boss.web.model.extra.cms;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 币种对应的标签
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyTagExtraVO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 币种id
     */
    @NotNull
    private Integer currencyBaseInfoId;

    /**
     * 标签分类id
     */
    @NotNull
    private Integer tagCategoryId;

    /**
     * 标签id
     */
    @NotNull
    private Integer tagInfoId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}
