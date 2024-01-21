package cc.newex.dax.extra.common.exception;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.extra.dto.error.FieldErrorMessage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * NewEx Extra 业务层异常类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public class ExtraBizException extends RuntimeException {

    private int code;

    // 字段校验异常信息
    private final List<FieldErrorMessage> fieldErrorMessageList = new ArrayList<>();

    public ExtraBizException() {
        super();
    }

    public ExtraBizException(final ErrorCode errorCode) {
        super(errorCode.getMessage());

        this.code = errorCode.getCode();
    }

    public ExtraBizException(final ErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }

    public ExtraBizException(final String key) {
        super(LocaleUtils.getMessage(key));
    }

    public void addFieldError(final FieldErrorMessage fieldErrorMessage) {
        this.fieldErrorMessageList.add(fieldErrorMessage);
    }

    public int getFieldErrorCount() {
        return this.getFieldErrorMessageList().size();
    }

    public boolean hasFieldErrors() {
        return (this.getFieldErrorCount() > 0);
    }

}
