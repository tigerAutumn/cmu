package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户安全事件表
 *
 * @author newex-team
 * @date 2018-04-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSecureEvent {
    /**
     *
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 行为编号(具体编号说明参考user_behavior表)
     */
    private Integer behaviorId;
    /**
     * 行为名称(具体编号说明参考user_behavior表)
     */
    private String behaviorName;
    /**
     * 修改之前的参数json
     */
    private String params;
    /**
     * 0:未处理 1:人工确认
     */
    private Integer status;
    /**
     * 说明
     */
    private String message;
    /**
     * ip地址
     */
    private Long ipAddress;
    /**
     * 操作执行所在地
     */
    private String region;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    public static UserSecureEvent getInstance() {
        return UserSecureEvent.builder()
                .userId(0L)
                .behaviorId(0)
                .behaviorName("")
                .params("")
                .status(0)
                .message("")
                .ipAddress(0L)
                .region("")
                .updatedDate(new Date())
                .build();
    }
}