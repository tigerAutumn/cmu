package cc.newex.openapi.bitmex.v1;

import cc.newex.maker.perpetual.enums.AccountEnum;
import cc.newex.openapi.bitmex.domain.BitMexPosition;
import cc.newex.openapi.bitmex.domain.BitMexTrade;
import cc.newex.openapi.bitmex.domain.BitMexWalletInfo;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.enums.OrderSideEnum;

import java.util.List;

/**
 * @author liutiejun
 * @date 2019-04-24
 */
public interface BitMexV1RestApi {

    /**
     * 市场深度数据
     *
     * @param code
     * @param depth Orderbook depth per side. Send 0 for full depth
     * @return
     */
    DepthOrderBook depth(String code, Integer depth);

    /**
     * 获得近期交易记录
     *
     * @param code
     * @param count
     * @return
     */
    List<BitMexTrade> historyTrade(String code, Integer count);

    /**
     * 下单
     *
     * @param account
     * @param code
     * @param side
     * @param amount
     * @param price
     * @return
     */
    String createOrder(AccountEnum account, String code, OrderSideEnum side, Integer amount, Double price);

    String getUserInfo(AccountEnum account);

    BitMexWalletInfo getWalletInfo(AccountEnum account, String currency);

    List<BitMexPosition> getPositionInfo(AccountEnum account, String code);

    String getOrderInfo(AccountEnum account, String code, Integer count);

}
