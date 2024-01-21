package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.domain.UserSecureEvent;
import cc.newex.dax.users.rest.model.Last10LoginResVO;
import cc.newex.dax.users.service.security.UserSecureEventService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分页查询record接口
 *
 * @author newex-team
 * @date 2018/8/24
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/security/records")
public class SecurityRecordAdminController {
    @Autowired
    private UserSecureEventService userOperatorEventService;

    @PostMapping("/pager")
    public ResponseResult getSecureEvent(@RequestBody final DataGridPager pager,
                                         @RequestParam(value = "userId") final long userId) {

        final PageInfo pageInfo = pager.toPageInfo();

        final List<UserSecureEvent> list = this.userOperatorEventService.listByPage(pageInfo, userId);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success(Lists.newArrayListWithCapacity(0));
        }

        final String pattern = "yyyy-MM-dd HH:mm:ss";
        final List<Last10LoginResVO> resVOList = Lists.newArrayListWithCapacity(list.size());
        for (final UserSecureEvent us : list) {
            final String dateTIme = DateFormatUtils.format(us.getCreatedDate(), pattern);
            resVOList.add(Last10LoginResVO.builder()
                    .dateTime(dateTIme)
                    .ipAddress(IpUtil.longToString(us.getIpAddress()))
                    .region(us.getRegion())
                    .type(LocaleUtils.getMessage(us.getBehaviorName()))
                    .build()
            );
        }
        
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), resVOList));
    }
}
