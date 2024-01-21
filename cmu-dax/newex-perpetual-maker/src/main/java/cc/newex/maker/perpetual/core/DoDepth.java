package cc.newex.maker.perpetual.core;

import cc.newex.maker.perpetual.enums.AccountEnum;
import cc.newex.maker.perpetual.enums.MakerEnum;
import cc.newex.maker.perpetual.enums.PlatformEnum;
import cc.newex.maker.utils.OrderUtils;
import cc.newex.maker.utils.SimpleThreadUtils;
import cc.newex.openapi.bitmex.domain.BitMexPosition;
import cc.newex.openapi.bitmex.domain.BitMexWalletInfo;
import cc.newex.openapi.bitmex.v1.BitMexV1RestApi;
import cc.newex.openapi.cmx.client.CmxClient;
import cc.newex.openapi.cmx.perpetual.domain.CurrentPosition;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import cc.newex.openapi.cmx.perpetual.domain.PositionPubConfig;
import cc.newex.openapi.cmx.perpetual.enums.OrderDetailSideEnum;
import cc.newex.openapi.cmx.perpetual.enums.OrderSideEnum;
import cc.newex.openapi.cmx.perpetual.service.AccountService;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import cc.newex.openapi.cmx.perpetual.service.PositionService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 做深度
 *
 * @author cmx-sdk-team
 * @date 2018/04/28
 */
@Slf4j
public class DoDepth {

    private int times;

    private volatile List<Long> buyOrderIds = new ArrayList<>();
    private volatile List<Long> sellOrderIds = new ArrayList<>();
    private volatile Map<Long, Double> orderDealMap = new HashMap<>();

    private MakerEnum makerEnum;

    private AccountService accountService;
    private OrderService orderService;
    private PositionService positionService;

    private BitMexV1RestApi bitMexV1RestApi;

    private static final Long MAX_RISK_LIMIT = 500L;

    private static final Integer MAX_AMOUNT = 5;

    private static final Double MIN_BALANCE = 0.3;

    public DoDepth(final MakerEnum makerEnum, final CmxClient cmxClient, final BitMexV1RestApi bitMexV1RestApi) {
        this.makerEnum = makerEnum;
        this.accountService = cmxClient.perpetual().ccex().account();
        this.orderService = cmxClient.perpetual().ccex().order();
        this.positionService = cmxClient.perpetual().ccex().position();
        this.bitMexV1RestApi = bitMexV1RestApi;
    }

    public void execute(final DepthOrderBook cmxBook, final DepthOrderBook otherBook) {
        // 深度数量0 不做深度
        if (this.makerEnum.getDepthConfEnum().getCnt() == 0 || this.isEmpty(otherBook)) {
            log.info("not do depth, count: 0, code: {}", this.makerEnum);

            return;
        }

        final double coinmexAsk1 = this.getAskOnePrice(cmxBook, PlatformEnum.CMX);
        final double coinmexBid1 = this.getBidOnePrice(cmxBook, PlatformEnum.CMX);
        final double otherAsk1 = this.getAskOnePrice(otherBook, this.makerEnum.getPlatform());
        final double otherBid1 = this.getBidOnePrice(otherBook, this.makerEnum.getPlatform());

        final double ask1 = Math.min(coinmexAsk1, otherAsk1);
        final double bid1 = Math.max(coinmexBid1, otherBid1);

        final double diff = (ask1 - bid1) / bid1;
        final double adjustRatio = this.makerEnum.getDepthConfEnum().getAdjustRatio();

        log.info("do depth, diff: {}, adjustRatio: {}", diff, adjustRatio);

        // 价格没有太大的波动
        if (diff > 0 && diff < adjustRatio * 2) {
            this.times++;

            if (this.times % 5 > 0) {
                log.info("not do depth, diff > 0 && diff < adjustRatio * 2, code: {}, times: {}", this.makerEnum, this.times);

                return;
            }
        }

        this.times = 0;

        try {
            // 初始化深度
            this.initOrder(otherBook);

            // 验证盘口价格
            this.verifyPrice(otherBook);

            this.arbitrage();
        } catch (final Exception e) {
            log.error("do depth error, code: " + this.makerEnum + ", msg: " + e.getMessage(), e);

            SimpleThreadUtils.sleep(1500);
        }
    }

