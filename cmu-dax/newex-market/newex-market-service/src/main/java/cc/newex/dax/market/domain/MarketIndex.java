package cc.newex.dax.market.domain;

import lombok.*;

import java.util.Date;

/**
 * @author
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketIndex {
    /**
     *
     */
    private Long id;
    /**
     * 0:btc 1:ltc
     */
    private Integer symbol;
    /**
     *
     */
    private Double price;
    /**
     * 生成指数来源
     */
    private String detail;
    /**
     * 标识平台价格是否有效 “,”号隔开
     */
    private String invalid;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date modifyDate;
}