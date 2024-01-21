package cc.newex.openapi.cmx.perpetual.service;

import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author coinmex-sdk-team
 * @date 2018/12/10
 */
public interface OrderService {

    List<OrderBook> list(String contractCode) throws IOException;

    DepthOrderBook depth(String contractCode, Integer size) throws IOException;

    Map postOrder(String contractCode, OrderRequest param) throws IOException;

    void deleteOrder(String contractCode, Long id) throws IOException;

    void deleteOrders(String contractCode) throws IOException;

    OrderBook getOrder(String contractCode, Long id) throws IOException;
}
