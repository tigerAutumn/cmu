package cc.newex.dax.integration.service.msg.provider.sms.emay.response;

import java.io.Serializable;

/**
 * 单条短信发送响应
 *
 * @author Frank
 */
public class SmsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统唯一smsId
     */
    private String smsId;

    private String mobile;

    private String customSmsId;

    public SmsResponse() {

    }

    public SmsResponse(final String smsId, final String mobile, final String customSmsId) {
        this.smsId = smsId;
        this.mobile = mobile;
        this.customSmsId = customSmsId;
    }

    public String getSmsId() {
        return this.smsId;
    }

    public void setSmsId(final String smsId) {
        this.smsId = smsId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getCustomSmsId() {
        return this.customSmsId;
    }

    public void setCustomSmsId(final String customSmsId) {
        this.customSmsId = customSmsId;
    }

}
