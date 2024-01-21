package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import com.alibaba.fastjson.JSONObject;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static cc.newex.wallet.annotation.RpcConfig.RpcType.REST_RPC;

/**
 * @author newex-team
 * @data 28/03/2018
 */
@RpcConfig(
        server = "${newex.btm.server}",
        type = REST_RPC
)
public interface BtmCommand {

    /**
     * 获得chain的最新信息
     *
     * @return
     */
    @POST("/get-block")
    JSONObject getBlock(@Body JSONObject param);

    /**
     * 获得chain的最新信息
     *
     * @return
     */
    @POST("/get-block-count")
    JSONObject getBlockCount();


    /**
     * 获得chain的最新信息
     *
     * @return
     */
    @POST("/get-transaction")
    JSONObject getTransaction(@Body JSONObject param);

    /**
     * 发送原始交易
     *
     * @param param
     * @return
     */
    @POST("/submit-transaction")
    JSONObject sendRawTransaction(@Body JSONObject param);
}
