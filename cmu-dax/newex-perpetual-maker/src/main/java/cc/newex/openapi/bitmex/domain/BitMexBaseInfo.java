package cc.newex.openapi.bitmex.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2019-04-24
 */
public class BitMexBaseInfo {

    public static final String BASE_URL = "https://testnet.bitmex.com";

    public static final Map<String, String> HEAD_MAP = new HashMap<String, String>() {{
        this.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
        this.put("Accept", "application/json");
        this.put("Content-Type", "application/json");
        this.put("Accept-Language", "zh-cn");
    }};

}
