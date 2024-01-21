package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.domain.UserSecureEvent;
import cc.newex.dax.users.rest.model.Last10LoginResVO;
import cc.newex.dax.users.service.security.UserSecureEventService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * 用户个人登录
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/records")
public class SecurityRecordController {
    @Autowired
    private UserSecureEventService userOperatorEventService;

    /**
     * 最后十次登录
     *
     * @param request
     * @return
     */
    @GetMapping("/last-10")
    public ResponseResult getLast10Login(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("getLast10Login, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final List<UserSecureEvent> list = this.userOperatorEventService.getLast10SecureEvents(userId);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success(Lists.newArrayListWithCapacity(0));
        }

        final String pattern = "yyyy-MM-dd HH:mm:ss";
        final Locale locale = new Locale(LocaleUtils.getLocale(request));
        final List<Last10LoginResVO> resVOList = Lists.newArrayListWithCapacity(list.size());
        for (final UserSecureEvent us : list) {
            final String dateTIme = DateFormatUtils.format(us.getCreatedDate(), pattern, locale);
            resVOList.add(Last10LoginResVO.builder()
                    .dateTime(dateTIme)
                    .ipAddress(IpUtil.longToString(us.getIpAddress()))
                    .region(us.getRegion())
                    .type(LocaleUtils.getMessage(us.getBehaviorName()))
                    .build()
            );
        }

        return ResultUtils.success(resVOList);
    }

    /**
     * @param request
     * @param pageIndex
     * @param pageSize
     * @description 获取分页的安全记录
     * @date 2018/6/27 下午3:44
     */
    @GetMapping("/secure-event")
    public ResponseResult getSecureEvent(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") final Integer pageIndex,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
                                         final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("listByPage, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        if (pageSize > 30) {
            pageSize = 30;
        }
        final DataGridPager dataGridPagerVO = new DataGridPager();
        dataGridPagerVO.setPage(pageIndex);
        dataGridPagerVO.setRows(pageSize);
        final PageInfo pageInfo = dataGridPagerVO.toPageInfo();

        final List<UserSecureEvent> list = this.userOperatorEventService.listByPage(pageInfo, userId);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success(Lists.newArrayListWithCapacity(0));
        }

        final String pattern = "yyyy-MM-dd HH:mm:ss";
        final Locale locale = new Locale(LocaleUtils.getLocale(request));
        final List<Last10LoginResVO> resVOList = Lists.newArrayListWithCapacity(list.size());
        for (final UserSecureEvent us : list) {
            final String dateTIme = DateFormatUtils.format(us.getCreatedDate(), pattern, locale);
            resVOList.add(Last10LoginResVO.builder()
                    .dateTime(dateTIme)
                    .ipAddress(IpUtil.longToString(us.getIpAddress()))
                    .region(us.getRegion())
                    .type(LocaleUtils.getMessage(us.getBehaviorName()))
                    .build()
            );
        }

        return ResultUtils.success(resVOList);
    }
}
