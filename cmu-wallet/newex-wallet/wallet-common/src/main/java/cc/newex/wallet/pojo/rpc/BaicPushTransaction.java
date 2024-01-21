package cc.newex.wallet.pojo.rpc;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author newex-team
 * @data 2019/1/21
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class BaicPushTransaction implements Serializable {
    private JSONArray signatures;
    private String compression;
    private String packed_context_free_data;
    private String packed_trx;
}
