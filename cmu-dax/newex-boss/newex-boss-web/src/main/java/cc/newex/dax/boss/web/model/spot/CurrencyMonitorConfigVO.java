package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2019-01-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyMonitorConfigVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 币种监控报警阈值
     */
    private BigDecimal threshold;

    /**
     * 状态，0-无效，1-有效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}
