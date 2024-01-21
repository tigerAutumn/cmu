package cc.newex.dax.perpetual.openapi.support.common;

import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.openapi.support.aop.PerpetualOpenApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author allen
 * @date 2018/4/29
 * @des
 */
@Slf4j
@Component
public class AuthenticationUtils {

    public OpenApiUserInfo getUserInfo(final HttpServletRequest request) {
        final OpenApiUserInfo userInfo = (OpenApiUserInfo) request.getAttribute(UserAuthConsts.CURRENT_USER);

        if (userInfo == null) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Partner_not_exist);
        }

        return userInfo;
    }

}
