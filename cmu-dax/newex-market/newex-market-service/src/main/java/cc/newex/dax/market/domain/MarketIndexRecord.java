package cc.newex.dax.market.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketIndexRecord {
    /**
     * 主键
     */
    private Long id;
    /**
     * 站点
     */
    private Integer marketFrom;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 中间价格
     */
    private BigDecimal middlePrice;
    /**
     * 默认0有效、1无效
     */
    private Integer invalid;
    /**
     * 记录时间
     */
    private Date createdDate;
}