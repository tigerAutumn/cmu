package cc.newex.dax.boss.web.controller.outer.v1.report;

import cc.newex.commons.lang.pair.IdNamePair;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.commons.report.engine.util.VelocityUtils;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.report.criteria.ReportingExample;
import cc.newex.dax.boss.report.domain.History;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.options.QueryParameterOptions;
import cc.newex.dax.boss.report.service.HistoryService;
import cc.newex.dax.boss.report.service.ReportingService;
import cc.newex.dax.boss.report.service.TableReportService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 报表设计器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/report/designer")
public class DesignerController
        extends BaseController<ReportingService, Reporting, ReportingExample, Integer> {
    @Resource
    private HistoryService reportHistoryService;
    @Resource
    private TableReportService tableReportService;


    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取报表列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult<Map<String, Object>> listByPage(final DataGridPager pager, final Integer id) {
        final PageInfo pageInfo = this.getPageInfo(pager);
        final List<Reporting> list = this.service.getByPageWithCategory(
                pageInfo, "t1.category_id", id == null ? 0 : id
        );
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/find")
    @OpLog(name = "分页查询报表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult<Map<String, Object>> find(final DataGridPager pager,
                                                    final String fieldName, final String keyword) {
        final PageInfo pageInfo = this.getPageInfo(pager);
        final List<Reporting> list = this.service.getByPageWithCategory(
                pageInfo, "t1." + fieldName, "%" + keyword + "%"
        );
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/getAll")
    @OpLog(name = "获取所有报表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult getAll() {
        final List<Reporting> reportList = this.service.getAll();
        if (CollectionUtils.isEmpty(reportList)) {
            return ResultUtils.success(new ArrayList<>(0));
        }

        final List<IdNamePair> list = new ArrayList<>(reportList.size());
        list.addAll(reportList.stream()
                .map(report -> new IdNamePair(String.valueOf(report.getId()), report.getName()))
                .collect(Collectors.toList()));
        return ResultUtils.success(list);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增报表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_ADD"})
    public ResponseResult add(@CurrentUser final User loginUser, final Reporting po) {
        po.setAdminUsername(loginUser.getAccount());
        po.setAdminUserId(loginUser.getId());
        po.setUid(UUID.randomUUID().toString());
        po.setComment("");
        po.setCreatedDate(new Date());
        this.service.add(po);
        this.reportHistoryService.add(this.getReportHistory(loginUser, po));
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改报表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_EDIT"})
    public ResponseResult edit(@CurrentUser final User loginUser, final Reporting po) {
        this.service.editById(po);
        this.reportHistoryService.add(this.getReportHistory(loginUser, po));
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success();
    }

    @PostMapping(value = "/execSqlText")
    @OpLog(name = "获取报表元数据列集合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult execSqlText(final Integer dsId, String sqlText, Integer dataRange,
                                      final String queryParams, final HttpServletRequest request) {
        if (dsId != null) {
            if (dataRange == null) {
                dataRange = 7;
            }
            sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
            return ResultUtils.success(this.service.getMetaDataColumns(dsId, sqlText));
        }
        return ResultUtils.failure(10006, "没有选择数据源");
    }

    @PostMapping(value = "/previewSqlText")
    @OpLog(name = "预览报表SQL语句")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult previewSqlText(final Integer dsId, String sqlText, Integer dataRange,
                                         final String queryParams, final HttpServletRequest request) {
        if (dsId != null) {
            if (dataRange == null) {
                dataRange = 7;
            }
            sqlText = this.getSqlText(sqlText, dataRange, queryParams, request);
            this.service.explainSqlText(dsId, sqlText);
            return ResultUtils.success(sqlText);
        }
        return ResultUtils.failure(10006, "没有选择数据源");
    }

    @GetMapping(value = "/getMetaColumnScheme")
    @OpLog(name = "获取报表元数据列结构")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_VIEW"})
    public ResponseResult<ReportMetaDataColumn> getMetaColumnScheme() {
        final ReportMetaDataColumn column = new ReportMetaDataColumn();
        column.setName("expr");
        column.setType(4);
        column.setDataType("DECIMAL");
        column.setWidth(42);
        return ResultUtils.success(column);
    }

    private String getSqlText(final String sqlText, final Integer dataRange, final String queryParams,
                              final HttpServletRequest request) {
        final Map<String, Object> formParameters =
                this.tableReportService.getBuildInParameters(request.getParameterMap(), dataRange);
        if (StringUtils.isNotBlank(queryParams)) {
            final List<QueryParameterOptions> queryParameters = JSON.parseArray(queryParams,
                    QueryParameterOptions.class);
            queryParameters.stream()
                    .filter(parameter -> !formParameters.containsKey(parameter.getName()))
                    .forEach(parameter -> formParameters.put(parameter.getName(), parameter.getRealDefaultValue()));
        }
        return VelocityUtils.parse(sqlText, formParameters);
    }

    private PageInfo getPageInfo(final DataGridPager pager) {
        final PageInfo pageInfo = pager.toPageInfo("t1.");
        if ("dsName".equals(pager.getSort())) {
            pageInfo.setSortItem("t1.ds_id");
        }
        return pageInfo;
    }

    private History getReportHistory(@CurrentUser final User loginUser, final Reporting po) {
        return History.builder()
                .reportId(po.getId())
                .categoryId(po.getCategoryId())
                .dsId(po.getDsId())
                .author(loginUser.getAccount())
                .comment(po.getComment())
                .name(po.getName())
                .uid(po.getUid())
                .metaColumns(po.getMetaColumns())
                .queryParams(po.getQueryParams())
                .options(po.getOptions())
                .sqlText(po.getSqlText())
                .status(po.getStatus())
                .sequence(po.getSequence())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}