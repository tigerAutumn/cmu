package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @date 2018/11/28
 */
@RpcConfig(
        server = "${newex.zcash.server}",
        username = "${newex.zcash.server.user}",
        password = "${newex.zcash.server.pwd}"
)
public interface ZcashCommand extends BtcLikeCommand {

}
