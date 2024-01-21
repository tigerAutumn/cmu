package cc.newex.dax.boss.web.model.activity.hundred;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author liutiejun
 * @date 2019-01-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimesExchangeRuleVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 兑换的抽奖次数
     */
    private Integer totalTimes;

    /**
     * 消耗的币种
     */
    private Integer currencyId;

    /**
     * 消耗的币种数量
     */
    private BigDecimal amount;

}
