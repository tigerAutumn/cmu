package cc.newex.dax.integration.domain.msg;

import lombok.*;

import java.util.Date;

/**
 * 短信与邮件发送白名单表
 *
 * @author newex-team
 * @date 2018-08-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageWhitelist {
    /**
     * 自增标识
     */
    private Integer id;
    /**
     * mail|sms等
     */
    private String type;
    /**
     * mobile或email地址
     */
    private String name;
    /**
     * 发送次数上限
     */
    private Integer maxRequestTimes;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * brokerId
     */
    private Integer brokerId;

    public static MessageWhitelist getInstance() {
        return MessageWhitelist.builder()
                .id(0)
                .type("")
                .name("")
                .maxRequestTimes(50)
                .createdDate(new Date())
                .build();
    }
}