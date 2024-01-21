package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.rc.server}",
        username = "${newex.rc.server.user}",
        password = "${newex.rc.server.pwd}"
)
public interface RcCommand extends OnLineBtcCommand {

}
