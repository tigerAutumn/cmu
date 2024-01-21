package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.EthRawTransaction;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@RpcConfig(server = "${newex.of.server}")
public interface OfCommand extends EthLikeCommand {

    @JsonRpcMethod("ofbank_getBlock")
    JSONObject getBlock(Long blockNum);

    @JsonRpcMethod("ofbank_lastBN")
    @Override
    String getBlockNumber();

    @JsonRpcMethod("ofbank_show")
    @Override
    String getBalance(String address, String blockNum);

    @Override
    @JsonRpcMethod("ofbank_checkTrans")
    EthRawTransaction getTransaction(String txId);

    @Override
    @JsonRpcMethod("eth_gasPrice")
    String getGasPrice();

    @JsonRpcMethod("eth_call")
    String getTokenBalance(JSONObject param, String latest);
}
