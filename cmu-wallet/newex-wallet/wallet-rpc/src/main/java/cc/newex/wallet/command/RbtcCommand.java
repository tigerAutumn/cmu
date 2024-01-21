package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@RpcConfig(server = "${newex.rbtc.server}")
public interface RbtcCommand extends EthLikeCommand {
    @JsonRpcMethod("eth_call")
    String getTokenBalance(JSONObject param, String latest);
}
