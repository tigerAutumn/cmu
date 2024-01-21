package cc.newex.dax.users.rest.common.util;

import cc.newex.dax.users.common.consts.UserRegFromConsts;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;

import javax.servlet.http.HttpServletRequest;

public class RegFromUtils {
    public static int getRegFrom(final HttpServletRequest request) {
        final Device device = DeviceUtils.getCurrentDevice(request);
        if (device.isNormal()) {
            return UserRegFromConsts.WEB_REGISTER;
        }
        if (device.isMobile()) {
            return UserRegFromConsts.PHONE_REGISTER;
        }
        if (device.isTablet()) {
            return UserRegFromConsts.TABLET_REGISTER;
        }
        return UserRegFromConsts.OTHER_REGISTER;
    }
}
