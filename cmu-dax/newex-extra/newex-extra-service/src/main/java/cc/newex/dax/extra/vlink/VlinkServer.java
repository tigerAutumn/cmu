package cc.newex.dax.extra.vlink;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author gi
 * @date 10/25/18
 */
public interface VlinkServer {

    /**
     * 获取合约列表
     *
     * @param params
     * @return
     */
    JSONObject contractList(Map<String, Object> params);

    /**
     * 获取合约详情
     *
     * @param params
     * @return
     */
    JSONObject contractInfo(Map<String, Object> params);

    /**
     * 查询购买记录
     *
     * @param params
     * @return
     */
    JSONObject getOrder(Map<String, Object> params);

    /**
     * 订单预览
     *
     * @param params
     * @return
     */
    JSONObject orderPreview(Map<String, Object> params);

    /**
     * 算力下单
     *
     * @param params
     * @return
     */
    JSONObject orderSubmit(Map<String, Object> params);

    /**
     * 获取收益
     *
     * @param params
     * @return
     */
    JSONObject getEarning(Map<String, Object> params);

    /**
     * 根据email获取登录的auth信息
     *
     * @param params
     * @return
     */
    String getUserAuth(Map<String, Object> params);

    /**
     * 提现
     *
     * @param params
     * @return
     */
    String withDraw(Map<String, Object> params);


}
