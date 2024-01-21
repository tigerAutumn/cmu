package cc.newex.dax.integration.dto.admin;

import lombok.*;

import java.util.Date;

/**
 * 信息发送状态明细表
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageSendStatusDetailDTO {
    /**
     * 标识自增
     */
    private Long id;
    /**
     * 信息id
     */
    private Long msgId;
    /**
     * 发送渠道代号
     */
    private String channel;
    /**
     * 信息类型(mail|sms)
     */
    private String type;
    /**
     * 服务地区(china|international)
     */
    private String region;
    /**
     * 是否成功(1表示成功，0表示失败,其他保留)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdTime;

    private Integer brokerId;
}