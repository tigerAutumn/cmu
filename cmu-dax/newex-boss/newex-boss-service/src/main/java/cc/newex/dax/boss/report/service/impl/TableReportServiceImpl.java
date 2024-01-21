package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.report.engine.ReportGenerator;
import cc.newex.commons.report.engine.data.ColumnType;
import cc.newex.commons.report.engine.data.ReportDataSet;
import cc.newex.commons.report.engine.data.ReportDataSource;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.commons.report.engine.data.ReportMetaDataSet;
import cc.newex.commons.report.engine.data.ReportParameter;
import cc.newex.commons.report.engine.data.ReportQueryParamItem;
import cc.newex.commons.report.engine.data.ReportSqlTemplate;
import cc.newex.commons.report.engine.data.ReportTable;
import cc.newex.commons.report.engine.query.Queryer;
import cc.newex.commons.report.engine.util.DateUtils;
import cc.newex.commons.report.engine.util.VelocityUtils;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.ReportingConsts;
import cc.newex.dax.boss.report.model.control.HtmlCheckBox;
import cc.newex.dax.boss.report.model.control.HtmlCheckBoxList;
import cc.newex.dax.boss.report.model.control.HtmlComboBox;
import cc.newex.dax.boss.report.model.control.HtmlDateBox;
import cc.newex.dax.boss.report.model.control.HtmlFormElement;
import cc.newex.dax.boss.report.model.control.HtmlSelectOption;
import cc.newex.dax.boss.report.model.control.HtmlTextBox;
import cc.newex.dax.boss.report.model.options.QueryParameterOptions;
import cc.newex.dax.boss.report.model.options.ReportingOptions;
import cc.newex.dax.boss.report.service.ReportingService;
import cc.newex.dax.boss.report.service.TableReportService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 报表生成服务类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Service("tableReportService")
public class TableReportServiceImpl implements TableReportService {
    @Resource
    private ReportingService reportService;

    @Override
    public ReportParameter getReportParameter(final Reporting report, final Map<?, ?> parameters) {
        final ReportingOptions options = this.reportService.parseOptions(report.getOptions());
        final Map<String, Object> formParams = this.getFormParameters(parameters, options.getDataRange());
        return this.createReportParameter(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final int id, final Map<String, Object> formParams) {
        final Reporting report = this.reportService.getById(id);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final String uid, final Map<String, Object> formParams) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final Reporting report, final Map<String, Object> formParams) {
        final ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.generate(reportDs, this.createReportParameter(report, formParams));
    }

    @Override
    public ReportTable getReportTable(final Queryer queryer, final ReportParameter reportParameter) {
        return ReportGenerator.generate(queryer, reportParameter);
    }

    @Override
    public ReportTable getReportTable(final ReportMetaDataSet metaDataSet, final ReportParameter reportParameter) {
        return ReportGenerator.generate(metaDataSet, reportParameter);
    }

    @Override
    public ReportDataSet getReportDataSet(final Reporting report, final Map<String, Object> parameters) {
        final ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.getDataSet(reportDs, this.createReportParameter(report, parameters));
    }

    private ReportParameter createReportParameter(final Reporting report, final Map<String, Object> formParams) {
        final String sqlText = new ReportSqlTemplate(report.getSqlText(), formParams).execute();
        final Set<String> enabledStatColumn = this.getEnabledStatColumns(formParams);
        final ReportingOptions options = this.reportService.parseOptions(report.getOptions());
        final List<ReportMetaDataColumn> metaColumns = this.reportService.parseMetaColumns(report.getMetaColumns());
        return new ReportParameter(report.getId().toString(), report.getName(),
                options.getLayout(), options.getStatColumnLayout(), metaColumns,
                enabledStatColumn, Boolean.valueOf(formParams.get(ReportingConsts.IS_ROW_SPAN).toString()), sqlText);
    }

    private Set<String> getEnabledStatColumns(final Map<String, Object> formParams) {
        final Set<String> checkedSet = new HashSet<>();
        final String checkedColumnNames = formParams.get(ReportingConsts.STAT_COLUMNS).toString();
        if (StringUtils.isBlank(checkedColumnNames)) {
            return checkedSet;
        }
        final String[] columnNames = StringUtils.split(checkedColumnNames, ',');
        checkedSet.addAll(Arrays.asList(columnNames));
        return checkedSet;
    }

    @Override
    public Map<String, Object> getBuildInParameters(final Map<?, ?> httpReqParamMap, final int dataRange) {
        final Map<String, Object> formParams = new HashMap<>(30);
        this.setBuildInParams(formParams, httpReqParamMap, dataRange);
        return formParams;
    }

