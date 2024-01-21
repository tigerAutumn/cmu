package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.EventExample;
import cc.newex.dax.boss.admin.domain.Event;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.EventService;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gi
 * @date 8/24/18
 */

@RestController
@Slf4j
@RequestMapping(value = "/v1/boss/users/oplog")
public class UserOplogController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UsersAdminClient usersAdminClient;
    @GetMapping(value = "/custom")
    @OpLog(name = "查看客服操作日志")
    public ResponseResult custom(@CurrentUser final User loginUser, final DataGridPager pager, @RequestParam(name = "userId") final Long userId) {
        final PageInfo pageInfo = pager.toPageInfo();
        final EventExample example = new EventExample();
        final EventExample.Criteria criteria = example.createCriteria();
        criteria.andSiteUserIdEqualTo(userId);
        if (loginUser.getBrokerId() != 0) {
            criteria.andBrokerIdEqualTo(loginUser.getBrokerId());
        }
        final List<Event> result = this.eventService.getByPage(pageInfo, example);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", result);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/user")
    @OpLog(name = "查看用户操作日志")
    public ResponseResult user(final DataGridPager pager, @RequestParam(name = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminClient.getSecureEvent(pager, userId);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/memo")
    @OpLog(name = "客服添加操作备注")
    public ResponseResult memo(@RequestParam(name = "id") final Integer id,
                               @RequestParam(name = "memo") final String memo) {
        final Event event = Event.builder().id(id).memo(memo).build();
        final int result = this.eventService.editById(event);
        return ResultUtils.success(result);
    }
}