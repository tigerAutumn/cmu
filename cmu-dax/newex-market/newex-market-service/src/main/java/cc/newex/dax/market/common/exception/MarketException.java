package cc.newex.dax.market.common.exception;

/**
 * 通用异常类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
public class MarketException extends RuntimeException {
    /**
     * 构造函数
     */
    public MarketException() {
        super();
    }

    /**
     * 信息
     *
     * @param msg
     */
    public MarketException(final String msg) {
        super(msg);
    }

    /**
     * @param msg   信息
     * @param cause 原因
     */
    public MarketException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
