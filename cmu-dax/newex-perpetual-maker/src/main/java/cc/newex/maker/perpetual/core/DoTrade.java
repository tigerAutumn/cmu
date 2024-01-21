package cc.newex.maker.perpetual.core;

import cc.newex.maker.perpetual.enums.MakerEnum;
import cc.newex.maker.perpetual.enums.PlatformEnum;
import cc.newex.maker.utils.OrderUtils;
import cc.newex.maker.utils.SimpleThreadUtils;
import cc.newex.openapi.bitmex.domain.BitMexTrade;
import cc.newex.openapi.bitmex.v1.BitMexV1RestApi;
import cc.newex.openapi.cmx.client.CmxClient;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import cc.newex.openapi.cmx.perpetual.enums.OrderDetailSideEnum;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交易做量
 *
 * @author cmx-sdk-team
 * @date 2018/04/28
 */
@Slf4j
public class DoTrade {

    private volatile long since = 0L;

    /**
     * 下单数量限制
     */
    private static final int MAX_ORDER_SIZE = 5;

    private MakerEnum makerEnum;

    private OrderService robotOrderService;

    private BitMexV1RestApi bitMexV1RestApi;

    public DoTrade(final MakerEnum makerEnum, final CmxClient cmxClient, final BitMexV1RestApi bitMexV1RestApi) {
        this.makerEnum = makerEnum;
        this.robotOrderService = cmxClient.perpetual().ccex().order();
        this.bitMexV1RestApi = bitMexV1RestApi;
    }

    public void execute(final DepthOrderBook cmxBook, final DepthOrderBook otherBook) {
        log.info("do trade, code: {}, since: {}", this.makerEnum, this.since);

        try {
            if (this.makerEnum.getTradeConfEnum() == null || this.isEmpty(cmxBook) || this.isEmpty(otherBook)) {
                log.info("do trade, TradeConfEnum is null, code:{}", this.makerEnum);

                return;
            }

            final double coinmexAsk1 = this.getAskOnePrice(cmxBook, PlatformEnum.CMX);
            final double coinmexBid1 = this.getBidOnePrice(cmxBook, PlatformEnum.CMX);
            final double otherAsk1 = this.getAskOnePrice(otherBook, this.makerEnum.getPlatform());
            final double otherBid1 = this.getBidOnePrice(otherBook, this.makerEnum.getPlatform());

            log.info("do trade, code: {}, coinmexAsk1: {}, coinmexBid1: {}, otherAsk1: {}, otherBid1: {}", this.makerEnum, coinmexAsk1, coinmexBid1, otherAsk1, otherBid1);

            double upperPrice = otherAsk1;
            double lowerPrice = otherBid1;

            if (coinmexAsk1 != 0 && otherBid1 < coinmexAsk1 && coinmexAsk1 < otherAsk1) {
                upperPrice = coinmexAsk1;
                lowerPrice = otherBid1;
            } else if (coinmexAsk1 != 0 && coinmexAsk1 < otherBid1) {
                upperPrice = coinmexAsk1;
                lowerPrice = coinmexBid1 > otherBid1 * 0.99 ? coinmexBid1 : otherBid1 * 0.99;
            } else if (coinmexBid1 != 0 && otherAsk1 > coinmexBid1 && coinmexBid1 > otherBid1) {
                upperPrice = otherAsk1;
                lowerPrice = coinmexBid1;
            } else if (coinmexBid1 != 0 && coinmexBid1 > otherAsk1) {
                upperPrice = coinmexAsk1 < otherAsk1 * 1.01 ? coinmexAsk1 : otherAsk1 * 1.01;
                lowerPrice = coinmexBid1;
            }

            log.info("do trade, code: {}, upperPrice: {}, lowerPrice: {}", this.makerEnum, upperPrice, lowerPrice);

            if (upperPrice <= lowerPrice) {
                throw new RuntimeException(String.format("DoTrade upperPrice:%s lowerPrice:%s error", upperPrice, lowerPrice));
            }

            final List<Double> amountList = this.getAmountList();
            if (CollectionUtils.isEmpty(amountList)) {
                log.info("do trade, amountList is null, code: {}", this.makerEnum);

                return;
            }

            // 撤销
            this.robotOrderService.deleteOrders(this.makerEnum.name().toLowerCase());

            // 下单
            this.doTrade(upperPrice, lowerPrice, amountList);
        } catch (final Exception e) {
            log.error("do trade error, code: " + this.makerEnum + ", msg: " + e.getMessage(), e);

            SimpleThreadUtils.sleep(1500);
        }
    }

