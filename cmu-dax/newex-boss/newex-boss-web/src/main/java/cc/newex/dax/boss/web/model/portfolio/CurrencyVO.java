package cc.newex.dax.boss.web.model.portfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author liutiejun
 * @date 2018-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyVO {

    /**
     * 币种名称
     */
    private String name;

    /**
     * 数量
     */
    private BigDecimal amount;

}
