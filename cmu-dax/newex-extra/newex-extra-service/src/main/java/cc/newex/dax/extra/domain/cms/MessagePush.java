package cc.newex.dax.extra.domain.cms;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagePush {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 本地化语言代号(zh-cn/en-us等)
     */
    private String locale;

    /**
     * 类型
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 链接
     */
    private String link;

    /**
     * 平台
     */
    private String platform;

    /**
     * 推送时间
     */
    private Date pushTime;

    /**
     * 状态(等待推送/已推送)
     */
    private String status;

    /**
     * 发布人
     */
    private String operator;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}
