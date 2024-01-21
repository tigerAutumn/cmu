package cc.newex.wallet.pojo.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by newex-team on 2018/12/13.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OntEvent implements Serializable {
    int GasConsumed;


    String TxHash;

    int State;

    OntStates[] Notify;

}
