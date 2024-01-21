package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     *
     */
    private Long id;
    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;
    /**
     * 基础货币名,如btc、fbtc
     */
    private String base;
    /**
     * 计价货币名，usd,cny,usdt
     */
    private String quote;
    /**
     * 方向 0:正向,1:反向
     */
    private Integer contractDirection;
    /**
     * 仓位方向，long多，short空
     */
    private String side;

    /**
     * 仓位方向 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
     */
    private String detailSide;
    /**
     * 委托数量
     */
    private String amount;
    /**
     * 已成交数量
     */
    private String dealAmount;
    /**
     * 平均成交价格
     */
    private String avgPrice;
    /**
     * 委托价格
     */
    private String price;
    /**
     * 委托价值
     */
    private String orderSize;
    /**
     * 0 等待成交 1 部分成交 2 已经成交 -1 已经撤销
     */
    private Integer status;
    /**
     * 10:限价 11:市价 13:强平单 14:爆仓单 15：穿仓 16：强减
     *
     */
    private Integer systemType;
    /**
     * 触发方向，greater大于，less小于
     */
    private String direction;

    /**
     * 该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损
     */
    private String profit;

    /**
     * 该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费
     */
    private String fee;

    /**
     * 该笔订单取消的理由，0是默认值，没有理由
     */
    private Integer reason;

    /**
     * 计划委托类型
     * INDEX("index"), //指数价格market
     * MARK("mark"), //标记价格mark
     * LAST("last"),//最新价格last
     */
    private String triggerBy;

    /**
     * 触发价格
     */
    private String triggerPrice;
    /**
     * 创建时间
     */
    private Date createdDate;

}
