package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author newex-team
 * @data 31/03/2018
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TxOutput implements Serializable {
    private BigDecimal value;
    private int n;
    private boolean spent;
    private ScriptPubKey scriptPubKey;
    private List<TxOutAsset> assets;

}
