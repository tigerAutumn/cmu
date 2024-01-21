package cc.newex.dax.boss.admin.domain;

import lombok.*;

import java.util.Date;

/**
 * 后台系统用户历史操作事件表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHistory {
    /**
     * 日志标识
     */
    private Integer id;
    /**
     * 日志来源
     */
    private String source;
    /**
     * 操作用户id
     */
    private Integer userId;
    /**
     * 操作用户账号
     */
    private String account;
    /**
     * 日志级别
     */
    private String level;
    /**
     * 日志信息
     */
    private String message;
    /**
     * url
     */
    private String url;
    /**
     * 日志发生的时间
     */
    private Date createdDate;

    /**
     * brokerId
     */
    private Integer brokerId;

    public static EventHistory getInstance() {
        return EventHistory.builder()
                .source("")
                .userId(0)
                .account("")
                .level("")
                .message("")
                .url("")
                .brokerId(0)
                .build();
    }
}