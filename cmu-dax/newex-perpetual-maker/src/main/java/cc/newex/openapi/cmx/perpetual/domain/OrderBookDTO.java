package cc.newex.openapi.cmx.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderBookDTO {
    /**
     * 仓位方向，long多，short空
     */
    private String side;
    /**
     * 委托数量
     */
    private BigDecimal amount;
    /**
     * 已成交数量
     */
    private BigDecimal dealAmount;
    /**
     * 平均成交价格
     */
    private BigDecimal avgPrice;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     * 委托价值
     */
    private BigDecimal orderSize;
    /**
     * 0 等待成交 1 部分成交 2 已经成交 -1 已经撤销
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdDate;

}
