package cc.newex.commons.openapi.specs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author newex-team
 * @date 2018-04-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiKeyInfo {
    private Long userId;
    private String label;
    private String apiKey;
    private String secret;
    private String passphrase;
    private List<String> authorities;
    private Long expiredTime;
    /**
     * 请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/2
     */
    private String rateLimit;
    /**
     * 用户请求服务器ip白名单列表
     */
    private String ipWhiteLists;
}
