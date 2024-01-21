package cc.newex.dax.integration.service.msg.provider.sms.emay.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求时间
     */
    private long requestTime = System.currentTimeMillis();

    /**
     * 请求有效时间(秒)<br/>
     * 服务器接受时间与请求时间对比，如果超过有效时间，拒绝此次请求<br/>
     * 防止被网络抓包不断发送同一条请求<br/>
     * 默认1分钟有效期
     */
    private int requestValidPeriod = 60;

    public long getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(final long requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestValidPeriod() {
        return this.requestValidPeriod;
    }

    public void setRequestValidPeriod(final int requestValidPeriod) {
        this.requestValidPeriod = requestValidPeriod;
    }

}
