package cc.newex.commons.security.jwt;

import cc.newex.commons.security.jwt.enums.BizTypeEnum;
import cc.newex.commons.security.jwt.enums.GlobalFrozenEnum;
import cc.newex.commons.security.jwt.exception.BusinessFrozenException;
import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtFrozenConfig;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.ucenter.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Slf4j
public class JwtFrozenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtFrozenConfig frozenConfig;
    @Autowired
    private SessionService sessionService;

    public JwtFrozenInterceptor() {
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        log.debug("request url:{}", request.getRequestURL());
        this.validateFrozen(request);
        return super.preHandle(request, response, handler);
    }


    private void validateFrozen(final HttpServletRequest request) {
        final JwtUserDetails jwtUserDetails = (JwtUserDetails) request.getAttribute(JwtConsts.JWT_CURRENT_USER);
        if (jwtUserDetails == null ||
                this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_FROZEN.getName()) ||
                jwtUserDetails.isFrozen() ||
                this.isSpotFrozen(jwtUserDetails) ||
                this.isC2CFrozen(jwtUserDetails) ||
                this.isContractsFrozen(jwtUserDetails) ||
                this.isAssetFrozen(jwtUserDetails)) {
            throw new BusinessFrozenException();
        }
    }

    private boolean isGlobalFrozen(final String name) {
        return Integer.valueOf(1).equals(this.sessionService.getGlobalStatus(name));
    }

    private boolean isSpotFrozen(final JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.SPOT &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_SPOT_FROZEN.getName()) || jwtUserDetails.isSpotFrozen())
        );
    }

    private boolean isC2CFrozen(final JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.C2C &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_C2C_FROZEN.getName()) || jwtUserDetails.isC2CFrozen())
        );
    }

    private boolean isContractsFrozen(final JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.CONTRACTS &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_CONTRACTS_FROZEN.getName()) || jwtUserDetails.isContractsFrozen())
        );
    }

    private boolean isAssetFrozen(final JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.ASSET &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_ASSET_FROZEN.getName()) || jwtUserDetails.isAssetFrozen())
        );
    }
}
