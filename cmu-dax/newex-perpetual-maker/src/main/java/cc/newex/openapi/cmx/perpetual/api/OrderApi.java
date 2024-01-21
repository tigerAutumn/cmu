package cc.newex.openapi.cmx.perpetual.api;

import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderBook;
import cc.newex.openapi.cmx.perpetual.domain.OrderRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * @author mexdm-sdk-team
 * @date 2018/12/08
 */
public interface OrderApi {
    /**
     * 获取订单列表
     *
     * @param contractCode
     * @return
     */
    @GET("perpetual/products/{contractCode}/list")
    Call<List<OrderBook>> list(@Path("contractCode") String contractCode);

    /**
     * 深度信息
     *
     * @param contractCode
     * @param size
     * @return
     */
    @GET("perpetual/public/products/{contractCode}/orderbook")
    Call<DepthOrderBook> depth(@Path("contractCode") String contractCode, @Query("size") Integer size);

    /**
     * 下单
     *
     * @param contractCode
     * @param body
     * @return
     */
    @POST("perpetual/products/{contractCode}/order")
    Call<Map> postOrder(@Path("contractCode") String contractCode, @Body OrderRequest body);

    /**
     * 根据订单 id 撤单
     */
    @HTTP(method = "DELETE", path = "perpetual/products/{contractCode}/order/{id}")
    Call<Void> deleteOrder(@Path("contractCode") String contractCode, @Path("id") Long id);

    /**
     * 批量撤单，会根据 contractCode 撤单
     */
    @HTTP(method = "DELETE", path = "perpetual/products/{contractCode}/orders")
    Call<Void> deleteOrders(@Path("contractCode") String contractCode);

    /**
     * 根据订单 id 获取订单信息
     */
    @GET("perpetual/products/{contractCode}/{id}")
    Call<OrderBook> getOrder(@Path("contractCode") String contractCode, @Path("id") Long id);
}
