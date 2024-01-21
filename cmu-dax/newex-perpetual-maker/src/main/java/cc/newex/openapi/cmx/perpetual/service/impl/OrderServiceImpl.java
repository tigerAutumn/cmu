package cc.newex.openapi.cmx.perpetual.service.impl;

import cc.newex.openapi.cmx.client.ApiClient;
import cc.newex.openapi.cmx.perpetual.api.OrderApi;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author coinmex-sdk-team
 * @date 2018/12/10
 */
public class OrderServiceImpl implements OrderService {

    private final OrderApi orderApi;

    public OrderServiceImpl(final ApiClient client) {
        this.orderApi = client.create(OrderApi.class);
    }

    private static void checkCode(final String code) {
        // 币对对不允许为空
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException(" code is required ");
        }
    }

    private static void checkOrderId(final Long orderId) {
        // 订单 id 不允许为空
        if (orderId == null) {
            throw new IllegalArgumentException(" orderId is required ");
        }
    }

    @Override
    public List<OrderBook> list(final String contractCode) throws IOException {
        final Call<List<OrderBook>> call = this.orderApi.list(contractCode);
        return call.execute().body();
    }

    @Override
    public DepthOrderBook depth(final String contractCode, final Integer size) throws IOException {
        final Call<DepthOrderBook> call = this.orderApi.depth(contractCode, size);
        return call.execute().body();
    }

    @Override
    public Map postOrder(final String contractCode, final OrderRequest param) throws IOException {
        final Call<Map> call = this.orderApi.postOrder(contractCode, param);
        return call.execute().body();
    }

    @Override
    public void deleteOrder(final String contractCode, final Long id) throws IOException {
        OrderServiceImpl.checkCode(contractCode);
        OrderServiceImpl.checkOrderId(id);
        this.orderApi.deleteOrder(contractCode, id).execute();
    }

    @Override
    public void deleteOrders(final String contractCode) throws IOException {
        this.orderApi.deleteOrders(contractCode).execute();
    }

    @Override
    public OrderBook getOrder(final String contractCode, final Long id) throws IOException {
        return this.orderApi.getOrder(contractCode, id).execute().body();
    }
}
