package cc.newex.wallet.command;

import cc.newex.wallet.pojo.rpc.BtcLikeBlock;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 * @data 08/04/2018
 */

public interface BtcLikeCommand extends IRpcCommand {
    /**
     * 获取区块链当前高度
     *
     * @return
     */
    @JsonRpcMethod(value = "getblockcount", required = true)
    long getBlockCount();

    /***
     * 获取指定高度的区块hash
     * @param height
     * @return
     */
    @JsonRpcMethod(value = "getblockhash", required = true)
    String getBlockHash(long height);


    /**
     * 根据hash获取区块详情
     *
     * @param hash
     * @return
     */
    @JsonRpcMethod(value = "getblock", required = true)
    BtcLikeBlock getBlock(String hash);

    /**
     * getrawtransaction
     *
     * @param txid
     * @param verbose
     * @return
     */
    @JsonRpcMethod(value = "getrawtransaction", required = true)
    BtcLikeRawTransaction getRawTransaction(String txid, boolean verbose);

    @JsonRpcMethod(value = "getrawtransaction", required = true)
    BtcLikeRawTransaction getRawTransaction(String txid, int verbose);

    /**
     * getrawtransaction
     *
     * @param txid
     * @return
     */
    @JsonRpcMethod(value = "getrawtransaction", required = true)
    String getRawTransactionStr(String txid);

    /**
     * 解析原始交易
     *
     * @param txHex
     * @return
     */
    @JsonRpcMethod(value = "decoderawtransaction", required = true)
    BtcLikeRawTransaction decodeRawTransactionStr(String txHex);

    /**
     * 发送原始交易
     *
     * @param signedhex
     * @return
     */
    @JsonRpcMethod(value = "sendrawtransaction", required = true)
    String sendRawTransaction(String signedhex);
}
