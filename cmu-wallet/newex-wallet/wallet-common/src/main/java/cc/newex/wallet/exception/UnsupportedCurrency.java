package cc.newex.wallet.exception;

import cc.newex.commons.support.enums.ErrorCode;
import lombok.Data;

/**
 * @author newex-team
 * @data 27/03/2018
 */
@Data
public class UnsupportedCurrency extends RuntimeException implements ErrorCode {
    private String currency;

    public UnsupportedCurrency(final String currency) {
        this.currency = currency;
    }

    @Override
    public int getCode() {
        return 23001;
    }

    @Override
    public String getMessage() {
        return "Currency " + this.currency + " is not supported";
    }
}