    private void arbitrage() throws Exception {
        // 查看cmx的仓位
        final CurrentPosition cmxPosition = this.getCmxPosition();

        if (cmxPosition == null) {
            log.info("do depth, no cmx position, code: {}", this.makerEnum);

            return;
        }

        // 仓位类型，long多，short空
        final OrderSideEnum cmxSide = OrderSideEnum.getBySideName(cmxPosition.getSide());
        // 委托数量
        final Integer cmxAmount = cmxPosition.getAmount().intValue();

        if (cmxAmount == null || cmxAmount <= 0) {
            log.info("do depth, no cmx position, code: {}", this.makerEnum);

            return;
        }

        final Map assets = this.accountService.assets(this.makerEnum.name().toLowerCase());
        log.info("do depth, cmx assets, {}", JSON.toJSONString(assets));

        final Double cmxAvailable = Double.valueOf(assets.get("availableMargin").toString());
        if (cmxAvailable < MIN_BALANCE) {
            log.info("do depth, cmx available < MIN_BALANCE, code: {}", this.makerEnum);

            // 资金不足，平仓一半
            this.cmxOrder(-1.0, Math.min(cmxAmount / 2, MAX_AMOUNT), OrderDetailSideEnum.getOppositeCloseSide(cmxSide));
        }

        final PositionPubConfig positionPubConfig = this.positionService.getPositionConfig(this.makerEnum.name().toLowerCase());
        final Long cmxRiskLimit = Long.valueOf(positionPubConfig.getGear());
        if (cmxRiskLimit > MAX_RISK_LIMIT) {
            log.info("do depth, cmx riskLimit > MAX_RISK_LIMIT, code: {}", this.makerEnum);

            // 风险限额过高，平仓一半
            this.cmxOrder(-1.0, Math.min(cmxAmount / 2, MAX_AMOUNT), OrderDetailSideEnum.getOppositeCloseSide(cmxSide));
        }

        final AccountEnum bitMexAccount = this.makerEnum.getAccountGroupEnum().getBitMexAccount();
        if (bitMexAccount == null) {
            log.info("do depth, no bitmex account, code: {}", this.makerEnum);

            return;
        }

        // 查看bitmex的仓位
        final BitMexPosition bitMexPosition = this.getBitMexPosition();

        if (bitMexPosition == null) {
            log.info("do depth, first create bitmex order, code: {}", this.makerEnum);

            // 下反向单
            this.bitMexV1RestApi.createOrder(bitMexAccount, this.makerEnum.getAlias(), OrderSideEnum.getOppositeSide(cmxSide), cmxAmount, -1.0);

            return;
        }

        final OrderSideEnum bitMexSide = this.getBitMexSide(bitMexPosition);
        final Integer bitMexAmount = bitMexPosition.getCurrentQty();

        // 判断用户资产
        final BitMexWalletInfo bitMexWalletInfo = this.bitMexV1RestApi.getWalletInfo(bitMexAccount, bitMexPosition.getCurrency());
        final Double bitMexAvailable = bitMexWalletInfo.getAmount() / Math.pow(10, 8);

        // 资金不足
        if (bitMexAvailable < MIN_BALANCE) {
            log.info("do depth, bitmex available < MIN_BALANCE, code: {}", this.makerEnum);

            this.bitMexV1RestApi.createOrder(bitMexAccount, this.makerEnum.getAlias(), OrderSideEnum.getOppositeSide(bitMexSide), Math.min(bitMexAmount / 2, MAX_AMOUNT), -1.0);

            return;
        }

        // 判断风险限额
        final Long bitMexRiskLimit = bitMexPosition.getRiskLimit() / Double.valueOf(Math.pow(10, 8)).longValue();
        if (bitMexRiskLimit > MAX_RISK_LIMIT) {
            log.info("do depth, bitmex riskLimit > MAX_RISK_LIMIT, code: {}", this.makerEnum);

            // 风险限额过高，平仓一半
            this.bitMexV1RestApi.createOrder(bitMexAccount, this.makerEnum.getAlias(), OrderSideEnum.getOppositeSide(bitMexSide), Math.min(bitMexAmount / 2, MAX_AMOUNT), -1.0);

            return;
        }

        Integer additionalAmount = this.getAdditionalAmount(cmxSide, cmxAmount, bitMexSide, bitMexAmount);

        // 每次下单仓位数量最多是5
        if (additionalAmount > MAX_AMOUNT) {
            additionalAmount = MAX_AMOUNT;
        }

        // 每次下单仓位数量最多是5
        if (additionalAmount < -1 * MAX_AMOUNT) {
            additionalAmount = -1 * MAX_AMOUNT;
        }

        if (additionalAmount > 0) {
            // 下反向单
            log.info("do depth, create the opposite side bitmex order, code: {}", this.makerEnum);

            this.bitMexV1RestApi.createOrder(bitMexAccount, this.makerEnum.getAlias(), OrderSideEnum.getOppositeSide(cmxSide), additionalAmount, -1.0);
        } else if (additionalAmount < 0) {
            // 下同方向单
            log.info("do depth, create the same side bitmex order, code: {}", this.makerEnum);

            this.bitMexV1RestApi.createOrder(bitMexAccount, this.makerEnum.getAlias(), cmxSide, additionalAmount * -1, -1.0);
        }

    }

