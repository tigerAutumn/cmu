package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by newex-team on 2018/12/13.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OntBalance implements Serializable {
    String ont;
    String ong;

}
