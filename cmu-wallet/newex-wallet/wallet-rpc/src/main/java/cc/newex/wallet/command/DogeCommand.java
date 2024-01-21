package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.doge.server}",
        username = "${newex.doge.server.user}",
        password = "${newex.doge.server.pwd}"
)
public interface DogeCommand extends BtcLikeCommand {
}
