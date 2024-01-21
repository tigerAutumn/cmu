package cc.newex.dax.integration.service.msg.provider.sms.emay.request;


/**
 * 单条短信发送参数
 *
 * @author Frank
 */
public class SmsSingleRequest extends SmsBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 自定义smsid
     */
    private String customSmsId;


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getCustomSmsId() {
        return this.customSmsId;
    }

    public void setCustomSmsId(final String customSmsId) {
        this.customSmsId = customSmsId;
    }

}
