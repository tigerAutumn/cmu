package cc.newex.dax.boss.web.model.activity;

import lombok.Data;

@Data
public class CurrencyAwardVO {

    /**
     * 活动标识
     */
    private String code;

    /**
     * 活动名称中文
     */
    private String cnTitle;

    /**
     * 活动名称英文
     */
    private String enTitle;

    /**
     * 短信模版编码
     */
    private String smsCode;

    /**
     * 邮件模版编码
     */
    private String mailCode;

    /**
     * 操作管理员
     */
    private String adminUser;

    /**
     * 发放奖励人数
     */
    private Long number;
}
