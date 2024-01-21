package cc.newex.dax.boss.report.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.report.engine.data.ReportDataSource;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.commons.report.engine.data.ReportQueryParamItem;
import cc.newex.dax.boss.report.criteria.ReportingExample;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.options.QueryParameterOptions;
import cc.newex.dax.boss.report.model.options.ReportingOptions;

import java.util.List;

/**
 * 报表信息表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface ReportingService
        extends CrudService<Reporting, ReportingExample, Integer> {
    /**
     * @param page
     * @param fieldName
     * @param categoryId
     * @return
     */
    List<Reporting> getByPageWithCategory(PageInfo page, String fieldName, Integer categoryId);

    /**
     * 分页查询
     *
     * @param pageInfo  分页参数
     * @param fieldName where 筛选字段名
     * @param keyword   where 筛选字段模糊匹配关键字
     * @return 分页记录列表
     */
    List<Reporting> getByPageWithCategory(PageInfo pageInfo, String fieldName, String keyword);

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @param example  where条件参数
     * @return 分页记录列表
     */
    List<Reporting> getByPageWithCategory(PageInfo pageInfo, ReportingExample example);

    /**
     * @param id
     * @param json
     * @return
     */
    boolean saveQueryParams(int id, String json);

    /**
     * @param uid
     * @return
     */
    Reporting getByUid(String uid);

    /**
     * @param id
     * @return
     */
    String getSqlText(int id);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportMetaDataColumn> getMetaDataColumns(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     */
    void explainSqlText(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportMetaDataColumn> executeSqlText(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportQueryParamItem> executeQueryParamSqlText(int dsId, String sqlText);

    /**
     * 解析json格式的报表元数据列为ReportMetaDataColumn对象集合
     *
     * @return List<ReportMetaDataColumn>
     */
    List<ReportMetaDataColumn> parseMetaColumns(String json);

    /**
     * 解析json格式的报表查询参数为QueryParameter对象集合
     *
     * @return List<QueryParameterOptions>
     */
    List<QueryParameterOptions> parseQueryParams(String json);

    /**
     * 解析json格式的报表选项ReportOptions
     *
     * @return ReportOptions
     */
    ReportingOptions parseOptions(String json);

    /**
     * @param dsId
     * @return
     */
    ReportDataSource getReportDataSource(int dsId);
}
