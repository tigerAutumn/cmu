package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserLoginEventExample;
import cc.newex.dax.users.data.UserLoginEventRepository;
import cc.newex.dax.users.domain.UserLoginEvent;
import cc.newex.dax.users.service.security.UserLoginEventService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 用户登录日志 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service
public class UserLoginEventServiceImpl extends
        AbstractCrudService<UserLoginEventRepository, UserLoginEvent, UserLoginEventExample, Long>
        implements UserLoginEventService {
    @Override
    protected UserLoginEventExample getPageExample(final String fieldName, final String keyword) {
        final UserLoginEventExample example = new UserLoginEventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public long addUserLoginEvent(final long userId, final String ip, final String deviceId, final String sessionId) {
        final UserLoginEvent userLoginLog = UserLoginEvent.builder()
                .userId(userId)
                .ipAddress(IpUtil.ipDotDec2Long(ip))
                .region("")
                .lastIpAddress(IpUtil.ipDotDec2Long(ip))
                .deviceId(deviceId)
                .sessionId(sessionId)
                .lastLoginDate(new Date())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
        return this.dao.insert(userLoginLog);
    }

    @Override
    public UserLoginEvent getLastLoginLog(final long userId) {
        final UserLoginEventExample usExample = new UserLoginEventExample();
        usExample.createCriteria()
                .andUserIdEqualTo(userId);
        usExample.setOrderByClause(" id desc");
        return this.dao.selectOneByExample(usExample);
    }

    @Override
    public Boolean isLoginWithTodayByUserId(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate afterOneDay = now.plusDays(1);

        UserLoginEventExample example = new UserLoginEventExample();
        UserLoginEventExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andCreatedDateBetween(convertLocalDateToDate(now), convertLocalDateToDate(afterOneDay));

        return !CollectionUtils.isEmpty(this.getByExample(example));
    }

    private Date convertLocalDateToDate(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

}