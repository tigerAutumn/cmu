package cc.newex.wallet.pojo.rpc;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * "scriptPubKey" : {
 * "asm" : "OP_DUP OP_HASH160 5ec92a9da7dfb810aa72b61178596a4ba6de9698 OP_EQUALVERIFY OP_CHECKSIG",
 * "hex" : "76a9145ec92a9da7dfb810aa72b61178596a4ba6de969888ac",
 * "reqSigs" : 1,
 * "type" : "pubkeyhash",
 * "addresses" : [
 * "19eBWKkbUNWkSu2Gn8dFz1d9QsMShgWs9B"
 * ]
 * }
 *
 * @author newex-team
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScriptPubKey implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5808212639641271980L;

    private String asm;
    private String hex;
    private int reqSigs;
    private String type;
    private List<String> addresses;

    public static long getSerialversionuid() {
        return ScriptPubKey.serialVersionUID;
    }

    public static ScriptPubKey convert(final String jsonString) {
        return JSON.parseObject(jsonString, ScriptPubKey.class);
    }


}
