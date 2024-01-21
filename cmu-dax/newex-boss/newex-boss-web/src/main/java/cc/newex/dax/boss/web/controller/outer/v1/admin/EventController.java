package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.EventExample;
import cc.newex.dax.boss.admin.domain.Event;
import cc.newex.dax.boss.admin.service.EventService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/admin/events")
public class EventController
        extends BaseController<EventService, Event, EventExample, Integer> {

    @GetMapping("/listByPage")
    @OpLog(name = "分页获取系统日志列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_EVENT_VIEW"})
    public ResponseResult listByPage(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Event> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping("/remove")
    @OpLog(name = "删除系统日志")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_EVENT_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success();
    }

    @GetMapping(value = "/clear")
    @OpLog(name = "清除系统日志")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_EVENT_CLEAR"})
    public ResponseResult clear() {
        this.service.clear();
        return ResultUtils.success();
    }
}