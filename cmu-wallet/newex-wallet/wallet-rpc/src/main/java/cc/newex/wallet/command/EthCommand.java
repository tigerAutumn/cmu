package cc.newex.wallet.command;

import cc.newex.wallet.annotation.RpcConfig;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@RpcConfig(server = "${newex.eth.server}")
public interface EthCommand extends EthLikeCommand {

}
