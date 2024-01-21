package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前杠杆和危险限额设置
 *
 * @author newex-team
 * @date 2018-11-20 21:08:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionPubConfig {
    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;
    /**
     * 0全仓，1逐仓
     */
    private Integer type;
    /**
     * 杠杆
     */
    private String lever;
    /**
     * 风险限额
     */
    private String gear;
    /**
     * 单位是base,最低档位
     */
    private String minGear;

    /**
     * 单位是base,每档的差值
     */
    private String diffGear;

    /**
     * 单位是base,最高档位
     */
    private String maxGear;

    /**
     * 维持保证金费率,每升一档都加一个维持保证金费率
     */
    private String maintainRate;
    /**
     * 标记价格
     */
    private String markPrice;
    /**
     * 指数价格
     */
    private String indexPrice;

    /**
     * 一张合约对应的quote面值,默认1
     */
    private String unitAmount;
    /**
     * 资金费率
     */
    private String fundRate;
    /**
     * 手续费率
     */
    private String feeRate;
}