    private Integer getAdditionalAmount(final OrderSideEnum cmxSide, final Integer cmxAmount, final OrderSideEnum bitMexSide, final Integer bitMexAmount) {
        if (cmxSide.equals(bitMexSide)) {
            // 同方向
            return cmxAmount + bitMexAmount;
        } else {
            // 反方向
            return cmxAmount - bitMexAmount;
        }
    }

    private OrderSideEnum getBitMexSide(final BitMexPosition bitMexPosition) {
        // 平均开仓价格
        final Double avgEntryPrice = bitMexPosition.getAvgEntryPrice();

        // 强平价格
        final Double liquidationPrice = bitMexPosition.getLiquidationPrice();

        if (avgEntryPrice > liquidationPrice) {
            // 做多
            return OrderSideEnum.LONG;
        } else {
            // 做空
            return OrderSideEnum.SHORT;
        }
    }

    /**
     * 查看bitmex的仓位
     *
     * @return
     */
    private BitMexPosition getBitMexPosition() {
        final List<BitMexPosition> bitMexPositionList = this.bitMexV1RestApi.getPositionInfo(this.makerEnum.getAccountGroupEnum().getBitMexAccount(), this.makerEnum.getAlias());
        bitMexPositionList.stream().forEach(position -> log.info("bitmex position: {}", JSON.toJSONString(position)));

        if (CollectionUtils.isEmpty(bitMexPositionList)) {
            return null;
        }

        return bitMexPositionList.get(0);
    }

    /**
     * 查看cmx的仓位
     *
     * @return
     */
    private CurrentPosition getCmxPosition() throws Exception {
        final List<CurrentPosition> cmxPositionList = this.positionService.listAll(this.makerEnum.name().toLowerCase());
        cmxPositionList.stream().forEach(position -> log.info("cmx position: {}", JSON.toJSONString(position)));

        if (CollectionUtils.isEmpty(cmxPositionList)) {
            log.info("do depth, no cmx position, code: {}", this.makerEnum);

            return null;
        }

        return cmxPositionList.get(0);
    }

    /**
     * 卖一价
     *
     * @param orderBook
     * @param platformEnum
     * @return
     */
    private double getAskOnePrice(final DepthOrderBook orderBook, final PlatformEnum platformEnum) {
        if (orderBook == null) {
            return 0.0;
        }

        final List<String[]> asks = orderBook.getAsks();

        if (CollectionUtils.isEmpty(asks)) {
            return 0.0;
        }

        return Double.parseDouble(asks.get(0)[0]);
    }

    /**
     * 买一价
     *
     * @return
     */
    private double getBidOnePrice(final DepthOrderBook orderBook, final PlatformEnum platformEnum) {
        if (orderBook == null) {
            return 0.0;
        }

        final List<String[]> bids = orderBook.getBids();

        if (CollectionUtils.isEmpty(bids)) {
            return 0.0;
        }

        return Double.parseDouble(bids.get(0)[0]);
    }

