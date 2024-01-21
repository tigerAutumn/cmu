package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户登录事件表
 *
 * @author newex-team
 * @date 2018-04-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginEvent {
    /**
     *
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录设备ID
     */
    private String deviceId;
    /**
     * 当前登录IP地址
     */
    private Long ipAddress;
    /**
     * 最近一次登录的ip
     */
    private Long lastIpAddress;
    /**
     * User-Agent
     */
    private String userAgent;
    /**
     * 当前用户登录所在地区名及编号
     */
    private String region;
    /**
     * 当前登录的session_id对应jwt中的sid字段
     */
    private String sessionId;
    /**
     * 最近一次登录时间
     */
    private Date lastLoginDate;
    /**
     * 登录时间
     */
    private Date createdDate;
    /**
     * 记录更新时间
     */
    private Date updatedDate;

    public static UserLoginEvent getInstance() {
        return UserLoginEvent.builder()
                .userId(0L)
                .deviceId("")
                .ipAddress(0L)
                .lastIpAddress(0L)
                .userAgent("")
                .region("")
                .sessionId("")
                .updatedDate(new Date())
                .build();
    }
}