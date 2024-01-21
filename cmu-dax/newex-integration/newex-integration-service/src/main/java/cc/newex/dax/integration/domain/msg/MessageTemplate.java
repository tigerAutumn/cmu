package cc.newex.dax.integration.domain.msg;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 信息模板表
 *
 * @author newex-team
 * @date 2018-04-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageTemplate {
    /**
     * 标识
     */
    private Integer id;
    /**
     * 模板唯一编号
     */
    private String code;
    /**
     * 模板签名
     */
    private String sign;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 模板语言(zh-cn|en-us|ko-kr等）
     */
    private String locale;
    /**
     * 模板类型(email|sms等)
     */
    private String type;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static MessageTemplate createInstance() {
        return MessageTemplate.builder()
                .code("")
                .sign("")
                .subject("")
                .locale("en-us")
                .type("")
                .content("")
                .createdDate(new Date())
                .build();
    }
}