    private boolean isEmpty(final DepthOrderBook orderBook) {
        if (orderBook == null) {
            return true;
        }

        return CollectionUtils.isEmpty(orderBook.getAsks()) || CollectionUtils.isEmpty(orderBook.getBids());
    }

    private void verifyPrice(final DepthOrderBook otherBook) throws Exception {
        this.verifyBuyPrice(otherBook);

        this.verifySellPrice(otherBook);
    }

    private void verifySellPrice(final DepthOrderBook otherBook) throws Exception {
        if (CollectionUtils.isEmpty(this.sellOrderIds)) {
            return;
        }

        final double outRisk = this.makerEnum.getDepthConfEnum().getOutRisk();

        final OrderBook order = this.orderService.getOrder(this.makerEnum.name().toLowerCase(), this.sellOrderIds.get(0));

        // 深度价格区间
        final double ask = Double.parseDouble(otherBook.getAsks().get(0)[0]);
        final double price = order.getPrice().doubleValue();

        final double minPrice = ask * (1 + outRisk);

        log.info("verifySellPrice, price: {}, ask: {}, minPrice: {}", price, ask, minPrice);

        if (price < minPrice) {
            log.error("verifySellPrice error, delete orders");

            this.deleteOrders();

            this.clear();
        }
    }

    private void verifyBuyPrice(final DepthOrderBook otherBook) throws Exception {
        if (CollectionUtils.isEmpty(this.buyOrderIds)) {
            return;
        }

        final double outRisk = this.makerEnum.getDepthConfEnum().getOutRisk();

        final OrderBook order = this.orderService.getOrder(this.makerEnum.name().toLowerCase(), this.buyOrderIds.get(0));

        // 深度价格区间
        final double bid = Double.parseDouble(otherBook.getBids().get(0)[0]);
        final double price = order.getPrice().doubleValue();

        final double maxPrice = bid * (1 - outRisk);

        log.info("verifyBuyPrice, price: {}, bid: {}, maxPrice: {}", price, bid, maxPrice);

        if (price > maxPrice) {
            log.error("verifyBuyPrice error, delete orders");

            this.deleteOrders();

            this.clear();
        }
    }

    private void clear() {
        this.buyOrderIds = new ArrayList<>();
        this.sellOrderIds = new ArrayList<>();
        this.orderDealMap = new HashMap<>();
    }

    private void deleteOrders() throws Exception {
        // 深度数量
        final int cnt = this.makerEnum.getDepthConfEnum().getCnt();

        for (int i = 0; i <= cnt * 2 / 50; i++) {
            //撤单
            this.orderService.deleteOrders(this.makerEnum.name().toLowerCase());
        }
    }

    /**
     * 初始化 深度列表
     *
     * @param otherBook
     * @throws Exception
     */
    private void initOrder(final DepthOrderBook otherBook) throws Exception {
        this.deleteOrders();
        this.clear();

        // 买单深度，价格从高到低
        final List<String[]> bids = otherBook.getBids();
        // 卖单深度，价格从低到高
        final List<String[]> asks = otherBook.getAsks();

        log.info("init order, size: {}, bids: {}", bids.size(), JSON.toJSONString(bids));
        log.info("init order, size: {}, asks: {}", asks.size(), JSON.toJSONString(asks));

        // 深度数量
        final int cnt = this.makerEnum.getDepthConfEnum().getCnt();

        final List<Double> randomList = this.getRandomList(cnt);

        for (int i = 0; i < cnt && i < asks.size() && i < bids.size(); i++) {
            // 做卖的深度
            this.initSellOrder(asks, randomList, i);

            // 做买的深度
            this.initBuyOrder(bids, randomList, i);
        }

    }

