package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.vollar.server}",
        username = "${newex.vollar.server.user}",
        password = "${newex.vollar.server.pwd}"
)
public interface VollarCommand extends OnLineBtcCommand {

}
