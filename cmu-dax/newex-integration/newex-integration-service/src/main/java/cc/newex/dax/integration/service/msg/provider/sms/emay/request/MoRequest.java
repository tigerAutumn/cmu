package cc.newex.dax.integration.service.msg.provider.sms.emay.request;


/**
 * 请求Balance参数
 *
 * @author Frank
 */
public class MoRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 请求数量<br/>
     * 最大500
     */
    private int number = 500;

    public int getNumber() {
        if (this.number <= 0 || this.number > 500) {
            this.number = 500;
        }
        return this.number;
    }

    public void setNumber(int number) {
        if (number > 500) {
            number = 500;
        }
        this.number = number;
    }

}