    private void initSellOrder(final List<String[]> asks, final List<Double> randomList, final int index) {
        // 深度数量
        final int cnt = this.makerEnum.getDepthConfEnum().getCnt();
        final double minAmount = this.makerEnum.getDepthConfEnum().getMinAmount();

        // 基础数量
        final double jcAmount = this.makerEnum.getDepthConfEnum().getAskQuota() * 0.4 / cnt;

        // 随机数量
        final double sjAmount = this.makerEnum.getDepthConfEnum().getAskQuota() * 0.4 * randomList.get(index);

        // 额外单独处理量
        final double extraAmount = this.makerEnum.getDepthConfEnum().getAskQuota() * 0.2;

        double sumAmount = jcAmount + sjAmount + extraAmount;

        sumAmount = Math.min(sumAmount, Double.parseDouble(asks.get(index)[1]) / this.makerEnum.getDepthConfEnum().getMultiple());
        if (sumAmount < minAmount) {
            sumAmount = minAmount;
        }

        // 得到下标对应价格
        final double price = this.adjustSellPrice(Double.parseDouble(asks.get(index)[0]));

        // 下单
        this.cmxOrder(price, sumAmount, OrderDetailSideEnum.OPEN_SHORT);
    }

    /**
     * 将卖单的价格调高
     *
     * @param price
     * @return
     */
    private double adjustSellPrice(final double price) {
        return price * (1 + this.makerEnum.getDepthConfEnum().getAdjustRatio());
    }

    private void initBuyOrder(final List<String[]> bids, final List<Double> randomList, final int index) {
        // 深度数量
        final int cnt = this.makerEnum.getDepthConfEnum().getCnt();
        final double minAmount = this.makerEnum.getDepthConfEnum().getMinAmount();

        // 基础数量
        final double jcAmount = this.makerEnum.getDepthConfEnum().getBidQuota() * 0.4 / cnt;

        // 随机数量
        final double sjAmount = this.makerEnum.getDepthConfEnum().getBidQuota() * 0.4 * randomList.get(index);

        // 额外单独处理量
        final double extraAmount = this.makerEnum.getDepthConfEnum().getBidQuota() * 0.2;

        double sumAmount = jcAmount + sjAmount + extraAmount;

        sumAmount = Math.min(sumAmount, Double.parseDouble(bids.get(index)[1]) / this.makerEnum.getDepthConfEnum().getMultiple());
        if (sumAmount < minAmount) {
            sumAmount = minAmount;
        }

        // 得到下标对应价格
        final double price = this.adjustBuyPrice(Double.parseDouble(bids.get(index)[0]));

        // 下单
        this.cmxOrder(price, sumAmount, OrderDetailSideEnum.OPEN_LONG);
    }

    /**
     * 将买单的价格调低
     *
     * @param price
     * @return
     */
    private double adjustBuyPrice(final double price) {
        return price * (1 - this.makerEnum.getDepthConfEnum().getAdjustRatio());
    }

    /**
     * 生长随机数列表
     *
     * @param size
     * @return
     */
    private List<Double> getRandomList(final int size) {
        final List<Integer> tmpList = new ArrayList<>();

        double sum = 0D;

        for (int i = 0; i < size; i++) {
            final int value = (int) (Math.random() * 100 + 1);

            sum = sum + value;

            tmpList.add(value);
        }

        final List<Double> randomList = new ArrayList<>();

        for (final int v : tmpList) {
            randomList.add(v / sum);
        }

        return randomList;
    }

    private void cmxOrder(final double price, final double amount, final OrderDetailSideEnum side) {
        final OrderRequest orderRequest = OrderUtils.getOrderRequest(price, amount, side);

        log.info("do depth coinmex order, {}", JSON.toJSONString(orderRequest));

        try {
            final Map result = this.orderService.postOrder(this.makerEnum.name().toLowerCase(), orderRequest);

            final Long orderId = Double.valueOf(result.get("id").toString()).longValue();

            if (side.equals(OrderDetailSideEnum.OPEN_LONG)) {
                this.buyOrderIds.add(orderId);
            }

            if (side.equals(OrderDetailSideEnum.OPEN_SHORT)) {
                this.sellOrderIds.add(orderId);
            }

            log.info("do depth coinmex order success, code: {}, result: {}", this.makerEnum, JSON.toJSONString(result));
        } catch (final Exception e) {
            log.error("do depth coinmex order error, code: {}, price: {}, amount: {}, side: {}, msg: {}", this.makerEnum, price, amount, side, e.getMessage());
        }
    }

}
