package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

/**
 * @author newex-team
 * @data 2018/6/11
 */

@RpcConfig(server = "${newex.xrp.server}", needProxy = true)
public interface XrpCommand {
    @JsonRpcMethod(value = "account_info", required = true)
    JSONObject getAccountInfo(JSONObject param);

    @JsonRpcMethod(value = "ledger_current", required = true)
    JSONObject getCurrentHeight();

    @JsonRpcMethod(value = "tx", required = true)
    JSONObject getTransaction(JSONObject param);

    /**
     * The account_tx method retrieves a list of transactions that involved the specified account.
     *
     * @param param
     * @return
     * @see <a href="https://xrpl.org/account_tx.html">xrpl</a>
     */
    @JsonRpcMethod(value = "account_tx", required = true)
    JSONObject getAccountTx(JSONObject param);

    @JsonRpcMethod(value = "submit", required = true)
    JSONObject sendRawTransaction(JSONObject param);

    @JsonRpcMethod(value = "server_info", required = true)
    JSONObject getServerInfo();

}
