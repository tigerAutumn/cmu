package cc.newex.dax.integration.service.msg.provider.sms.emay.response;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private T data;

    public ResponseData(final String code, final T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseData() {

    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }


}
