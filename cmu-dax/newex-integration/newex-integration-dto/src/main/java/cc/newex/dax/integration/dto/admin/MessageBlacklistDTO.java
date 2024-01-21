package cc.newex.dax.integration.dto.admin;

import lombok.*;

import java.util.Date;

/**
 * 短信与邮件黑名单表
 *
 * @author newex-team
 * @date 2018-08-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageBlacklistDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 类型sms|mail
     */
    private String type;
    /**
     * 手机或邮箱
     */
    private String name;
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
}