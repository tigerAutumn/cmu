package cc.newex.dax.boss.web.model.extra.currency;

import lombok.*;

/**
 * 币种标签VO
 *
 * @author better
 * @date create in 2018/9/27 上午11:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyTagExtraVO {

    /**
     * 币对code
     */
    private String code;

    /**
     * 币对id
     */
    private Long id;

    /**
     * 标签
     */
    private String tags;
}
