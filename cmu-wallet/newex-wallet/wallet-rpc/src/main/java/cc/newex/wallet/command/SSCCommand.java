package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 2018/5/10
 */
@RpcConfig(
        server = "${newex.ssc.server}",
        username = "${newex.ssc.server.user}",
        password = "${newex.ssc.server.pwd}"
)
public interface SSCCommand extends ActLikeCommand {

}
