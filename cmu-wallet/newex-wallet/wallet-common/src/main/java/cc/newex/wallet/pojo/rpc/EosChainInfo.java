package cc.newex.wallet.pojo.rpc;

import lombok.Data;

/**
 * @author newex-team
 * @data 2018/6/4
 */

@Data
public class EosChainInfo {
    private String serverInfo;
    private String chainId;
    private Long headBlockNum;
    private String headBlockId;
    private String headBlockProducer;
}
