package cc.newex.maker.perpetual.core;

import cc.newex.maker.perpetual.enums.RobotTradeEnum;
import cc.newex.maker.utils.OrderUtils;
import cc.newex.maker.utils.SimpleThreadUtils;
import cc.newex.openapi.cmx.client.CmxClient;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import cc.newex.openapi.cmx.perpetual.enums.OrderDetailSideEnum;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 机器人下单
 * 通过自己平台的深度，按照一定规则
 * 给币做量
 *
 * @author cmx-sdk-team
 * @date 2018/04/28
 */
@Slf4j
public class RobotTrade {

    private final RobotTradeEnum robotTradeEnum;

    private final OrderService orderService;

    public RobotTrade(final RobotTradeEnum robotTradeEnum, final CmxClient cmxClient) {
        this.robotTradeEnum = robotTradeEnum;
        this.orderService = cmxClient.perpetual().ccex().order();
    }

    public void trade() {
        log.info("begin do trade, code: {}", this.robotTradeEnum);

        while (true) {
            try {
                // 撤销
                this.orderService.deleteOrders(this.robotTradeEnum.name().toLowerCase());

                final DepthOrderBook orderBook = this.orderService.depth(this.robotTradeEnum.name().toLowerCase(), 10);

                if (this.isEmpty(orderBook)) {
                    log.info("take order book failed, robotTradeEnum: {}", this.robotTradeEnum);
                    SimpleThreadUtils.sleep(1500);
                    continue;
                }

                // 卖1
                final double ask = Double.parseDouble(orderBook.getAsks().get(0)[0]);
                // 买1
                final double bid = Double.parseDouble(orderBook.getBids().get(0)[0]);

                // 价格
                final double price = this.getPrice(bid, ask);

                if (price <= 0) {
                    SimpleThreadUtils.sleep(1500);
                    continue;
                }

                // 数量
                final double amount = this.getAmount();

                this.doTrade(price, amount);

                log.info("do trade coinmex order success: {}, price: {}, amount: {}", this.robotTradeEnum, price, amount);

                final int sleepTime = RandomUtils.nextInt(this.robotTradeEnum.getMinSleepTime(), this.robotTradeEnum.getMaxSleepTime());

                SimpleThreadUtils.sleep(sleepTime * 1000);
            } catch (final Exception e) {
                log.error("do trade coinmex order error, code : " + this.robotTradeEnum, e);

                SimpleThreadUtils.sleep(1500);
            }
        }
    }

    private boolean isEmpty(final DepthOrderBook orderBook) {
        if (orderBook == null) {
            return true;
        }

        return CollectionUtils.isEmpty(orderBook.getAsks()) || CollectionUtils.isEmpty(orderBook.getBids());
    }

    private double getAmount() {
        final int flag = RandomUtils.nextInt(0, 10);

        if (flag % 2 == 0) {
            return RandomUtils.nextInt(this.robotTradeEnum.getMin(), this.robotTradeEnum.getMax());
        } else {
            return RandomUtils.nextInt(this.robotTradeEnum.getRandomMin(), this.robotTradeEnum.getRandomMax());
        }
    }

    /**
     * @param bid 买一价格
     * @param ask 卖一价格
     * @return
     */
    private double getPrice(final double bid, final double ask) {
        if (ask <= bid) {
            log.info("ask <= bid, code: {}, ask: {}, bid: {}", this.robotTradeEnum, ask, bid);

            return -1;
        }

        final int flag = RandomUtils.nextInt(0, 10);

        final double middle = (ask - bid) / 2;

        double random = 0.0;

        if (flag % 2 == 0) {
            random = middle + middle / this.robotTradeEnum.getPriceRate() * Math.random();
        } else {
            random = middle - middle / this.robotTradeEnum.getPriceRate() * Math.random();
        }

        final int priceDigit = this.robotTradeEnum.getPriceDigit();

        final BigDecimal minPrice = createMinPrice(priceDigit);

        // 价格
        BigDecimal price = new BigDecimal(String.valueOf(ask - random));
        price = price.setScale(priceDigit, BigDecimal.ROUND_DOWN);

        final BigDecimal price1 = price.add(minPrice);
        final BigDecimal price2 = price.subtract(minPrice);

        log.info("do trade, code: {}, ask: {}, bid: {}, price: {}, price1: {}, price2: {}", this.robotTradeEnum, ask, bid, price, price1, price2);

        final BigDecimal askBd = new BigDecimal(String.valueOf(ask));
        final BigDecimal bidBd = new BigDecimal(String.valueOf(bid));

        if (price.compareTo(bidBd) > 0 && price.compareTo(askBd) < 0) {
            return price.doubleValue();
        }

        if (price1.compareTo(bidBd) > 0 && price1.compareTo(askBd) < 0) {
            return price1.doubleValue();
        }

        if (price2.compareTo(bidBd) > 0 && price2.compareTo(askBd) < 0) {
            return price2.doubleValue();
        }

        return -1;
    }

    /**
     * 价格的调整幅度
     *
     * @param maxDigit
     * @return
     */
    private static BigDecimal createMinPrice(final Integer maxDigit) {
        if (maxDigit <= 0) {
            return BigDecimal.ZERO;
        }

        final BigDecimal minAmount = BigDecimal.ONE;

        return minAmount.divide(new BigDecimal(Math.pow(10, maxDigit)), maxDigit, BigDecimal.ROUND_DOWN);
    }

    private void doTrade(final double price, final double amount) throws IOException {
        final int flag = RandomUtils.nextInt(0, 10);

        try {
            if (flag % 3 == 0) {
                this.doTrade(price, amount, OrderDetailSideEnum.OPEN_LONG);
                this.doTrade(price, amount, OrderDetailSideEnum.OPEN_SHORT);
            } else {
                this.doTrade(price, amount, OrderDetailSideEnum.OPEN_SHORT);
                this.doTrade(price, amount, OrderDetailSideEnum.OPEN_LONG);
            }
        } catch (final Exception e) {
            log.error("do trade coinmex order error, code: {}, msg: {}", this.robotTradeEnum, e.getMessage());

            //撤销
            this.orderService.deleteOrders(this.robotTradeEnum.name().toLowerCase());
        }

    }

    private void doTrade(final double price, final double amount, final OrderDetailSideEnum side) throws Exception {
        final OrderRequest orderRequest = OrderUtils.getOrderRequest(price, amount, side);

        final Map result = this.orderService.postOrder(this.robotTradeEnum.name().toLowerCase(), orderRequest);

        log.info("do trade coinmex order success, code: {}, result: {}", this.robotTradeEnum, JSON.toJSONString(result));
    }

}
