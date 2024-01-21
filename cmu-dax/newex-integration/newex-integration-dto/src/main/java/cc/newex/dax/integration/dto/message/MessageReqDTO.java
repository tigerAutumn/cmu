package cc.newex.dax.integration.dto.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageReqDTO {
    /**
     * 对应 {@link MessageTemplateConsts}
     */
    private String templateCode;
    /**
     * 本地化语言代号(zh-cn,en-us等）
     */
    private String locale;
    /**
     * 全球国家电话代号参考{@see https://en.wikipedia.org/wiki/E.164 }
     */
    private String countryCode;
    /**
     * 收件人手机号(如发手机短信则必填),多个手机号用英文逗号分隔(如:13800000000,13800000001...)
     */
    private String mobile;
    /**
     * 收件人邮件地址(如发邮件则必填),多个邮件地址用英文逗号分隔(如:test1@example.com,test1@example.com...)
     */
    private String email;
    /**
     * {@link MessageTemplateConsts} 对应的参数变量
     * JSON格式字符串(如：{"name":"hello,world"})
     */
    private String params;

    private Integer brokerId = 1;
}
