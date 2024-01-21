package cc.newex.dax.integration.service.msg.provider.sms.emay.request;


/**
 * 批量短信发送参数
 *
 * @author Frank
 */
public class SmsBatchOnlyRequest extends SmsBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号与自定义SmsId
     */
    private String[] mobiles;

    /**
     * 短信内容
     */
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String[] getMobiles() {
        return this.mobiles;
    }

    public void setMobiles(final String[] mobiles) {
        this.mobiles = mobiles;
    }

}
