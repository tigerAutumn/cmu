package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@RpcConfig(
        server = "${newex.bsv.server}",
        username = "${newex.bsv.server.user}",
        password = "${newex.bsv.server.pwd}"
)
public interface BsvCommand extends BtcLikeCommand {
}
