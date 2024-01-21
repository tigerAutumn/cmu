package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 28/03/2018
 */
@RpcConfig(
        server = "${newex.btc.server}",
        username = "${newex.btc.server.user}",
        password = "${newex.btc.server.pwd}"
)
public interface BtcCommand extends BtcLikeCommand {

}
