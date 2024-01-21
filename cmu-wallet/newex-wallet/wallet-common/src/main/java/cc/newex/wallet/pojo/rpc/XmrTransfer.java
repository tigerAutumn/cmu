package cc.newex.wallet.pojo.rpc;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/12/25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class XmrTransfer implements Serializable {
    private String address;
    private BigDecimal amount;
    private Long confirmations;
    private Boolean double_spend_seen;
    private BigDecimal fee;
    private Long height;
    private String note;
    private String  payment_id;
    private JSON subaddr_index;
    private Long suggested_confirmations_threshold;
    private Long timestamp;
    private String txid;
    private String type;
    private Long unlock_time;
}
