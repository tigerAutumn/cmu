package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.god.server}",
        username = "${newex.god.server.user}",
        password = "${newex.god.server.pwd}"
)
public interface GodCommand extends OnLineBtcCommand {

}
