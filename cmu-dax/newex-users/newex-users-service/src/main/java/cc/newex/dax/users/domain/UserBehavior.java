package cc.newex.dax.users.domain;

import lombok.*;

import java.util.Date;

/**
 * 用户行为表
 *
 * @author newex-team
 * @date 2018-04-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBehavior {
    /**
     * 标识
     */
    private Integer id;
    /**
     * 行为类别
     */
    private String category;
    /**
     * 行为名称
     */
    private String name;
    /**
     * 行为说明
     */
    private String description;
    /**
     * 操作成功是否监控
     */
    private Byte hasSuccessMonitor;
    /**
     * 操作错误是否监控
     */
    private Byte hasFailureMonitor;
    /**
     * 操作最大重试次数
     */
    private Integer maxRetryLimit;
    /**
     * 冻结持续时间(单位:秒)表示当操作达到最大重试次数时,冻结多长时间后可以继续操作
     */
    private Integer durationOfFreezing;
    /**
     * 当操作需要发送通知信息(如短信，邮件)时该操作对应的通知模板代号
     */
    private String noticeTemplateCode;
    /**
     * 是否需要登录态(0不需要，1需要)
     */
    private Byte needLogin;
    /**
     * 是否同时校验谷歌，手机和邮箱all:全部，single:单独验证，priority:按照谷歌，邮箱和手机的优先级选择其一
     */
    private String checkRule;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    public static UserBehavior getInstance() {
        return UserBehavior.builder()
                .category("")
                .name("")
                .description("")
                .hasSuccessMonitor((byte) 1)
                .hasFailureMonitor((byte) 1)
                .maxRetryLimit(5)
                .durationOfFreezing(120)
                .noticeTemplateCode("")
                .needLogin((byte) 1)
                .checkRule("")
                .updatedDate(new Date())
                .build();
    }
}