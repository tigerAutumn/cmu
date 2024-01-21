package cc.newex.dax.perpetual.domain.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DealOrder {
    /**
     * 订单id
     */
    private int id;
    /**
     * 订单价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 方向
     */
    private Byte side;
    /**
     * 时间
     */
    private Date createdDate;
}
