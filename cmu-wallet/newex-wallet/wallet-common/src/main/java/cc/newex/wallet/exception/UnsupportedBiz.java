package cc.newex.wallet.exception;

import cc.newex.commons.support.enums.ErrorCode;
import lombok.Data;

/**
 * @author newex-team
 * @data 27/03/2018
 */
@Data
public class UnsupportedBiz extends RuntimeException implements ErrorCode {
    private String biz;

    public UnsupportedBiz(final String biz) {
        this.biz = biz;
    }

    @Override
    public int getCode() {
        return 23002;
    }

    @Override
    public String getMessage() {
        return "Biz " + this.biz + " is not supported";
    }
}
