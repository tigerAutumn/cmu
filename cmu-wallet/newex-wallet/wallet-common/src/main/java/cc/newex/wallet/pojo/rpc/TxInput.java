package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author newex-team
 * @data 31/03/2018
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TxInput implements Serializable {
    private String coinbase;
    private String txid;
    private int vout;
    private long sequence;
    private TxOutput prev_out;
    private ScriptSig scriptSig;
}