    @Override
    public Map<String, Object> getFormParameters(final Map<?, ?> httpReqParamMap) {
        return this.getFormParameters(httpReqParamMap, 7);
    }

    @Override
    public Map<String, Object> getFormParameters(final Map<?, ?> httpReqParamMap, final int dataRange) {
        final Map<String, Object> formParams = new HashMap<>();
        this.setBuildInParams(formParams, httpReqParamMap, 7);
        this.setQueryParams(formParams, httpReqParamMap);
        return formParams;
    }

    private void setBuildInParams(final Map<String, Object> formParams, final Map<?, ?> httpReqParamMap,
                                  int dataRange) {
        dataRange = (dataRange - 1) < 0 ? 0 : dataRange - 1;
        // 判断是否设置报表开始时间与结束时期
        if (httpReqParamMap.containsKey(ReportingConsts.START_TIME)) {
            final String[] values = (String[]) httpReqParamMap.get(ReportingConsts.START_TIME);
            formParams.put(ReportingConsts.START_TIME, values[0]);
        } else {
            formParams.put(ReportingConsts.START_TIME, DateUtils.add(-dataRange, ReportingConsts.DATE_YYYY_MM_DD));
        }
        if (httpReqParamMap.containsKey(ReportingConsts.END_TIME)) {
            final String[] values = (String[]) httpReqParamMap.get(ReportingConsts.END_TIME);
            formParams.put(ReportingConsts.END_TIME, values[0]);
        } else {
            formParams.put(ReportingConsts.END_TIME, DateUtils.getNow(ReportingConsts.DATE_YYYY_MM_DD));
        }
        // 判断是否设置报表统计列
        if (httpReqParamMap.containsKey(ReportingConsts.STAT_COLUMNS)) {
            final String[] values = (String[]) httpReqParamMap.get(ReportingConsts.STAT_COLUMNS);
            formParams.put(ReportingConsts.STAT_COLUMNS, StringUtils.join(values, ','));
        } else {
            formParams.put(ReportingConsts.STAT_COLUMNS, "");
        }
        // 判断是否设置报表表格行跨行显示
        if (httpReqParamMap.containsKey(ReportingConsts.IS_ROW_SPAN)) {
            final String[] values = (String[]) httpReqParamMap.get(ReportingConsts.IS_ROW_SPAN);
            formParams.put(ReportingConsts.IS_ROW_SPAN, values[0]);
        } else {
            formParams.put(ReportingConsts.IS_ROW_SPAN, Boolean.TRUE.toString());
        }

        final String startTime = formParams.get(ReportingConsts.START_TIME).toString();
        final String endTime = formParams.get(ReportingConsts.END_TIME).toString();

        formParams.put(ReportingConsts.INT_START_TIME,
                Integer.valueOf(DateUtils.getDate(startTime, ReportingConsts.DATE_YYYYMMDD)));
        formParams.put(ReportingConsts.UTC_START_TIME,
                DateUtils.getUtcDate(startTime, ReportingConsts.DATE_YYYY_MM_DD));
        formParams.put(ReportingConsts.UTC_INT_START_TIME,
                Integer.valueOf(DateUtils.getUtcDate(startTime, ReportingConsts.DATE_YYYYMMDD)));
        formParams.put(ReportingConsts.INT_END_TIME,
                Integer.valueOf(DateUtils.getDate(endTime, ReportingConsts.DATE_YYYYMMDD)));
        formParams.put(ReportingConsts.UTC_END_TIME,
                DateUtils.getUtcDate(endTime, ReportingConsts.DATE_YYYY_MM_DD));
        formParams.put(ReportingConsts.UTC_INT_END_TIME,
                Integer.valueOf(DateUtils.getUtcDate(endTime, ReportingConsts.DATE_YYYYMMDD)));
    }

    private void setQueryParams(final Map<String, Object> formParams, final Map<?, ?> httpReqParamMap) {
        String[] values = (String[]) httpReqParamMap.get(ReportingConsts.UID);
        if (ArrayUtils.isEmpty(values) || "".equals(values[0].trim())) {
            return;
        }

        final String uid = values[0].trim();
        final Reporting report = this.reportService.getByUid(uid);
        final List<QueryParameterOptions> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        for (final QueryParameterOptions queryParam : queryParams) {
            String value = "";
            values = (String[]) httpReqParamMap.get(queryParam.getName());
            if (values != null && values.length > 0) {
                value = this.getQueryParamValue(queryParam.getDataType(), values);
            }
            formParams.put(queryParam.getName(), value);
        }
    }

