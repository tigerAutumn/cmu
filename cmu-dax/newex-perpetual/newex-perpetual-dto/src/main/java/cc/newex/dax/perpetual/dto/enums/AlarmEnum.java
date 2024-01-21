package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum AlarmEnum {
  EMAIL("email", "邮件", MsgConfigEnum.TYPE_FUTURE_EMAIL_SEND_FAIL_WARN_ZH, 10), //
  K_LINE("kline", "K线", MsgConfigEnum.TYPE_FUTURE_K_LINE_FAIL_WARN_ZH, 10), //
  ;

  private String name;
  private String chineseName;
  private MsgConfigEnum msgModel;
  private int intervalTime;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getChineseName() {
    return this.chineseName;
  }

  public void setChineseName(String chineseName) {
    this.chineseName = chineseName;
  }

  public MsgConfigEnum getMsgModel() {
    return this.msgModel;
  }

  public void setMsgModel(MsgConfigEnum msgModel) {
    this.msgModel = msgModel;
  }

  public int getIntervalTime() {
    return this.intervalTime;
  }

  public void setIntervalTime(int intervalTime) {
    this.intervalTime = intervalTime;
  }

  AlarmEnum(String name, String chineseName, MsgConfigEnum msgModel, int intervalTime) {
    this.name = name;
    this.chineseName = chineseName;
    this.msgModel = msgModel;
    this.intervalTime = intervalTime;
  }
}
