package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.PositionSideEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.AssetsFeeRateService;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.FormulaUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/inner/v1/mock")
@Profile(value = {"dev", "daily", "test", "ppl-test", "ppl-daily"})
public class MockController {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private AssetsFeeRateService assetsFeeRateService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private UserBalanceService userBalanceService;

    /**
     * 更新指数价格
     *
     * @param currencyCode
     * @param price
     * @return
     */
    @RequestMapping("/index/price/{currencyCode}/{price}")
    public ResponseResult mockIndexPrice(@PathVariable("currencyCode") final String currencyCode,
                                         @PathVariable("price") final String price) {

        final String key = PerpetualCacheKeys.getPerpetualIndexPriceKey(currencyCode);

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("symbol", currencyCode);
        jsonObject.put("price", new BigDecimal(price));

        this.cacheService.setCacheValue(key, jsonObject.toJSONString());

        return ResultUtils.success(jsonObject);
    }

    /**
     * 取消所有订单(内部使用)
     * 或者指定用户的所有订单
     */
    @DeleteMapping("/cancelall/orders")
    public ResponseResult cancelAll(@RequestParam(value = "userId", required = false) final Long userId) {

        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        for (final Contract contract : contractList) {
            List<Order> orderList = this.orderShardingService.queryContractCodeOrderList(contract.getContractCode());
            if (CollectionUtils.isEmpty(orderList)) {
                MockController.log.info("订单列表为空");
                break;
            }
            if (userId != null) {
                orderList = orderList.stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
            }
            for (final Order order : orderList) {
                this.orderShardingService.cancelAll(order.getBrokerId(), order.getUserId(), order.getContractCode());
            }

        }
        return ResultUtils.success();
    }

    /**
     * 获取头部用户
     *
     * @param contractCode
     * @param side
     * @return
     */
    @GetMapping("/userRank/{contractCode}/{side}")
    public List<UserPositionService.UserRank> takeUserRank(@PathVariable("contractCode") final String contractCode,
                                                           @PathVariable("side") final String side) {
        return this.userPositionService.getUserRank(contractCode, OrderSideEnum.LONG.getSide().equalsIgnoreCase(side) ? OrderSideEnum.LONG : OrderSideEnum.SHORT);
    }

    /**
     * 获取标记价格信息
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/marketPrice/{contractCode}")
    public MarkIndexPriceDTO takeMarketPrice(@PathVariable("contractCode") final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        this.marketService.scheduleMarketPrice(contract);
        return this.marketService.getMarkIndex(contract);
    }

    /**
     * 获取深度缓存
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/depth/{contractCode}")
    public DepthCacheBean depth(@PathVariable("contractCode") final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        return this.marketDataShardingService.getDepthCacheBean(contract.getContractCode());
    }

    /**
     * 模拟测试资金费率
     *
     * @param contractCode
     * @param lastFeeRate
     * @param preminumIndex
     * @return
     */
    @GetMapping("/assetsFeeRate/{contractCode}/{lastFeeRate}/{preminumIndex}")
    public BigDecimal assetsFeeRate(@PathVariable("contractCode") final String contractCode,
                                    @PathVariable("lastFeeRate") final String lastFeeRate,
                                    @PathVariable("preminumIndex") final String preminumIndex) {
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contractCode);
        if (currencyPair == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        return this.assetsFeeRateService.takeFeeRate(currencyPair, new BigDecimal(lastFeeRate), new BigDecimal(preminumIndex));
    }

    @GetMapping("/clear/{contractCode}/{brokerId}/{userId}")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, BigDecimal> clear(@PathVariable("contractCode") final String contractCode,
                                         @PathVariable("brokerId") final Integer brokerId,
                                         @PathVariable("userId") final Long userId) {

        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }

        final Map<String, MarkIndexPriceDTO> priceMap = this.marketService.allMarkIndexPrice();
        final UserBalance userBalance = this.userBalanceService.getForUpdate(userId, brokerId, contract.getBase());
        final List<Contract> contracts = this.contractService.getUnExpiredContract().stream().filter(c -> c.getBase().equalsIgnoreCase(contract.getBase())).collect(Collectors.toList());