    private boolean isEmpty(final DepthOrderBook orderBook) {
        if (orderBook == null) {
            return true;
        }

        return CollectionUtils.isEmpty(orderBook.getAsks()) || CollectionUtils.isEmpty(orderBook.getBids());
    }

    /**
     * 卖一价
     *
     * @param orderBook
     * @param platformEnum
     * @return
     */
    private double getAskOnePrice(final DepthOrderBook orderBook, final PlatformEnum platformEnum) {
        final List<String[]> asks = orderBook.getAsks();

        return Double.parseDouble(asks.get(0)[0]);
    }

    /**
     * 买一价
     *
     * @return
     */
    private double getBidOnePrice(final DepthOrderBook orderBook, final PlatformEnum platformEnum) {
        final List<String[]> bids = orderBook.getBids();

        // 价格从高到低
        return Double.parseDouble(bids.get(0)[0]);
    }

    private double getAmount(final List<Double> amountList, final int index) {
        double amount = amountList.get(index) * this.makerEnum.getTradeConfEnum().getAmountRatio();

        final double maxAmount = this.makerEnum.getTradeConfEnum().getMax();
        final double minAmount = this.makerEnum.getTradeConfEnum().getMin();

        if (amount < minAmount || amount > maxAmount) {
            amount = RandomUtils.nextDouble(minAmount, maxAmount);
        }

        amount = this.adjustAmount(amount);

        return amount;
    }

    private double adjustAmount(final double amount) {
        final double[] rates = new double[]{1.31415, 2.71828, 1.31415};

        double targetAmount = 0.0;

        for (int i = 0; i < rates.length; i++) {
            targetAmount += amount * rates[i];
        }

        final int i = RandomUtils.nextInt(0, 10) % 3;
        if (i == 0) {
            targetAmount += 0.1 * targetAmount;
        } else if (i == 1) {
            targetAmount += 0.01 * targetAmount;
        } else if (i == 2) {
            targetAmount += 0.001 * targetAmount;
        }

        return targetAmount;
    }

    /**
     * @param bid 买一价格
     * @param ask 卖一价格
     * @return
     */
    private double getPrice(final double bid, final double ask) {
        final int flag = RandomUtils.nextInt(0, 10);

        final double middle = (ask - bid) / 2;

        double random = 0.0;

        if (flag % 2 == 0) {
            random = middle + middle * Math.random();
        } else {
            random = middle - middle * Math.random();
        }

        // 价格
        final double price = ask - random;

        if (price >= ask || price <= bid) {
            log.info("do trade, price >= ask || price <= bid, makerEnum: {}, price: {}, ask: {}, bid: {}",
                    this.makerEnum, price, ask, bid);

            return -1;
        }

        return price;
    }

    private List<Double> getAmountList() {
        return this.getAmountListBitMex();
    }

    private List<Double> getAmountListBitMex() {
        synchronized (this) {
            final String code = this.makerEnum.getAlias();

            final List<BitMexTrade> tradeList = this.bitMexV1RestApi.historyTrade(code, 10);

            if (CollectionUtils.isEmpty(tradeList)) {
                return null;
            }

            return tradeList.stream().map(BitMexTrade::getSize).collect(Collectors.toList());
        }
    }

    private void doTrade(final double upperPrice, final double lowerPrice, final List<Double> amountList) throws Exception {
        for (int i = 0; i < MAX_ORDER_SIZE && i < amountList.size(); i++) {
            final double amount = this.getAmount(amountList, i);

            final double price = this.getPrice(lowerPrice, upperPrice);

            if (price <= 0) {
                continue;
            }

            // 下单
            this.doTrade(price, amount);
        }
    }

    private void doTrade(final double price, final double amount) throws Exception {
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
            log.error("do trade coinmex order error, code: {}, msg: {}", this.makerEnum, e.getMessage());

            //撤销
            this.robotOrderService.deleteOrders(this.makerEnum.name().toLowerCase());
        }

    }

    private void doTrade(final double price, final double amount, final OrderDetailSideEnum side) throws Exception {
        final OrderRequest orderRequest = OrderUtils.getOrderRequest(price, amount, side);

        final Map result = this.robotOrderService.postOrder(this.makerEnum.name().toLowerCase(), orderRequest);

        log.info("do trade coinmex order success, code: {}, result: {}", this.makerEnum, JSON.toJSONString(result));
    }

}
