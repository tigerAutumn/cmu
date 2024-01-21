package cc.newex.dax.boss.web.controller.outer.v1.report;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.report.criteria.ConfExample;
import cc.newex.dax.boss.report.domain.Conf;
import cc.newex.dax.boss.report.service.ConfService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表配置控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/report/conf")
public class ConfController
        extends BaseController<ConfService, Conf, ConfExample, Integer> {

    @RequestMapping(value = "/listByPage")
    @OpLog(name = "获取指定ID的报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_VIEW"})
    public ResponseResult listByPage(final DataGridPager pager, final Integer id) {
        final int pid = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Conf> list = this.service.getByPage(pageInfo, pid);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @RequestMapping(value = "/listChildren")
    @OpLog(name = "获取指定ID的所有子报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_VIEW"})
    public ResponseResult listChildren(final Integer id) {
        final List<Conf> list = this.service.getByParentId(id == null ? 0 : id);
        final List<TreeNode<Conf>> easyUITreeNodes = new ArrayList<>(list.size());
        for (final Conf po : list) {
            final String confId = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String text = po.getName();
            final String state = po.isHasChild() ? "closed" : "open";
            final String icon = po.isHasChild() ? "icon-dict2" : "icon-item1";
            easyUITreeNodes.add(new TreeNode<>(confId, pid, text, state, icon, false, po));
        }
        return ResultUtils.success(easyUITreeNodes);
    }

    @RequestMapping(value = "/find")
    @OpLog(name = "分页查找指定ID的报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_VIEW"})
    public ResponseResult find(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Conf> list = this.service.getByPage(pageInfo, fieldName, keyword);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_ADD"})
    public ResponseResult add(final Conf po) {
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        this.service.add(po);
        return ResultUtils.success("");
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "编辑报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_EDIT"})
    public ResponseResult edit(final Conf po) {
        this.service.editById(po);
        return ResultUtils.success("");
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success("");
    }

    @GetMapping(value = "/getConfItems")
    @OpLog(name = "获取指定父key下的所有配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_CONF_VIEW"})
    public ResponseResult getConfItems(final String key) {
        return ResultUtils.success(this.service.getByParentKey(key));
    }
}