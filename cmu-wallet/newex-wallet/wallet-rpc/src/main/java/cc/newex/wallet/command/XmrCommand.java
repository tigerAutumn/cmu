package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.*;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcParamsPassMode;

import java.util.List;

@RpcConfig(
        server = "${newex.xmr.server}",
        username = "${newex.xmr.user}",
        password = "${newex.xmr.pwd}"
)
public interface XmrCommand {
    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#get_height
     *
     * @return
     */
    @JsonRpcMethod(value = "get_height", required = true)
    JSONObject getHeight();


    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#get_transfers
     * @param in
     * @param filter_by_height
     * @param min_height
     * @param max_height
     * @return
     */
    @JsonRpcMethod(value = "get_transfers", paramsPassMode = JsonRpcParamsPassMode.OBJECT, required = true)
    JSONObject  getTransfers(@JsonRpcParam(value = "in") Boolean in,
                             @JsonRpcParam(value = "out") Boolean out,
                             @JsonRpcParam(value = "filter_by_height") Boolean filter_by_height,
                             @JsonRpcParam(value = "min_height") Long min_height,
                             @JsonRpcParam(value = "max_height") Long max_height);


    /**
     *  https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#get_balance
     * @return
     */
    @JsonRpcMethod(value = "get_balance", required = true)
    JSONObject getBalance();

    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#export_outputs
     * @return
     */
    @JsonRpcMethod(value = "export_outputs", required = true)
    JSONObject exportOutputs();


    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#import_key_images
     * @param xmrSignedKeyImage
     * @param offset
     * @return
     */
    @JsonRpcMethod(value = "import_key_images", paramsPassMode = JsonRpcParamsPassMode.OBJECT, required = true)
    JSONObject importKeyImages(@JsonRpcParam("signed_key_images") List<XmrSignedKeyImage> xmrSignedKeyImage,
                               @JsonRpcParam("offset") Integer offset);

    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#transfer
     * @param xmrTransferDestinations
     * @param account_index
     * @param subaddr_indices
     * @param priority
     * @param get_tx_key
     * @param ring_size
     * @param payment_id
     * @return
     */
    @JsonRpcMethod(value = "transfer",  paramsPassMode = JsonRpcParamsPassMode.OBJECT, required = true)
    JSONObject transfer(@JsonRpcParam("destinations") List<XmrTransferDestinations> xmrTransferDestinations ,
                        @JsonRpcParam("account_index") Integer account_index,
                        @JsonRpcParam("subaddr_indices") List<Integer> subaddr_indices,
                        @JsonRpcParam("priority") Integer priority,
                        @JsonRpcParam("get_tx_key") Boolean get_tx_key,
                        @JsonRpcParam("ring_size") Integer ring_size,
                        @JsonRpcParam("payment_id") String payment_id);

    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#submit_transfer
     * @param tx_data_hex
     * @return
     */
    @JsonRpcMethod(value = "submit_transfer", paramsPassMode = JsonRpcParamsPassMode.OBJECT,required = true)
    JSONObject submitTransfer(@JsonRpcParam("tx_data_hex") String tx_data_hex);

    /**
     * https://www.getmonero.org/resources/developer-guides/wallet-rpc.html#get_transfer_by_txid
     * @param txid
     * @return
     */
    @JsonRpcMethod(value = "get_transfer_by_txid", paramsPassMode = JsonRpcParamsPassMode.OBJECT, required = true)
    JSONObject getTransferByTxid(@JsonRpcParam("txid") String txid);
}
