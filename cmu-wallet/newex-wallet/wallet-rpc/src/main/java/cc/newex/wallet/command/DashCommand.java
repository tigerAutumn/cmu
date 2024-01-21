package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.dash.server}",
        username = "${newex.dash.server.user}",
        password = "${newex.dash.server.pwd}"
)
public interface DashCommand extends BtcLikeCommand {
}
