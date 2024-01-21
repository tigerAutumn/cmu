package cc.newex.dax.users.rest.common.service;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.IpAddressClient;
import cc.newex.dax.integration.dto.ip.IpLocationDTO;
import cc.newex.dax.users.common.util.WebUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserLoginEvent;
import cc.newex.dax.users.domain.UserSecureEvent;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.service.security.UserLoginEventService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统事件外观服务类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class EventFacadeService {
    @Autowired
    private UserLoginEventService userLoginLogService;
    @Autowired
    private UserSecureEventService secureEventService;
    @Autowired
    private IpAddressClient ipAddressClient;

    /**
     * 记录最后登录IP和sessionID
     *
     * @param request
     * @param userDetailInfo
     * @param jwtUserDetails
     */
    public void addSecureAndLoginEvent(final HttpServletRequest request,
                                       final UserDetailInfo userDetailInfo,
                                       final JwtUserDetails jwtUserDetails, final BehaviorNameEnum behaviorNameEnum) {
        final UserLoginEvent userLoginLog = this.createUserLoginEvent(request, userDetailInfo.getId());
        userLoginLog.setSessionId(jwtUserDetails.getSid());
        userLoginLog.setLastLoginDate(new Date());
        this.userLoginLogService.add(userLoginLog);

        final UserSecureEvent secureEvent = UserSecureEvent.getInstance();
        secureEvent.setUserId(userDetailInfo.getId());
        secureEvent.setCreatedDate(new Date());
        secureEvent.setBehaviorId(behaviorNameEnum.getId());
        secureEvent.setBehaviorName(behaviorNameEnum.getName());
        secureEvent.setIpAddress(userLoginLog.getIpAddress());
        secureEvent.setRegion(userLoginLog.getRegion());
        this.secureEventService.add(secureEvent);
    }

    /**
     * @param ip
     * @description 获取ip定位信息
     * @date 2018/4/27 下午4:44
     */
    private IpLocationDTO getIpLocationByIp(final String ip) {
        try {
            final ResponseResult<IpLocationDTO> result = this.ipAddressClient.getIpLocationInfo(ip);
            if (ObjectUtils.allNotNull(result)) {
                return result.getData();
            } else {
                return null;
            }
        } catch (final Exception e) {
            log.error("getIpLocationByIp error ip={}", ip);
        }

        return null;
    }

    public UserLoginEvent createUserLoginEvent(final HttpServletRequest request, final long userId) {
        final IpLocationDTO ipLocation = this.getIpLocationByIp(IpUtil.getRealIPAddress(request));
        return UserLoginEvent.builder()
                .userId(userId)
                .ipAddress(IpUtil.toLong(IpUtil.getRealIPAddress(request)))
                .region(ipLocation == null ? StringUtils.EMPTY : ipLocation.getRegion())
                .lastIpAddress(0L)
                .deviceId(StringUtils.defaultIfBlank(WebUtils.getDeviceId(request), StringUtils.EMPTY))
                .userAgent(StringUtils.defaultIfBlank(WebUtils.getUserAgent(request), StringUtils.EMPTY))
                .lastLoginDate(new Date())
                .build();
    }
}