    private String getQueryParamValue(final String dataType, final String[] values) {
        if (values.length == 1) {
            return values[0];
        }
        if (ReportingConsts.FLOAT.equals(dataType) ||
                ReportingConsts.INTEGER.equals(dataType)) {
            return StringUtils.join(values, ",");
        }
        return StringUtils.join(values, "','");
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final String uid, final Map<String, Object> buildInParams,
                                                          final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getFormElementMap(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final int id, final Map<String, Object> buildInParams,
                                                          final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getById(id);
        return this.getFormElementMap(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final Reporting report, final Map<String, Object> buildInParams,
                                                          final int minDisplayedStatColumn) {
        final List<HtmlFormElement> formElements = this.getFormElements(report, buildInParams, minDisplayedStatColumn);
        final Map<String, HtmlFormElement> formElementMap = new HashMap<>(formElements.size());
        for (final HtmlFormElement element : formElements) {
            formElementMap.put(element.getName(), element);
        }
        return formElementMap;
    }

    @Override
    public List<HtmlFormElement> getFormElements(final String uid, final Map<String, Object> buildInParams,
                                                 final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(final int id, final Map<String, Object> buildInParams,
                                                 final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getById(id);
        return this.getFormElements(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(final Reporting report, final Map<String, Object> buildInParams,
                                                 final int minDisplayedStatColumn) {
        final List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildInParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildInParams));
        formElements.add(this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn));
        return formElements;
    }

    @Override
    public List<HtmlFormElement> getDateAndQueryParamFormElements(final Reporting report,
                                                                  final Map<String, Object> buildInParams) {
        final List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildInParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildInParams));
        return formElements;
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final String uid, final Map<String, Object> buildInParams) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getDateFormElements(report, buildInParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final int id, final Map<String, Object> buildInParams) {
        final Reporting report = this.reportService.getById(id);
        return this.getDateFormElements(report, buildInParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final Reporting report, final Map<String, Object> buildInParams) {
        final StringBuilder text = new StringBuilder(report.getSqlText());
        text.append(" ");
        text.append(report.getQueryParams());

        final String regex = "\\$\\{.*?\\}";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(text.toString());
        final Set<String> set = new HashSet<>(2);
        while (matcher.find()) {
            final String group = matcher.group(0);
            String name = group.replaceAll("utc|int|Int|[\\$\\{\\}]", "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (!set.contains(name) && StringUtils.indexOfIgnoreCase(group, name) != -1) {
                set.add(name);
            }
        }

        final List<HtmlDateBox> dateboxes = new ArrayList<>(2);
        String name = ReportingConsts.START_TIME;
        if (set.contains(name)) {
            dateboxes.add(new HtmlDateBox(name, "开始日期", buildInParams.get(name).toString()));
        }
        name = ReportingConsts.END_TIME;
        if (set.contains(name)) {
            dateboxes.add(new HtmlDateBox(name, "结束日期", buildInParams.get(name).toString()));
        }
        return dateboxes;
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(final String uid, final Map<String, Object> buildInParams,
                                                           final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(final int id, final Map<String, Object> buildInParams,
                                                           final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getById(id);
        return this.getFormElements(report, buildInParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(final Reporting report,
                                                           final Map<String, Object> buildInParams) {
        final List<QueryParameterOptions> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        final List<HtmlFormElement> formElements = new ArrayList<>(3);

        for (final QueryParameterOptions queryParam : queryParams) {
            HtmlFormElement htmlFormElement = null;
            queryParam.setDefaultText(VelocityUtils.parse(queryParam.getDefaultText(), buildInParams));
            queryParam.setDefaultValue(VelocityUtils.parse(queryParam.getDefaultValue(), buildInParams));
            queryParam.setContent(VelocityUtils.parse(queryParam.getContent(), buildInParams));
            final String formElement = queryParam.getFormElement().toLowerCase();
            if ("select".equals(formElement) || "selectMul".equalsIgnoreCase(formElement)) {
                htmlFormElement = this.getComboBoxFormElements(queryParam, report.getDsId(), buildInParams);
            } else if ("checkbox".equals(formElement)) {
                htmlFormElement = new HtmlCheckBox(queryParam.getName(), queryParam.getText(),
                        queryParam.getRealDefaultValue());
            } else if ("text".equals(formElement)) {
                htmlFormElement = new HtmlTextBox(queryParam.getName(), queryParam.getText(),
                        queryParam.getRealDefaultValue());
            } else if ("date".equals(formElement)) {
                htmlFormElement = new HtmlDateBox(queryParam.getName(), queryParam.getText(),
                        queryParam.getRealDefaultValue());
            }
            if (htmlFormElement != null) {
                this.setElementCommonProperties(queryParam, htmlFormElement);
                formElements.add(htmlFormElement);
            }
        }
        return formElements;
    }

    private HtmlComboBox getComboBoxFormElements(final QueryParameterOptions queryParam, final int dsId,
                                                 final Map<String, Object> buildInParams) {
        final List<ReportQueryParamItem> options = this.getOptions(queryParam, dsId, buildInParams);
        final List<HtmlSelectOption> htmlSelectOptions = new ArrayList<>(options.size());

        if (queryParam.hasDefaultValue()) {
            htmlSelectOptions.add(new HtmlSelectOption(
                    queryParam.getDefaultText(),
                    queryParam.getDefaultValue(), true));
        }
        for (int i = 0; i < options.size(); i++) {
            final ReportQueryParamItem option = options.get(i);
            if (!option.getName().equals(queryParam.getDefaultValue())) {
                htmlSelectOptions.add(new HtmlSelectOption(option.getText(),
                        option.getName(), (!queryParam.hasDefaultValue() && i == 0)));
            }
        }

        final HtmlComboBox htmlComboBox = new HtmlComboBox(queryParam.getName(), queryParam.getText(),
                htmlSelectOptions);
        htmlComboBox.setMultipled("selectMul".equals(queryParam.getFormElement()));
        htmlComboBox.setAutoComplete(queryParam.isAutoComplete());
        return htmlComboBox;
    }

    private void setElementCommonProperties(final QueryParameterOptions queryParam,
                                             final HtmlFormElement htmlFormElement) {
        htmlFormElement.setDataType(queryParam.getDataType());
        htmlFormElement.setHeight(queryParam.getHeight());
        htmlFormElement.setWidth(queryParam.getWidth());
        htmlFormElement.setRequired(queryParam.isRequired());
        htmlFormElement.setDefaultText(queryParam.getRealDefaultText());
        htmlFormElement.setDefaultValue(queryParam.getRealDefaultValue());
        htmlFormElement.setComment(queryParam.getComment());
    }

    private List<ReportQueryParamItem> getOptions(final QueryParameterOptions queryParam, final int dsId,
                                                  final Map<String, Object> buildInParams) {
        if (ReportingConsts.SQL.equals(queryParam.getDataSource())) {
            return this.reportService.executeQueryParamSqlText(dsId, queryParam.getContent());
        }

        final List<ReportQueryParamItem> options = new ArrayList<>();
        if (ReportingConsts.TEXT.equals(queryParam.getDataSource()) &&
                StringUtils.isNoneBlank(queryParam.getContent())) {
            final HashSet<String> set = new HashSet<>();
            final String[] optionSplits = StringUtils.split(queryParam.getContent(), '|');
            for (final String option : optionSplits) {
                final String[] nameValuePairs = StringUtils.split(option, ',');
                final String name = nameValuePairs[0];
                final String text = nameValuePairs.length > 1 ? nameValuePairs[1] : name;
                if (!set.contains(name)) {
                    set.add(name);
                    options.add(new ReportQueryParamItem(name, text));
                }
            }
        }
        return options;
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final String uid, final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getByUid(uid);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final int id, final int minDisplayedStatColumn) {
        final Reporting report = this.reportService.getById(id);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final List<ReportMetaDataColumn> columns,
                                                      final int minDisplayedStatColumn) {
        final List<ReportMetaDataColumn> statColumns = columns.stream()
                .filter(column -> column.getType() == ColumnType.STATISTICAL ||
                        column.getType() == ColumnType.COMPUTED)
                .collect(Collectors.toList());
        if (statColumns.size() <= minDisplayedStatColumn) {
            return null;
        }

        final List<HtmlCheckBox> checkBoxes = new ArrayList<>(statColumns.size());
        for (final ReportMetaDataColumn column : statColumns) {
            final HtmlCheckBox checkbox = new HtmlCheckBox(column.getName(), column.getText(), column.getName());
            checkbox.setChecked(!column.isOptional());
            checkBoxes.add(checkbox);
        }
        return new HtmlCheckBoxList(ReportingConsts.STAT_COLUMNS, "统计列", checkBoxes);
    }

    @Override
    public List<HtmlFormElement> getNonStatColumnFormElements(final List<ReportMetaDataColumn> columns) {
        final List<HtmlFormElement> formElements = new ArrayList<>(10);
        columns.stream()
                .filter(column -> column.getType() == ColumnType.LAYOUT ||
                        column.getType() == ColumnType.DIMENSION)
                .forEach(column -> {
                    final HtmlComboBox htmlComboBox = new HtmlComboBox("dim_" + column.getName(),
                            column.getText(), new ArrayList<>(0));
                    htmlComboBox.setAutoComplete(true);
                    formElements.add(htmlComboBox);
                });
        return formElements;
    }
}