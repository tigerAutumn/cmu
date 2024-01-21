package cc.newex.dax.integration.service.msg.provider.sms.emay.request;


public class SmsBaseRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 定时时间
     * yyyy-MM-dd HH:mm:ss
     */
    private String timerTime;

    /**
     * 扩展码
     */
    private String extendedCode;

    public String getTimerTime() {
        return this.timerTime;
    }

    public void setTimerTime(final String timerTime) {
        this.timerTime = timerTime;
    }

    public String getExtendedCode() {
        return this.extendedCode;
    }

    public void setExtendedCode(final String extendedCode) {
        this.extendedCode = extendedCode;
    }

}
