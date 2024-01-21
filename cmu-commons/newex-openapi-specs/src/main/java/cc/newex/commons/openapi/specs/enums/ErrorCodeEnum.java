package cc.newex.commons.openapi.specs.enums;

/**
 * @author newex-team
 * @date 2017/12/09
 **/
public interface ErrorCodeEnum {
    /**
     * 错误代号
     *
     * @return 错误代号
     */
    int getCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * get http status code eg:401/403/500/ etc.
     *
     * @return http status code
     */
    int getStatus();
}
