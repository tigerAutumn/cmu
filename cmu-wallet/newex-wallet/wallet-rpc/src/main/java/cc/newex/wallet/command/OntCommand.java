package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.OntBalance;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 */
@RpcConfig(server = "${newex.ont.server}")
public interface OntCommand {

    @JsonRpcMethod("getblock")
    JSONObject getBlock(Long blockNum, int verbose);

    @JsonRpcMethod("getsmartcodeevent")
    JSONArray getSmartcodeevent(Long blockNum, int verbose);

    @JsonRpcMethod("getblockcount")
    Integer getBlockCount(String latest);

    @JsonRpcMethod("getbalance")
    OntBalance getBalance(String address);

    @JsonRpcMethod("getgasprice")
    String getGasPrice();

    @JsonRpcMethod("sendrawtransaction")
    String sendRawTransaction(String params, int PreExec);

    @JsonRpcMethod("getblockheightbytxhash")
    int getblockheightbytxhash(String txId);

    @JsonRpcMethod("getunboundong")
    String getUnboundong(String address);

}
