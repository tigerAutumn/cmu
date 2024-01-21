package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * apikey表
 *
 * @author newex-team
 * @date 2018-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserApiSecret {

    /**
     * 主键
     */
    private Long id;
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
    private String authorities;
    /**
     * 过期时间
     */
    private Long expiredTime;
    /**
     * ip地址
     */
    private Long ipAddress;
    /**
     * 请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/2
     */
    private String rateLimit;
    /**
     * 用户请求服务器ip白名单列表
     */
    private String ipWhiteLists;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;
    /**
     * 券商ID
     */
    private Integer brokerId;

    public static UserApiSecret getInstance() {
        return UserApiSecret.builder()
                .id(0L)
                .userId(0L)
                .label("")
                .apiKey("")
                .secret("")
                .passphrase("")
                .authorities("")
                .expiredTime(0L)
                .ipAddress(0L)
                .rateLimit("6/2")
                .ipWhiteLists("")
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}