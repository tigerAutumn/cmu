package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * {
 * "name": "lgacoin",
 * "issuetxid": "c2ba3758fb7a6f130e3c62afad05b0355a546908af55bedf33b6e711646a1948",
 * "assetref": "23-266-47810",
 * "qty": 50.00000000,
 * "raw": 5000000000,
 * "type": "transfer"
 * }
 *
 * @author newex-team
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TxOutAsset implements Serializable {

    private String name;
    private String issuetxid;
    private String assetref;
    private BigDecimal qty;
    private BigDecimal raw;
    private String type;
}
