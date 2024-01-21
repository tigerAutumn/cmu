package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.domain.OrderFinish;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/12/18
 */
public class PushDataUtil {

    /**
     * 订单转json
     *
     * @param orderList
     * @param contract
     * @return
     */
    public static JSONArray dealOrders(final List<Order> orderList, final Contract contract) {
        final JSONArray result = new JSONArray();
        for (final Order order : orderList) {
            final JSONObject json = (JSONObject) JSON.toJSON(order);
            result.add(json);
            json.put("id", order.getOrderId());
            json.put("amount", PushDataUtil.setDigit(order.getAmount(), PerpetualConstants.SCALE));
            json.put("price", order.getPrice());
            json.put("avgPrice", order.getAvgPrice());
            json.put("dealAmount", PushDataUtil.setDigit(order.getDealAmount(), PerpetualConstants.SCALE));
            // 基础货币最小交易小数位
            json.put("size", PushDataUtil.setDigit(order.getSize(), contract.getMinTradeDigit()));
            json.put("dealSize", PushDataUtil.setDigit(order.getDealSize(), contract.getMinTradeDigit()));
            json.put("openMargin", PushDataUtil.setDigit(order.getOpenMargin(), contract.getMinTradeDigit()));
            json.put("extraMargin", PushDataUtil.setDigit(order.getExtraMargin(), contract.getMinTradeDigit()));
            json.put("avgMargin", PushDataUtil.setDigit(order.getAvgMargin(), contract.getMinTradeDigit()));
            json.put("profit", PushDataUtil.setDigit(order.getProfit(), contract.getMinTradeDigit()));
            json.put("fee", PushDataUtil.setDigit(order.getFee(), contract.getMinTradeDigit()));

            json.put("contractDirection", contract.getDirection());
            json.put("base", contract.getBase());
            json.put("quote", contract.getQuote());
        }
        return result;
    }

    public static JSONArray dealOrderFinishs(final List<OrderFinish> orderList, final Contract contract) {
        final JSONArray result = new JSONArray();
        for (final OrderFinish order : orderList) {
            final JSONObject json = (JSONObject) JSON.toJSON(order);
            result.add(json);
            json.put("id", order.getOrderId());
            json.put("amount", PushDataUtil.setDigit(order.getAmount(), PerpetualConstants.SCALE));
            json.put("price", order.getPrice());
            json.put("avgPrice", order.getAvgPrice());
            json.put("dealAmount", PushDataUtil.setDigit(order.getDealAmount(), PerpetualConstants.SCALE));
            // 基础货币最小交易小数位
            json.put("size", PushDataUtil.setDigit(order.getSize(), contract.getMinTradeDigit()));
            json.put("dealSize", PushDataUtil.setDigit(order.getDealSize(), contract.getMinTradeDigit()));
            json.put("openMargin", PushDataUtil.setDigit(order.getOpenMargin(), contract.getMinTradeDigit()));
            json.put("extraMargin", PushDataUtil.setDigit(order.getExtraMargin(), contract.getMinTradeDigit()));
            json.put("profit", PushDataUtil.setDigit(order.getProfit(), contract.getMinTradeDigit()));
            json.put("fee", PushDataUtil.setDigit(order.getFee(), contract.getMinTradeDigit()));

            json.put("contractDirection", contract.getDirection());
            json.put("base", contract.getBase());
            json.put("quote", contract.getQuote());
        }
        return result;
    }

    /**
     * 条件订单转json
     *
     * @param orderConditionList
     * @param contract
     * @return
     */
    public static JSONArray dealConditionOrders(final List<OrderCondition> orderConditionList, final Contract contract) {
        final JSONArray result = new JSONArray();
        for (final OrderCondition order : orderConditionList) {
            final JSONObject json = (JSONObject) JSON.toJSON(order);
            result.add(json);
            json.put("id", order.getOrderId());
            json.put("amount", PushDataUtil.setDigit(order.getAmount(), PerpetualConstants.SCALE));
            json.put("price", order.getPrice());
            json.put("avgPrice", order.getAvgPrice());
            json.put("conditionPrice", order.getConditionPrice());
            json.put("dealAmount", PushDataUtil.setDigit(order.getDealAmount(), PerpetualConstants.SCALE));
            // 基础货币最小交易小数位
            json.put("size", PushDataUtil.setDigit(order.getSize(), contract.getMinTradeDigit()));
            json.put("dealSize", PushDataUtil.setDigit(order.getDealSize(), contract.getMinTradeDigit()));
            json.put("openMargin", PushDataUtil.setDigit(order.getOpenMargin(), contract.getMinTradeDigit()));
            json.put("extraMargin", PushDataUtil.setDigit(order.getExtraMargin(), contract.getMinTradeDigit()));
            json.put("avgMargin", PushDataUtil.setDigit(order.getAvgMargin(), contract.getMinTradeDigit()));

            json.put("contractDirection", contract.getDirection());
            json.put("base", contract.getBase());
            json.put("quote", contract.getQuote());
        }
        return result;
    }

    /**
     * 设置精度
     */
    private static String setDigit(final BigDecimal value, final int digit) {
        return value.setScale(digit, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
    }

    /**
     * 持仓转json
     *
     * @param positionList
     * @param contract
     * @return
     */
    public static JSONArray dealUserPositions(final List<UserPosition> positionList,
                                              final Contract contract) {
        for (final UserPosition userPosition : positionList) {
            userPosition.setOpenMargin(
                    userPosition.getOpenMargin().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            userPosition.setMaintenanceMargin(userPosition.getMaintenanceMargin()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            userPosition
                    .setFee(userPosition.getFee().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
                userPosition.setSize(
                        userPosition.getSize().setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN));
            } else {
                userPosition.setSize(
                        userPosition.getSize().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            }
            userPosition.setRealizedSurplus(userPosition.getRealizedSurplus()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
        }
        return JSON.parseArray(JSON.toJSONString(positionList));
    }

    /**
     * 持仓转json
     *
     * @param balanceList
     * @param contract
     * @return
     */
    public static JSONArray dealUserBalance(final List<UserBalance> balanceList,
                                            final Contract contract) {
        final JSONArray result = new JSONArray();
        for (final UserBalance userBalance : balanceList) {
            final JSONObject json = new JSONObject();
            result.add(json);
            json.put("userId", userBalance.getUserId());
            json.put("currencyCode", userBalance.getCurrencyCode());
            json.put("availableBalance", userBalance.getAvailableBalance().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("frozenBalance", userBalance.getFrozenBalance()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("positionMargin", userBalance.getPositionMargin().add(userBalance.getPositionFee())
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("positionFee", userBalance.getPositionFee()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("orderMargin", userBalance.getOrderMargin().add(userBalance.getOrderFee())
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("orderFee", userBalance.getOrderFee()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            json.put("realizedSurplus", userBalance.getRealizedSurplus()
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
            final BigDecimal totalBalance = userBalance.getAvailableBalance()
                    .add(userBalance.getFrozenBalance().add(userBalance.getOrderMargin()).add(userBalance.getOrderFee())
                            .add(userBalance.getPositionMargin()).add(userBalance.getPositionFee()));
            json.put("totalBalance", totalBalance
                    .setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
        }
        return result;
    }
}
