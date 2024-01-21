package cc.newex.dax.boss.web.controller.outer.v1.config;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.config.domain.GlobalDict;
import cc.newex.dax.boss.config.service.GlobalDictService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/config/global-dicts")
public class GlobalDictController {
    @Resource
    private GlobalDictService globalDictService;

    @GetMapping(value = "/listByPage")
    @OpLog(name = "获取指定ID的报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_VIEW"})
    public ResponseResult listByPage(final DataGridPager pager, final Integer id) {
        final int pid = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<GlobalDict> list = this.globalDictService.getByPage(pageInfo, pid);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/listChildren")
    @OpLog(name = "获取指定ID的所有子报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_VIEW"})
    public ResponseResult listChildren(final Integer id) {
        final List<GlobalDict> list = this.globalDictService.getByParentId(id == null ? 0 : id);
        final List<TreeNode<GlobalDict>> easyUITreeNodes = new ArrayList<>(list.size());
        for (final GlobalDict po : list) {
            final String confId = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String state = po.isHasChild() ? "closed" : "open";
            final String icon = po.isHasChild() ? "icon-dict2" : "icon-item1";
            final String text = po.getName();
            easyUITreeNodes.add(new TreeNode<>(confId, pid, text, state, icon, false, po));
        }
        return ResultUtils.success(easyUITreeNodes);
    }

    @GetMapping(value = "/find")
    @OpLog(name = "分页查找指定ID的报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_VIEW"})
    public ResponseResult find(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<GlobalDict> list = this.globalDictService.getByPage(pageInfo, fieldName, keyword);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_ADD"})
    public ResponseResult add(final GlobalDict po) {
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        this.globalDictService.add(po);
        return ResultUtils.success("");
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_EDIT"})
    public ResponseResult edit(final GlobalDict po) {
        this.globalDictService.editById(po);
        return ResultUtils.success("");
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表元数据配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.globalDictService.removeById(id);
        return ResultUtils.success("");
    }

    @GetMapping(value = "/getGlobalDictItems")
    @OpLog(name = "获取指定父key下的所有配置项")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_GLOBAL_DICT_VIEW"})
    public ResponseResult getGlobalDictItems(final String key) {
        return ResultUtils.success(this.globalDictService.getByParentKey(key));
    }

}
