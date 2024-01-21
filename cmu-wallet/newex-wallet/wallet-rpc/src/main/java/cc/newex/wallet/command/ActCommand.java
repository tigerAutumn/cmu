package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 2018/5/10
 */
@RpcConfig(
        server = "${newex.act.server}",
        username = "${newex.act.server.user}",
        password = "${newex.act.server.pwd}"
)
public interface ActCommand extends ActLikeCommand {

}
