package cc.newex.dax.integration.service.msg.provider.sms.emay.response;

import java.io.Serializable;

/**
 * 状态报告数据
 *
 * @author Frank
 */
public class ReportResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String smsId;// 短信唯一标识

    private String customSmsId;// 客户自定义SmsId

    private String state;// 成功失败标识

    private String desc;// 状态报告描述

    private String mobile;// 手机号

    private String receiveTime;// 状态报告返回时间

    private String submitTime;// 信息提交时间

    private String extendedCode;// 扩展码

    public String getCustomSmsId() {
        return this.customSmsId;
    }

    public void setCustomSmsId(final String customSmsId) {
        this.customSmsId = customSmsId;
    }

    public String getState() {
        return this.state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getReceiveTime() {
        return this.receiveTime;
    }

    public void setReceiveTime(final String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public String getSmsId() {
        return this.smsId;
    }

    public void setSmsId(final String smsId) {
        this.smsId = smsId;
    }

    public String getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(final String submitTime) {
        this.submitTime = submitTime;
    }

    public String getExtendedCode() {
        return this.extendedCode;
    }

    public void setExtendedCode(final String extendedCode) {
        this.extendedCode = extendedCode;
    }

}
