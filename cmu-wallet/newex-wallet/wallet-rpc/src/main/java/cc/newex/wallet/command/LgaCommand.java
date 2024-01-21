package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.pojo.rpc.LgaListPermission;
import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.util.List;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.lga.server}",
        username = "${newex.lga.server.user}",
        password = "${newex.lga.server.pwd}"
)
public interface LgaCommand extends BtcLikeCommand {
    /**
     * 授权地址权限
     *
     * @return
     */
    @JsonRpcMethod(value = "grant", required = true)
    String grant(String address, String permissions);

    /**
     * 返回该地址的所有权限
     *
     * @return
     */
    @JsonRpcMethod(value = "listpermissions", required = true)
    List<LgaListPermission> listpermissions(String type, String address);
}
