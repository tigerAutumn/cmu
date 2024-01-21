package cc.newex.dax.perpetual.common.exception;


import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;

/**
 * @author harry
 * @Date: 2018/6/5 09:57
 * @Description:
 */
public class BizPreconditions {

    public static void checkState(boolean b, BizErrorCodeEnum bizErrorCodeEnum) {
        if (!b) {
            throw new BizException(bizErrorCodeEnum);
        }
    }

    public static void checkState(boolean b, BizErrorCodeEnum bizErrorCodeEnum,
                                  String errorMessageTemplate,
                                  Object... errorMessageArgs) {
        if (!b) {
            throw new BizException(bizErrorCodeEnum,
                    String.format(errorMessageTemplate, errorMessageArgs));
        }
    }

}