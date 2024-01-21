package cc.newex.dax.extra.common.consts;

/**
 * The interface Rating token constant.
 *
 * @author better
 * @date create in 2018/11/29 11:33 AM
 */
public interface RatingTokenConstant {

    /**
     * 账号
     */
    String ACCOUNT = "coinmex";

    /**
     * 签名字符串
     */
    String SECRET = "L0dgkSHeS8R7puFgojHs1h097aBVmDar";

    /**
     * 鉴权字符串
     */
    String AUTHORIZATION = "hmac username=\"%s\", algorithm=\"hmac-sha256\",headers=\"x-date url\", signature=\"%s\"";
}
