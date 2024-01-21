package cc.newex.dax.boss.web.common.aop;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.aop.OpLogAspect;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.dax.boss.admin.domain.Event;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/03/25
 **/
@Slf4j
@Aspect
@Component
public class BossOpLogAspect extends OpLogAspect {
    @Resource
    private EventService eventService;

    @Override
    protected void logEvent(final EventParameter eventParameter) {
        final HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final User user = (User) req.getAttribute(UserAuthConsts.CURRENT_USER);
        if (user != null) {
            final Event event = Event.builder()
                    .source(eventParameter.getName())
                    .account(user.getAccount())
                    .userId(user.getId())
                    .message(eventParameter.toString())
                    .level(eventParameter.getLevel())
                    .url(req.getRequestURL().toString())
                    .type(eventParameter.getType())
                    .siteUserId(ObjectUtils.defaultIfNull(eventParameter.getSiteUserId(), 0L))
                    .createdDate(new Date())
                    .brokerId(user.getBrokerId())
                    .build();
            this.eventService.add(event);
        }
    }
}

