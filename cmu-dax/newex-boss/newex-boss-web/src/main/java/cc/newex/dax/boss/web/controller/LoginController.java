package cc.newex.dax.boss.web.controller;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.MembershipFacade;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.Event;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.EventService;
import cc.newex.dax.boss.admin.service.IpWhiteService;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.web.common.security.BossAuthRequest;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 用户登录页控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private MembershipFacade<User> membershipFacade;
    @Resource
    private EventService eventService;
    @Resource
    private IpWhiteService ipWhiteService;
    @Resource
    private GoogleAuthenticator googleAuthenticator;

    @GetMapping("")
    public String login(final Model model, final HttpServletRequest request, final HttpServletResponse response) {
        LocaleUtils.setInitLocale(request, response);
        return "login";
    }

    @GetMapping("/{lang}")
    public ModelAndView language(@PathVariable final String lang, final HttpServletRequest request,
                                 final HttpServletResponse response) {
        LocaleUtils.setLocale(lang, request, response);
        return new ModelAndView("redirect:/");
    }

    @ResponseBody
    @PostMapping(value = "/auth")
    public ResponseResult auth(@Valid final BossAuthRequest authRequest, final HttpServletRequest req) {
        final User user = this.membershipFacade.getUserNonSensitiveInfo(authRequest.getAccount());
        if (user == null) {
            return ResultUtils.failure(BizErrorCodeEnum.USER_AND_PASSWORD_ERROR);
        }

        if (this.isProdOrPreMode()) {
            //如果不是超级管理员则需要判断IP登录限制
//            if (!this.membershipFacade.isAdministrator(user.getRoles()) &&
//                    this.isNotInIpWhiteList(user.getId(), req)) {
//                return ResultUtils.failure(BizErrorCodeEnum.LOGIN_IP_LIMITED);
//            }
            if (this.isInvalidCaptcha(authRequest.getCaptcha(), user.getTotpKey())) {
                return ResultUtils.failure(BizErrorCodeEnum.LOGIN_VERIFICATION_FAILURE);
            }
        }

        // Perform the security
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getAccount(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        req.getSession().setAttribute(UserAuthConsts.CURRENT_USER, user);

        final Event event = Event.getInstance();
        event.setUserId(user.getId());
        event.setSource("login");
        event.setAccount(user.getAccount());
        event.setMessage(LocaleUtils.getMessage("ctrl.login.successful"));
        event.setUrl("/login");
        this.eventService.add(event);
        return ResultUtils.success(null);
    }

    private boolean isProdOrPreMode() {
        return (AppEnvConsts.isProductionMode() || AppEnvConsts.isPreMode());
    }

    private boolean isNotInIpWhiteList(final Integer userId, final HttpServletRequest req) {
        final String realIP = IpUtil.getRealIPAddress(req);
        return !this.ipWhiteService.isIpInWhite(realIP);
    }

    private boolean isInvalidCaptcha(final String captcha, final String secretKey) {
        return !this.googleAuthenticator.authorize(secretKey, NumberUtils.toInt(captcha, 0));
    }
}