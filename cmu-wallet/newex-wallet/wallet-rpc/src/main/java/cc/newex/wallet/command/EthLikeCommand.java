package cc.newex.wallet.command;

import cc.newex.wallet.pojo.rpc.EthGasUsedDto;
import cc.newex.wallet.pojo.rpc.EthLikeBlock;
import cc.newex.wallet.pojo.rpc.EthRawTransaction;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 * @data 12/04/2018
 */
public interface EthLikeCommand extends IRpcCommand {
    @JsonRpcMethod(value = "eth_getBalance")
    String getBalance(String addStr, String str);

    @JsonRpcMethod(value = "eth_blockNumber")
    String getBlockNumber();

    @JsonRpcMethod("eth_getBlockByNumber")
    EthLikeBlock getBlockByHeight(String str, boolean flag);

    @JsonRpcMethod("eth_getBlockByHash")
    EthLikeBlock getBlockByHash(String str, boolean flag);

    @JsonRpcMethod("eth_sendRawTransaction")
    String sendRawTransaction(String params);

    @JsonRpcMethod("eth_gasPrice")
    String getGasPrice();

    @JsonRpcMethod("eth_getTransactionByHash")
    EthRawTransaction getTransaction(String txId);

    @JsonRpcMethod("eth_getTransactionReceipt")
    EthGasUsedDto getGasUsed(String txId);

    @JsonRpcMethod("eth_getTransactionCount")
    String getAddressNonce(String address, String latest);
}
