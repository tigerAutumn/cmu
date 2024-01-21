package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.bch.server}",
        username = "${newex.bch.server.user}",
        password = "${newex.bch.server.pwd}"
)
public interface BchCommand extends BtcLikeCommand {
}
