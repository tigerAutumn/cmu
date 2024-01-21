// package cc.newex.maker.perpetual.core;
//
// import cc.newex.maker.perpetual.domain.Trade;
// import cc.newex.maker.perpetual.enums.MakerEnum;
// import cc.newex.maker.utils.OrderUtils;
// import cc.newex.openapi.cmx.perpetual.domain.OrderBook;
// import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
// import cc.newex.openapi.cmx.perpetual.enums.OrderDetailSideEnum;
// import cc.newex.openapi.cmx.perpetual.service.OrderService;
// import com.alibaba.fastjson.JSON;
// import com.google.common.collect.Lists;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.collections4.CollectionUtils;
//
// import java.io.IOException;
// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * 交易做量
//  *
//  * @author newex-team
//  * @date 2018-12-17
//  */
// @Slf4j
// public class DoMockTrade {
//     /**
//      * 是否执行完成，第一次默认完成
//      */
//     private volatile boolean completed = true;
//     private static double lastPrice;
//     private static double lastAmount;
//
//     private List<Trade> tradeList = Lists.newArrayList();
//
//     void setTradeList(final List<Trade> tradeList) {
//         this.tradeList = tradeList;
//     }
//
//     void execute(final MakerEnum makerEnum, final OrderService orderService) {
//         if (!this.completed) {
//             return;
//         }
//         synchronized (this) {
//             this.completed = false;
//         }
//
//         try {
//             if (makerEnum.getTradeConfEnum() == null) {
//                 DoMockTrade.log.info("DoMockTrade getTradeConfEnum is null pair:{}", makerEnum.name().toLowerCase());
//                 synchronized (this) {
//                     this.completed = true;
//                 }
//                 return;
//             }
//             if (CollectionUtils.isEmpty(this.tradeList)) {
//                 DoMockTrade.log.info("DoMockTrade {}  tradeList is null", makerEnum.name());
//                 synchronized (this) {
//                     this.completed = true;
//                 }
//                 return;
//             }
//             final Trade trade = this.tradeList.stream().findFirst().get();
//             lastPrice = new Double(trade.getPrice());
//             lastAmount = new Double(trade.getSize()) * Math.round(10);
//
//             final double maxAmount = makerEnum.getTradeConfEnum().getMax();
//             double orderAmount = lastAmount;
//             orderAmount = orderAmount < makerEnum.getTradeConfEnum().getMini() ? makerEnum.getTradeConfEnum().getMini() : orderAmount;
//             orderAmount = orderAmount > maxAmount ? maxAmount : orderAmount;
//
//             final double price = lastPrice;
//             if (CollectionUtils.isNotEmpty(DoDepth.getOrderBookList())) {
//                 orderService.deleteOrders(makerEnum.name().toLowerCase());
//                 log.info("DoMockTrade cancel orders");
//             }
//             //下单
//             mergeTrade(orderAmount, price, makerEnum, orderService);
//         } catch (final Exception e) {
//             log.error("DoMockTrade is  error ");
//         }
//         synchronized (this) {
//             this.completed = true;
//         }
//     }
//
//
//     private static void mergeTrade(final double amount, final double price, final MakerEnum makerEnum, final OrderService orderService) {
//         doTrade(price, amount, OrderDetailSideEnum.OPEN_LONG, makerEnum, orderService);
//         doTrade(price, amount, OrderDetailSideEnum.OPEN_SHORT, makerEnum, orderService);
//     }
//
//
//     private static void doTrade(final double price, final double amount, final OrderDetailSideEnum side, final MakerEnum makerEnum, final OrderService orderService) {
//         final OrderRequest request = OrderUtils.getOrderRequest(new BigDecimal(price).setScale(makerEnum.getTradeConfEnum().getMinTradeDigit(), RoundingMode.DOWN),
//                 new BigDecimal(amount).setScale(makerEnum.getTradeConfEnum().getMinTradeDigit(), RoundingMode.DOWN), side);
//         try {
//             final Map result = orderService.postOrder(makerEnum.name().toLowerCase(), request);
//             log.info("DoMockTrade 合约{}下{}单: order result : {}", makerEnum.name().toLowerCase(), side, JSON.toJSONString(result));
//
//             final BigDecimal totalOrderSize = orderService.list(makerEnum.name().toLowerCase()).stream()
//                     .map(OrderBook::getOrderSize).reduce(BigDecimal.ZERO, BigDecimal::add);
//             if (totalOrderSize.compareTo(new BigDecimal(5000)) > 0) {
//                 orderService.deleteOrders(makerEnum.name().toLowerCase());
//             }
//         } catch (final IOException e) {
//             log.error("DoMockTrade order is error ");
//         }
//     }
// }
