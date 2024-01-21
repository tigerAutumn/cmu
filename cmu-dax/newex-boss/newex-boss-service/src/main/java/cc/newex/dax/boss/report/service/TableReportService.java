package cc.newex.dax.boss.report.service;


import cc.newex.commons.report.engine.data.ReportDataSet;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.commons.report.engine.data.ReportMetaDataSet;
import cc.newex.commons.report.engine.data.ReportParameter;
import cc.newex.commons.report.engine.data.ReportTable;
import cc.newex.commons.report.engine.query.Queryer;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.control.HtmlCheckBoxList;
import cc.newex.dax.boss.report.model.control.HtmlDateBox;
import cc.newex.dax.boss.report.model.control.HtmlFormElement;

import java.util.List;
import java.util.Map;

/**
 * 表格报表生成服务类,包括报表生成,转换,报表数据集处理等
 *
 * @author newex-team
 */
public interface TableReportService {
    /**
     * @param report
     * @param parameters
     * @return
     */
    ReportParameter getReportParameter(Reporting report, Map<?, ?> parameters);

    /**
     * @param id
     * @param formParams
     * @return
     */
    ReportTable getReportTable(int id, Map<String, Object> formParams);

    /**
     * @param uid
     * @param formParams
     * @return
     */
    ReportTable getReportTable(String uid, Map<String, Object> formParams);

    /**
     * @param report
     * @param formParams
     * @return
     */
    ReportTable getReportTable(Reporting report, Map<String, Object> formParams);

    /**
     * @param queryer
     * @param reportParameter
     * @return
     */
    ReportTable getReportTable(Queryer queryer, ReportParameter reportParameter);

    /**
     * @param metaDataSet
     * @param reportParameter
     * @return
     */
    ReportTable getReportTable(ReportMetaDataSet metaDataSet, ReportParameter reportParameter);

    /**
     * @param report
     * @param parameters
     * @return
     */
    ReportDataSet getReportDataSet(Reporting report, Map<String, Object> parameters);

    /**
     * @param httpReqParamMap
     * @param dataRange
     * @return
     */
    Map<String, Object> getBuildInParameters(Map<?, ?> httpReqParamMap, int dataRange);

    /**
     * @param httpReqParamMap
     * @return
     */
    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap);

    /**
     * @param httpReqParamMap
     * @param dataRange
     * @return
     */
    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap, int dataRange);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(String uid, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(int id, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(Reporting report, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(String uid, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(int id, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(Reporting report, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlFormElement> getDateAndQueryParamFormElements(Reporting report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(String uid, Map<String, Object> buildinParams);

    /**
     * @param id
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(int id, Map<String, Object> buildinParams);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(Reporting report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(String uid, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(int id, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(Reporting report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(String uid, int minDisplayedStatColumn);

    /**
     * @param id
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(int id, int minDisplayedStatColumn);

    /**
     * @param columns
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(List<ReportMetaDataColumn> columns,
                                               int minDisplayedStatColumn);

    /**
     * @param columns
     * @return
     */
    List<HtmlFormElement> getNonStatColumnFormElements(List<ReportMetaDataColumn> columns);
}
