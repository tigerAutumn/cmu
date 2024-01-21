package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户受限制行为表
 *
 * @author newex-team
 * @date 2018-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLimitedBehavior implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 行为类型
     */
    private Integer type;
    /**
     * 登录名(邮箱或手机号)
     */
    private String loginName;
    /**
     * ip地址
     */
    private Long ipAddress;
    /**
     * ip所属地区
     */
    private String ipRegion;
    /**
     * 登录设备id
     */
    private String deviceId;
    /**
     * 最大限制数
     */
    private Integer maximum;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static UserLimitedBehavior getInstance() {
        return UserLimitedBehavior.builder()
                .id(0L)
                .type(0)
                .loginName("")
                .ipAddress(0L)
                .ipRegion("")
                .deviceId("")
                .maximum(5)
                .updatedDate(new Date())
                .build();
    }
}