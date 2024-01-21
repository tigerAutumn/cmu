package cc.newex.dax.integration.service.msg.provider.sms.emay.response;

import java.io.Serializable;

/**
 * 上行数据
 *
 * @author Frank
 */
public class MoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mobile;// 手机号

    private String extendedCode; // 扩展码

    private String content;// 内容

    private String moTime;// 手机上行时间

    public MoResponse() {

    }

    public MoResponse(final String mobile, final String extendedCode, final String content, final String moTime) {
        this.mobile = mobile;
        this.extendedCode = extendedCode;
        this.content = content;
        this.moTime = moTime;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getExtendedCode() {
        return this.extendedCode;
    }

    public void setExtendedCode(final String extendedCode) {
        this.extendedCode = extendedCode;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getMoTime() {
        return this.moTime;
    }

    public void setMoTime(final String moTime) {
        this.moTime = moTime;
    }

}
