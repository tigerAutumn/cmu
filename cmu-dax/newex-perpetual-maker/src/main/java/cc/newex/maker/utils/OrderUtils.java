package cc.newex.maker.utils;

import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import cc.newex.openapi.cmx.perpetual.enums.OrderDetailSideEnum;
import cc.newex.openapi.cmx.perpetual.enums.OrderSystemTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderUtils {
    /**
     * 获取多单对象
     *
     * @param price
     * @param amount
     * @return
     */
    public static OrderRequest getLongOrder(final BigDecimal price, final BigDecimal amount, final int num) {
        final BigDecimal newPrice = BigDecimalUtil.getLongOrderPrice(price, num);
        final BigDecimal newAmount = BigDecimalUtil.getLongOrderAmount(amount, num);
        final OrderRequest request = getOrderRequest(newPrice, newAmount.multiply(new BigDecimal(10)), OrderDetailSideEnum.OPEN_LONG);
        return request;
    }

    /**
     * 获取空单对象
     *
     * @param price
     * @param amount
     * @return
     */
    public static OrderRequest getShortOrder(final BigDecimal price, final BigDecimal amount, final int num) {
        final BigDecimal newPrice = BigDecimalUtil.getShortOrderPrice(price, num);
        final BigDecimal newAmount = BigDecimalUtil.getShortOrderAmount(amount, num);
        final OrderRequest request = getOrderRequest(newPrice, newAmount.multiply(new BigDecimal(10)), OrderDetailSideEnum.OPEN_SHORT);
        return request;
    }

    /**
     * 获取下单对象
     *
     * @param price
     * @param amount
     * @return
     */
    public static OrderRequest getOrderRequest(final BigDecimal price, final BigDecimal amount, final OrderDetailSideEnum sideEnum) {
        final OrderRequest request = OrderRequest.builder()
                .type(OrderSystemTypeEnum.LIMIT.getSystemType())
                .side(sideEnum.getSide())
                .price(price)
                .amount(amount)
                .beMaker(0)
                .build();

        return request;
    }

    /**
     * 获取下单对象
     *
     * @param price
     * @param amount
     * @return
     */
    public static OrderRequest getOrderRequest(final double price, final double amount, final OrderDetailSideEnum sideEnum) {
        if (price > 0) {
            // 限价单
            final OrderRequest request = OrderRequest.builder()
                    .type(OrderSystemTypeEnum.LIMIT.getSystemType())
                    .side(sideEnum.getSide())
                    .price(new BigDecimal(String.valueOf(price)).setScale(4, RoundingMode.HALF_DOWN))
                    .amount(new BigDecimal(Double.valueOf(amount).intValue()))
                    .beMaker(0)
                    .build();

            return request;
        } else {
            // 市价单
            final OrderRequest request = OrderRequest.builder()
                    .type(OrderSystemTypeEnum.MARKET.getSystemType())
                    .side(sideEnum.getSide())
                    .amount(new BigDecimal(Double.valueOf(amount).intValue()))
                    .beMaker(0)
                    .build();

            return request;
        }

    }

}