        final List<UserPosition> positions = this.userPositionService.getUserPositionByBase(userId, brokerId, contract.getBase());
        final UserPosition userPosition = positions.stream().filter(p -> p.getContractCode().equalsIgnoreCase(contractCode)).findFirst().orElse(null);
        if (userPosition == null) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }

        final BigDecimal subSize;
        final BigDecimal addSize;
        final MarkIndexPriceDTO markPrice = priceMap.get(userPosition.getContractCode());
        final boolean longToShort = markPrice.getFeeRate().compareTo(BigDecimal.ZERO) > 0;
        if (longToShort) {
            if (userPosition.getSide().equalsIgnoreCase(PositionSideEnum.LONG.getSide())) {
                subSize = this.sub(userBalance, userPosition, positions, priceMap, contracts);
                addSize = BigDecimal.ZERO;
            } else {
                addSize = this.add(userBalance, userPosition, positions, priceMap, contracts);
                subSize = BigDecimal.ZERO;
            }
        } else {
            if (userPosition.getSide().equalsIgnoreCase(PositionSideEnum.LONG.getSide())) {
                addSize = this.add(userBalance, userPosition, positions, priceMap, contracts);
                subSize = BigDecimal.ZERO;
            } else {
                subSize = this.sub(userBalance, userPosition, positions, priceMap, contracts);
                addSize = BigDecimal.ZERO;
            }
        }

        this.userBalanceService.editById(userBalance);
        this.userPositionService.batchEdit(positions);

        return new HashMap<String, BigDecimal>() {{
            this.put("subSize", subSize);
            this.put("addSize", addSize);
            this.put("marketPrice", markPrice.getMarkPrice());
            this.put("feeRate", markPrice.getFeeRate());
        }};
    }

    private BigDecimal sub(final UserBalance userBalance, final UserPosition userPosition, final List<UserPosition> positions, final Map<String, MarkIndexPriceDTO> priceMap, final List<Contract> contracts) {
        final MarkIndexPriceDTO markPrice = priceMap.get(userPosition.getContractCode());
        final BigDecimal value = BigDecimalUtil.divide(markPrice.getFeeRate().abs(), markPrice.getMarkPrice());
        final BigDecimal totalSize = BigDecimalUtil.multiply(userPosition.getAmount(), value);
        BigDecimal tempSize = totalSize;

        {
            // 如果是全仓 从 用户余额、订单保证金、订单手续费、仓位保证金中扣除 如果是逐仓 从 仓位保证金中扣除
            if (userPosition.getType().equals(PositionTypeEnum.ALL_IN.getType())) {
                // 全仓 如果 仓位保证金-未实现 > 0 则最大可用 是 available， 否则 available - （仓位保证金-未实现）
                BigDecimal availableBalance = userBalance.getAvailableBalance();
                // 未实现的亏损（仓位保证金-未实现）> 0 则为 0 否则 （仓位保证金-未实现）
                BigDecimal subTotal = BigDecimal.ZERO;
                for (final UserPosition u : positions) {
                    if (u.getType().equals(PositionTypeEnum.PART_IN.getType())) {
                        continue;
                    }
                    subTotal = subTotal.add(this.getSubAvailableBalance(userPosition, markPrice.getMarkPrice()));
                }
                availableBalance = availableBalance.subtract(subTotal);
                // 如果 剩余的 available 不够资金费用 则撤单
                if (availableBalance.compareTo(tempSize) < 0
                        && (userBalance.getOrderMargin().compareTo(BigDecimal.ZERO) > 0
                        || userBalance.getOrderFee().compareTo(BigDecimal.ZERO) > 0)) {
                    subTotal = BigDecimal.ZERO;
                    final String[] contractCodes = contracts.stream().map(Contract::getContractCode).collect(Collectors.toList()).toArray(new String[0]);
                    // 撤销所有订单
                    this.orderShardingService.cancelAll(userPosition.getBrokerId(), userPosition.getUserId(), contractCodes);
                    // 把订单保证金、订单手续费 挪到 available balance
                    for (final UserPosition u : positions) {
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(u.getOrderMargin()).add(u.getOrderFee()));
                        u.setOrderMargin(BigDecimal.ZERO);
                        u.setOrderFee(BigDecimal.ZERO);
                    }
                    userBalance.setOrderMargin(BigDecimal.ZERO);
                    userBalance.setOrderFee(BigDecimal.ZERO);
                    availableBalance = userBalance.getAvailableBalance();
                    for (final UserPosition u : positions) {
                        if (u.getType().equals(PositionTypeEnum.PART_IN.getType())) {
                            continue;
                        }
                        subTotal = subTotal.add(this.getSubAvailableBalance(userPosition, markPrice.getMarkPrice()));
                    }
                    availableBalance = availableBalance.subtract(subTotal);
                }

                if (availableBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    if (availableBalance.compareTo(tempSize) >= 0) {
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(tempSize));
                        tempSize = BigDecimal.ZERO;
                    } else {
                        tempSize = tempSize.subtract(availableBalance);
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(availableBalance));

                        final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, markPrice.getMarkPrice());

                        final BigDecimal openMargin = unrealSize.compareTo(BigDecimal.ZERO) >= 0 ? userPosition.getOpenMargin() : userPosition.getOpenMargin().subtract(unrealSize.abs());
                        if (openMargin.compareTo(BigDecimal.ZERO) > 0) {
                            if (openMargin.compareTo(tempSize) >= 0) {
                                userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(tempSize));
                                userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(tempSize));
                                tempSize = BigDecimal.ZERO;
                            } else {
                                tempSize = tempSize.subtract(openMargin);
                                userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(openMargin));
                                userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(openMargin));
                            }
                        }
                    }
                }
            } else {
                // 逐仓 开仓保证金-未实现
                BigDecimal availableBalance = userPosition.getOpenMargin();
                final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, markPrice.getMarkPrice());
                availableBalance = unrealSize.compareTo(BigDecimal.ZERO) < 0 ? availableBalance.subtract(unrealSize.abs()) : availableBalance;
                if (availableBalance.compareTo(BigDecimal.ZERO) > 0) {
                    if (availableBalance.compareTo(tempSize) >= 0) {
                        userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(tempSize));
                        userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(tempSize));
                        tempSize = BigDecimal.ZERO;
                    } else {
                        userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(availableBalance));
                        tempSize = tempSize.subtract(availableBalance);
                        userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(availableBalance));
                    }
                }
            }
        }
        FormulaUtil.calculationBrokerForcedLiquidationPrice(contracts, priceMap, userPosition, positions, userBalance);
        userPosition.setRealizedSurplus(userPosition.getRealizedSurplus().subtract(totalSize.subtract(tempSize)));
        userBalance.setRealizedSurplus(userBalance.getRealizedSurplus().subtract(totalSize.subtract(tempSize)));
        return totalSize.subtract(tempSize);
    }

    private BigDecimal add(final UserBalance userBalance, final UserPosition userPosition, final List<UserPosition> positions, final Map<String, MarkIndexPriceDTO> priceMap, final List<Contract> contracts) {

        final MarkIndexPriceDTO markPrice = priceMap.get(userPosition.getContractCode());
        final BigDecimal value = BigDecimalUtil.divide(userPosition.getAmount(), markPrice.getMarkPrice());
        final BigDecimal totalSize = BigDecimalUtil.multiply(value, markPrice.getFeeRate().abs());
        if (PositionTypeEnum.ALL_IN.getType() == userPosition.getType()) {
            userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(totalSize));
        } else {
            userPosition.setOpenMargin(userPosition.getOpenMargin().add(totalSize));
            userBalance.setPositionMargin(userBalance.getPositionMargin().add(totalSize));
        }
        FormulaUtil.calculationBrokerForcedLiquidationPrice(contracts, priceMap, userPosition, positions, userBalance);
        userPosition.setRealizedSurplus(userPosition.getRealizedSurplus().add(totalSize));
        userBalance.setRealizedSurplus(userBalance.getRealizedSurplus().add(totalSize));
        return value;
    }

    private BigDecimal getSubAvailableBalance(final UserPosition userPosition, final BigDecimal price) {
        final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, price);
        return unrealSize.compareTo(BigDecimal.ZERO) >= 0
                ? BigDecimal.ZERO
                : (userPosition.getOpenMargin().compareTo(unrealSize.abs()) >= 0
                ? BigDecimal.ZERO
                : userPosition.getOpenMargin().subtract(unrealSize.abs()).abs());
    }
}
