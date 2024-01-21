package cc.newex.dax.market.domain;

import cc.newex.dax.market.dto.model.PortfolioInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinConfig {
    /**
     *
     */
    private Long id;
    /**
     * 币种数字值
     */
    private Integer symbol;
    /**
     * 币种英文缩写
     */
    private String symbolName;
    /**
     * 币指数的marketFrom
     */
    private Integer indexMarketFrom;
    /**
     * 币种符号
     */
    private String symbolMark;
    /**
     * 币种对应的marketFrom列表
     */
    private String marketFrom;
    /**
     * 币种对应的marketFrom列表
     */
    private int[] marketFromArray;

    /**
     * 组合指数配置
     */
    private PortfolioInfo portfolioInfo;

    /**
     * 交易小数位
     */
    private Integer pricePoint;
    /**
     * 无效运算开关 0默认关、1开
     */
    private Byte invalidSwitch;
    /**
     * 类型 0指数、1组合指数
     */
    private Integer type;
    /**
     * 状态 0正常、1预发、2下线
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date modifyDate;
}