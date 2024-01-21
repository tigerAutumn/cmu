package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下单交易的实体类
 *
 * @author liutiejun
 * @date 2018-11-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceOrderParam {
    /**
     * 币对如 etc_eth
     */
    private String code;
    /**
     * 买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 限价单 limit 市价单 market
     */
    private String type;
    /**
     * 交易数量
     */
    private String size;
    /**
     * 限价单使用 价格
     */
    private String price;
    /**
     * 市价单使用 价格
     */
    private String funds;

    /**
     * 来源（web app ios android,openapi）
     */
    private Byte source = 4;

    /**
     * 1币币交易 2杠杆交易
     */
    private Byte systemType = 1;

}
