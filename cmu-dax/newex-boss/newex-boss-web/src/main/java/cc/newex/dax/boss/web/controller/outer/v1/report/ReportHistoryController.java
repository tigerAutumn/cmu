package cc.newex.dax.boss.web.controller.outer.v1.report;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.report.criteria.HistoryExample;
import cc.newex.dax.boss.report.domain.History;
import cc.newex.dax.boss.report.service.HistoryService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表历史记录控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/report/history")
public class ReportHistoryController
        extends BaseController<HistoryService, History, HistoryExample, Integer> {

    @RequestMapping(value = "/listByPage")
    @OpLog(name = "查看报表版本历史")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult<Map<String, Object>> list(final DataGridPager pager, final Integer reportId) {
        final PageInfo pageInfo = pager.toPageInfo();
        final Map<String, Object> modelMap = new HashMap<>(2);
        final List<History> list = this.service.getByPage(pageInfo, reportId == null ? 0 : reportId, null, null);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }
}