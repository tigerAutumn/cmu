package cc.newex.dax.users.event.listener;

import cc.newex.commons.support.eventbus.EventListener;
import cc.newex.dax.users.event.LoginEvent;
import cc.newex.dax.users.event.NoticeEvent;
import cc.newex.dax.users.event.SecureEvent;
import cc.newex.dax.users.service.security.UserLoginEventService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import com.google.common.eventbus.Subscribe;

import javax.annotation.Resource;

/**
 * @author newex-team
 * @date 2018-05-15
 */
@EventListener
public class UserEventListener {
    @Resource
    private UserLoginEventService userLoginEventService;
    @Resource
    private UserNoticeEventService userNoticeEventService;
    @Resource
    private UserSecureEventService userSecureEventService;

    @Subscribe
    public void onEvent(final LoginEvent event) {

    }

    @Subscribe
    public void onEvent(final NoticeEvent event) {

    }

    @Subscribe
    public void onEvent(final SecureEvent event) {
    }
}
