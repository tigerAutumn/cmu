package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户通知事件表
 *
 * @author newex-team
 * @date 2018-04-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNoticeEvent {
    /**
     *
     */
    private Long id;
    /**
     * 接收者用户
     */
    private Long userId;
    /**
     * 行为编号(具体编号说明参考user_behavior表)
     */
    private Integer behaviorId;
    /**
     * 通知类型 1验证码。2,用户通知 其他保留
     */
    private Integer type;
    /**
     * 通知模版代号
     */
    private String templateCode;
    /**
     * 目标邮件/手机号码
     */
    private String target;
    /**
     * 唯一对外标志
     */
    private String token;
    /**
     * 1.手机  2 邮件
     */
    private Integer channel;
    /**
     * -1 发送失败  0 新创建  1 发送成功
     */
    private Integer status;
    /**
     * 参数
     */
    private String params;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    public static UserNoticeEvent getInstance() {
        return UserNoticeEvent.builder()
                .userId(0L)
                .behaviorId(0)
                .type(0)
                .templateCode("")
                .target("")
                .token("")
                .channel(0)
                .status(0)
                .params("")
                .updatedDate(new Date())
                .build();
    }
}