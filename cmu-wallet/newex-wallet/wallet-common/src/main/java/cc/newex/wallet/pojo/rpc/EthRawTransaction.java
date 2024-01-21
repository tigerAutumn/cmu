package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by newex-team on 2018/12/13.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthRawTransaction implements Serializable {
    private String hash;
    private String nonce;
    private String blockHash;
    private String blockNumber;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;

}
