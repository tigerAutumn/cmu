package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/12/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBalanceDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 是否测试币 0:线上币,1:测试币
     */
    private Integer env;

    /**
     * 总资产
     */
    private BigDecimal totalBalance;

    /**
     * 总已实现盈余
     */
    private BigDecimal realizedSurplus;
    /**
     * 总未实现盈余
     */
    private BigDecimal unrealizedSurplus;

    /**
     * 业务方ID
     */
    private Integer brokerId;
}
