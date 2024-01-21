package cc.newex.dax.market.domain;

import lombok.*;

import java.util.Date;

/**
 * {"market_name":"huobi","market_from":"9","price":"1.0"}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketIndexDetail {
    /**
     * 标识
     */
    private Integer marketFrom;
    /**
     * 名称
     */
    private String marketName;
    /**
     * 价格
     */
    private Double price;

}