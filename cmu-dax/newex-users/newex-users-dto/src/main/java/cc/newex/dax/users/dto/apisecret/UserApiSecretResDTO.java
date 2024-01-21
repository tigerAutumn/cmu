package cc.newex.dax.users.dto.apisecret;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserApiSecretResDTO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 备注名称
     */
    private String label;
    /**
     * api键
     */
    private String apiKey;
    /**
     * 密钥
     */
    private String secret;
    /**
     * secret口令盐
     */
    private String passphrase;
    /**
     * 权限值
     */
    private List<String> authorities;
    /**
     * 过期时间
     */
    private Long expiredTime;
    /**
     * 请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/2
     */
    private String rateLimit;
    /**
     * 用户请求服务器ip白名单列表
     */
    private String ipWhiteLists;
    /**
     * 券商ID
     */
    private Integer brokerId;
}
