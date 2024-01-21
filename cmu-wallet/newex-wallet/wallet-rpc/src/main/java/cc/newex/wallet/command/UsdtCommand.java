package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import cc.newex.wallet.pojo.rpc.OmniRawTransaction;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * @author newex-team
 * @data 08/04/2018
 */
@RpcConfig(
        server = "${newex.usdt.server}",
        username = "${newex.usdt.server.user}",
        password = "${newex.usdt.server.pwd}"
)
public interface UsdtCommand extends BtcLikeCommand {
    @JsonRpcMethod(value = "omni_gettransaction", required = true)
    OmniRawTransaction getOmniRawTransaction(String txid);

    @JsonRpcMethod(value = "omni_listblocktransactions", required = true)
    Set<String> listOmniBlockTxs(long height);


    /**
     * getrawtransaction
     *
     * @param txid
     * @param verbose
     * @return
     */
    @Override
    @JsonRpcMethod(value = "getrawtransaction", required = true)
    BtcLikeRawTransaction getRawTransaction(String txid, int verbose);


    /**
     * 获取指定地址上的余额
     *
     * @param address
     * @return
     */
    @JsonRpcMethod(value = "omni_getbalance", required = true)
    Map<String, BigDecimal> getBalance(String address, int propertyId);
}
