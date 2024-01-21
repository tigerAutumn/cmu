package cc.newex.wallet.command;


import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.BaicABIReq;
import cc.newex.wallet.pojo.rpc.BaicPushTransaction;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.api.vo.ChainInfo;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static cc.newex.wallet.annotation.RpcConfig.RpcType.REST_RPC;

@RpcConfig(
        server = "${newex.baic.server}",
        type = REST_RPC
)
public interface BaicCommand {
    /**
     * 获取abi信息
     * @param baicABIReq
     * @return
     */
    @POST("/v1/chain/abi_json_to_bin")
    JSONObject abiJsonToBin(@Body BaicABIReq baicABIReq);

    /**
     * 广播交易
     * @param baicPushTransaction
     * @return
     */
    @POST("/v1/chain/push_transaction")
    JSONObject pushTransaction(@Body BaicPushTransaction baicPushTransaction);

    /**
     * 获取链信息
     * @return
     */
    @POST("v1/chain/get_info")
    ChainInfo getChainInfo();
}
