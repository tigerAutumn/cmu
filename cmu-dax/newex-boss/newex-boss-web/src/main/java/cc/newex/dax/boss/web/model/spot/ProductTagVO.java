package cc.newex.dax.boss.web.model.spot;

import lombok.*;

/**
 * 币对标签VO
 *
 * @author better
 * @date create in 2018/9/27 上午11:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductTagVO {

    /**
     * 币对code
     */
    private String code;

    /**
     * 币对id
     */
    private Long id;

    /**
     * 币种code
     */
    private String currencyCode;

    /**
     * 标签
     */
    private String tags;
}
