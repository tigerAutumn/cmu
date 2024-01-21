package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import retrofit2.http.GET;

import static cc.newex.wallet.annotation.RpcConfig.RpcType.REST_RPC;

/**
 * @author newex-team
 * @data 28/03/2018
 */
@RpcConfig(
        server = "${newex.eos.server}",
        type = REST_RPC
)
public interface EosCommand {

    /**
     * 获得chain的最新信息
     *
     * @return
     */
    @GET("/v1/chain/get_info")
    String getChainInfo();
}
