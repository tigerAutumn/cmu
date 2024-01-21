package cc.newex.dax.perpetual.openapi.support.common;

import cc.newex.commons.dictionary.enums.OrderTypeEnum;
import cc.newex.commons.dictionary.enums.SideTypeEnum;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.dto.PlaceOrderParam;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author newex-term
 * @date 2018-12-13
 */
public class OrderUtils {

    public static Order initOrder(final Integer brokerId, final Long userId, final CurrencyPair currencyPair, final PlaceOrderParam param) {
        // 获取订单类型 limit market
        final Byte orderType = OrderTypeEnum.getType(param.getType());

        // 获取买卖类型 buy sell
        final Byte sideType = SideTypeEnum.getType(param.getSide());

        final boolean market = OrderTypeEnum.MARKET.getType().equals(orderType);

        final String strPrice = !market ? param.getPrice() : param.getFunds();

        final BigDecimal price = StringUtils.isEmpty(strPrice) ? BigDecimal.ZERO : new BigDecimal(strPrice);

        // 市价单 买 size = 0， 否则必填
        final BigDecimal size = market && SideTypeEnum.BUY.getType().equals(sideType) ? BigDecimal.ZERO : new BigDecimal(
                param.getSize());


        final Date timestamp = new Date();

        return Order.builder()
                .userId(userId)
                .status(OrderStatusEnum.NOT_DEAL.getCode())
                .build();
    }

    private static BigDecimal setPlace(final BigDecimal value, final Integer place) {
        if (value == null) {
            return null;
        } else {
            return value.setScale(place, RoundingMode.FLOOR);
        }
    }

}
