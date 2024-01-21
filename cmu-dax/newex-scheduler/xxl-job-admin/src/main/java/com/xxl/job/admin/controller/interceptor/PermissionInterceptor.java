package com.xxl.job.admin.controller.interceptor;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigInteger;

/**
 * 权限拦截, 简易版
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    public static void setSession(HttpServletRequest request, XxlJobUser xxlJobUser) {
        request.getSession().setAttribute("xxlJobUser", xxlJobUser);
    }

    public static XxlJobUser getSession(HttpServletRequest request) {
        return (XxlJobUser)request.getSession().getAttribute("xxlJobUser");
    }

    public static void removeSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //防止创建Session
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/toLogin");
        }
        session.removeAttribute("xxlJobUser");
    }

    public static boolean login(HttpServletResponse response, String username, String password, boolean ifRemember) {

        // login token
        String tokenTmp = DigestUtils.md5Hex(username + "_" + password);
        tokenTmp = new BigInteger(1, tokenTmp.getBytes()).toString(16);
        //
        //if (!LOGIN_IDENTITY_TOKEN.equals(tokenTmp)){
        //return false;
        //}

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, tokenTmp, ifRemember);
        return true;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            removeSession(request, response);
        } catch (Exception e) {
            log.error("logout error", e);
            e.printStackTrace();
        }
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
    }

    public static boolean ifLogin(HttpServletRequest request) {
        String indentityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        //从seeion中取用户信息
        XxlJobUser xxlJobUser=(XxlJobUser)request.getSession().getAttribute("xxlJobUser");
        if (indentityInfo==null || xxlJobUser==null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        if (!ifLogin(request)) {
            HandlerMethod method = (HandlerMethod)handler;
            PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
            if (permission == null || permission.limit()) {
                response.sendRedirect(request.getContextPath() + "/toLogin");
                //request.getRequestDispatcher("/toLogin").forward(request, response);
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }

}
