package cc.newex.dax.boss.web.model.asset;

import lombok.*;

import java.math.BigDecimal;

/**
 * 按照周期进行解锁
 *
 * @author liutiejun
 * @date 2018-07-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockCycleVO {

    /**
     * 解锁数量
     */
    private BigDecimal amount;

    /**
     * 解锁周期，单位分钟
     */
    private Integer cycle;
    
}
