package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用来把rpc返回的结果映射成对象
 */
@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmniRawTransaction {
    private static final long serialVersionUID = 1L;

    private String txid;
    private BigDecimal fee;
    private String sendingaddress;
    private String referenceaddress;
    private boolean ismine;
    private int version;
    private int typeint;
    private String type;
    private int propertyid;
    private boolean divisible;
    private BigDecimal amount;
    private Boolean valid;
    private String blockhash;
    private String blocktime;
    private int positioninblock;
    private long block;
    private int confirmations;
}
