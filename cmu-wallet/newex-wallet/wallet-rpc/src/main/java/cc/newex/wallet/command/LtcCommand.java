package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.ltc.server}",
        username = "${newex.ltc.server.user}",
        password = "${newex.ltc.server.pwd}"
)
public interface LtcCommand extends BtcLikeCommand {
}
