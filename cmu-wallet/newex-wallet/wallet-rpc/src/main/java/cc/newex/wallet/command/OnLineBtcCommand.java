package cc.newex.wallet.command;

import cc.newex.wallet.pojo.rpc.TxInput;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @data 09/04/2018
 */
public interface OnLineBtcCommand extends BtcLikeCommand {
    /**
     * 创建地址
     *
     * @return
     */
    @JsonRpcMethod(value = "getnewaddress", required = true)
    String getnewaddress();

    /**
     * 验证地址
     *
     * @return
     */
    @JsonRpcMethod(value = "validateaddress", required = true)
    JSONObject validateaddress(String address);

    /**
     * 构建交易
     *
     * @return
     */
    @JsonRpcMethod(value = "createrawtransaction", required = true)
    String createrawtransaction(List<TxInput> txInputs, Map<String, BigDecimal> assets);

    /**
     * 对构建的交易进行签名
     *
     * @return
     */
    @JsonRpcMethod(value = "signrawtransaction", required = true)
    JSONObject signrawtransaction(String hex);

}
