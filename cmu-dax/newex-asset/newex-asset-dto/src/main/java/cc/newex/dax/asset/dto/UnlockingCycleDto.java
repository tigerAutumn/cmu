package cc.newex.dax.asset.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lilaizhen
 */
@Data
public class UnlockingCycleDto {

    /**
     * 解锁数量
     */
    private BigDecimal amount;
    /**
     * 解锁周期 -- 单位分钟
     */
    private Integer cycle;
}
