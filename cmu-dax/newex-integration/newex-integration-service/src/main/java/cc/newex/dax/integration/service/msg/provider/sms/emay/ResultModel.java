package cc.newex.dax.integration.service.msg.provider.sms.emay;

public class ResultModel {

    private String code;
    private String result;

    public ResultModel(final String code, final String result) {
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

}
