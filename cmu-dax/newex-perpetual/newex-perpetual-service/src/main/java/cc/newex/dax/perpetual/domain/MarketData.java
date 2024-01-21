package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * K线
 *
 * @author newex-team
 * @date 2018-12-07 16:31:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketData {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价
     */
    private BigDecimal close;

    /**
     * 成交张数
     */
    private BigDecimal amount;

    /**
     * 成交价值
     */
    private BigDecimal size;

    /**
     * 0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}