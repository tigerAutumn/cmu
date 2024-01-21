package cc.newex.dax.perpetual.openapi.support.common;

import cc.newex.commons.dictionary.enums.OrderTypeEnum;
import cc.newex.commons.dictionary.enums.SideTypeEnum;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.dto.PlaceOrderParam;
import cc.newex.dax.perpetual.openapi.support.aop.PerpetualOpenApiException;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liutiejun
 * @date 2018-11-13
 */
public class OrderValidationUtils {

    public static void checkOrderParam(final PlaceOrderParam param) {
        // 币种对不允许为空
        if (StringUtils.isBlank(param.getCode())) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Parm_null);
        }

        // 获取订单的类型 limit/market
        final Byte orderType = OrderTypeEnum.getType(param.getType());

        // 获取订单的买卖类型 buy/sell
        final Byte sideType = SideTypeEnum.getType(param.getSide());

        // 订单的 订单类型（限价单/市价单），买卖类型必填
        if (orderType == null || sideType == null) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Parm_null);
        }

        if (OrderTypeEnum.LIMITED.getType().equals(orderType)) {
            // 限价单的价格、数量不允许为空
            if (StringUtils.isBlank(param.getSize()) || BigDecimalUtil.isNotBigDecimal(param.getSize())) {
                throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
            }

            if (StringUtils.isBlank(param.getPrice()) || BigDecimalUtil.isNotBigDecimal(param.getPrice())) {
                throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
            }

        } else if (OrderTypeEnum.MARKET.getType().equals(orderType)) {
            // 市价单的买价格信息不允许为空，卖的数量不允许为空
            if (SideTypeEnum.BUY.getType().equals(sideType)) {
                if (StringUtils.isEmpty(param.getFunds()) || BigDecimalUtil.isNotBigDecimal(param.getFunds())) {
                    throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
                }

            }

            if (SideTypeEnum.SELL.getType().equals(sideType)) {
                if (StringUtils.isEmpty(param.getSize()) || BigDecimalUtil.isNotBigDecimal(param.getSize())) {
                    throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
                }

            }
        } else {
            // 其他的订单类型，目前不存在
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
        }

        // systemType
//        if (!Constants.SPOT_SYSTEM_TYPE.equals(param.getSystemType())) {
//            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
//        }

    }

}
