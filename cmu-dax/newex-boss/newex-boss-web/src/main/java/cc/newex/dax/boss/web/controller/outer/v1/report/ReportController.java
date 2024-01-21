package cc.newex.dax.boss.web.controller.outer.v1.report;

import cc.newex.commons.report.engine.data.ReportDataSet;
import cc.newex.commons.report.engine.exception.NotFoundLayoutColumnException;
import cc.newex.commons.report.engine.exception.QueryParamsException;
import cc.newex.commons.report.engine.exception.SQLQueryException;
import cc.newex.commons.report.engine.exception.TemplatePraseException;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.BootstrapQueryFormView;
import cc.newex.dax.boss.report.model.EasyUIQueryFormView;
import cc.newex.dax.boss.report.model.QueryParamFormView;
import cc.newex.dax.boss.report.model.options.ReportingOptions;
import cc.newex.dax.boss.report.service.ChartReportService;
import cc.newex.dax.boss.report.service.ReportingService;
import cc.newex.dax.boss.report.service.TableReportService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 报表生成控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Controller
@RequestMapping(value = "/report")
public class ReportController {
    @Resource
    private ReportingService reportService;
    @Resource
    private TableReportService tableReportService;
    @Resource
    private ChartReportService chartReportService;

    @OpLog(name = "预览报表")
    @GetMapping(value = {"/uid/{uid}"})
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_PREVIEW"})
    public ModelAndView preview(@PathVariable final String uid) {
        final ModelAndView modelAndView = new ModelAndView("modules/report/display");
        modelAndView.addObject("report", ReportUtils.getReportMetaData(uid));
        return modelAndView;
    }

    @OpLog(name = "预览报表")
    @RequestMapping(value = {"/{type}/uid/{uid}"})
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_PREVIEW"})
    public ModelAndView preview(@PathVariable final String type, @PathVariable final String uid,
                                final String theme, final Boolean isRenderByForm, final String uiStyle,
                                final HttpServletRequest request) {
        final String typeName = StringUtils.equalsIgnoreCase("chart", type) ? "chart" : "table";
        final String themeName = StringUtils.isBlank(theme) ? "default" : theme;
        final String viewName = String.format("modules/report/themes/%s/%s", themeName, typeName);
        final ModelAndView modelAndView = new ModelAndView(viewName);
        try {
            if (BooleanUtils.isTrue(isRenderByForm)) {
                ReportUtils.renderByFormMap(uid, modelAndView, request);
            } else {
                final QueryParamFormView formView = StringUtils.equalsIgnoreCase("bootstrap", uiStyle)
                        ? new BootstrapQueryFormView() : new EasyUIQueryFormView();
                ReportUtils.renderByTemplate(uid, modelAndView, formView, request);
            }
        } catch (final QueryParamsException | TemplatePraseException ex) {
            modelAndView.addObject("formHtmlText", ex.getMessage());
            log.error("查询参数生成失败", ex);
        } catch (final Exception ex) {
            modelAndView.addObject("formHtmlText", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }
        return modelAndView;
    }

    @OpLog(name = "获取报表DataSet JSON格式数据")
    @ResponseBody
    @RequestMapping(value = "/getDataSet.json")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_PREVIEW"})
    public ResponseResult getDataSet(final String uid, final HttpServletRequest request) {
        ResponseResult result;
        try {
            final Reporting po = this.reportService.getByUid(uid);
            final ReportingOptions options = this.reportService.parseOptions(po.getOptions());

            final Map<String, Object> formParameters =
                    this.tableReportService.getFormParameters(request.getParameterMap(), options.getDataRange());
            result = ResultUtils.success(this.tableReportService.getReportDataSet(po, formParameters));
        } catch (final QueryParamsException |
                NotFoundLayoutColumnException |
                SQLQueryException |
                TemplatePraseException ex) {
            log.error("报表生成失败", ex);
            result = ResultUtils.failure(10007, "报表生成失败");
        } catch (final Exception ex) {
            log.error("报表系统出错", ex);
            result = ResultUtils.failure(10008, "报表系统出错");
        }
        return result;
    }

    @OpLog(name = "获取表格报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/table/getData.json")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_PREVIEW"})
    public ResponseResult getTableData(final String uid, final HttpServletRequest request) {
        final JSONObject data = new JSONObject();
        try {
            ReportUtils.generate(uid, data, request);
        } catch (final QueryParamsException |
                NotFoundLayoutColumnException |
                SQLQueryException |
                TemplatePraseException ex) {
            data.put("htmlTable", ex.getMessage());
            log.error("报表生成失败", ex);
        } catch (final Exception ex) {
            data.put("htmlTable", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }

        return ResultUtils.success(data);
    }

    @OpLog(name = "获取图表报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/chart/getData.json")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_PREVIEW"})
    public ResponseResult getChartData(final String uid, final HttpServletRequest request) {
        final JSONObject data = ReportUtils.getDefaultChartData();
        if (StringUtils.isNotBlank(uid)) {
            try {
                final Reporting po = this.reportService.getByUid(uid);
                final ReportingOptions options = this.reportService.parseOptions(po.getOptions());

                final Map<String, Object> formParameters =
                        this.tableReportService.getFormParameters(request.getParameterMap(), options.getDataRange());

                final ReportDataSet reportDataSet = this.tableReportService.getReportDataSet(po, formParameters);
                data.put("dimColumnMap", this.chartReportService.getDimColumnMap(reportDataSet));
                data.put("dimColumns", this.chartReportService.getDimColumns(reportDataSet));
                data.put("statColumns", this.chartReportService.getStatColumns(reportDataSet));
                data.put("dataRows", this.chartReportService.getDataRows(reportDataSet));
            } catch (final QueryParamsException |
                    NotFoundLayoutColumnException |
                    SQLQueryException |
                    TemplatePraseException ex) {
                data.put("msg", ex.getMessage());
                log.error("报表生成失败", ex);
            } catch (final Exception ex) {
                data.put("msg", "报表系统错误:" + ex.getMessage());
                log.error("报表系统出错", ex);
            }
        }
        return ResultUtils.success(data);
    }

    @PostMapping(value = "/table/exportExcel")
    @OpLog(name = "导出报表为Excel")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DESIGNER_EXPORT"})
    public void exportToExcel(final String uid, final String name, final String htmlText,
                              final HttpServletRequest request, final HttpServletResponse response) {
        try {
            ReportUtils.exportToExcel(uid, name, htmlText, request, response);
        } catch (final Exception ex) {
            log.error("导出Excel失败", ex);
        }
    }
}