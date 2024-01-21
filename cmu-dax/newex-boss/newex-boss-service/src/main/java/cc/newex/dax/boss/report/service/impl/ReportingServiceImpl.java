package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.report.engine.data.ReportDataSource;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.commons.report.engine.data.ReportQueryParamItem;
import cc.newex.commons.report.engine.query.QueryerFactory;
import cc.newex.dax.boss.report.criteria.ReportingExample;
import cc.newex.dax.boss.report.data.ReportingRepository;
import cc.newex.dax.boss.report.domain.DataSource;
import cc.newex.dax.boss.report.domain.Reporting;
import cc.newex.dax.boss.report.model.options.QueryParameterOptions;
import cc.newex.dax.boss.report.model.options.ReportingOptions;
import cc.newex.dax.boss.report.service.ConfService;
import cc.newex.dax.boss.report.service.DataSourceService;
import cc.newex.dax.boss.report.service.ReportingService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表信息表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class ReportingServiceImpl
        extends AbstractCrudService<ReportingRepository, Reporting, ReportingExample, Integer>
        implements ReportingService {
    @Resource
    private DataSourceService dsService;
    @Resource
    private ConfService confService;
    @Resource
    private ReportingRepository reportingRepos;

    @Override
    protected ReportingExample getPageExample(final String fieldName, final String keyword) {
        final ReportingExample example = new ReportingExample();
        example.createCriteria()
                .andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Reporting> getByPageWithCategory(final PageInfo page, final String fieldName, final Integer categoryId) {
        final ReportingExample example = new ReportingExample();
        example.createCriteria()
                .andFieldEqualTo(fieldName, categoryId);
        return this.getByPageWithCategory(page, example);
    }

    @Override
    public List<Reporting> getByPageWithCategory(PageInfo pageInfo, String fieldName, String keyword) {
        if (StringUtils.isBlank(fieldName)) {
            return this.getByPageWithCategory(pageInfo, null);
        }
        return this.getByPageWithCategory(pageInfo, this.getPageExample(fieldName, keyword));
    }

    @Override
    public List<Reporting> getByPageWithCategory(PageInfo pageInfo, ReportingExample example) {
        pageInfo.setTotals(this.reportingRepos.countByPagerWithCategory(pageInfo, example));
        return this.reportingRepos.selectByPagerWithCategory(pageInfo, example);
    }

    @Override
    public boolean saveQueryParams(final int id, final String json) {
        final Reporting po = Reporting.builder().id(id).queryParams(json).build();
        return this.reportingRepos.updateById(po) > 0;
    }

    @Override
    public Reporting getByUid(final String uid) {
        final ReportingExample example = new ReportingExample();
        example.createCriteria()
                .andUidEqualTo(uid);
        return this.reportingRepos.selectOneByExample(example);
    }

    @Override
    public String getSqlText(final int id) {
        final Reporting po = this.reportingRepos.selectById(id);
        return po == null ? "" : po.getSqlText();
    }

    @Override
    public List<ReportMetaDataColumn> getMetaDataColumns(final int dsId, final String sqlText) {
        final List<ReportMetaDataColumn> metaDataColumns = this.executeSqlText(dsId, sqlText);
        final Map<String, ReportMetaDataColumn> commonColumnMap = this.confService.getCommonColumns();
        final Map<String, ReportMetaDataColumn> commonOptionColumnMap = this.confService.getCommonOptionalColumns();

        for (final ReportMetaDataColumn column : metaDataColumns) {
            final String columnName = column.getName();
            if (commonColumnMap.containsKey(columnName)) {
                final ReportMetaDataColumn commonColumn = commonColumnMap.get(columnName);
                column.setType(commonColumn.getType().getValue());
                column.setText(commonColumn.getText());
            }
            if (commonOptionColumnMap.containsKey(columnName)) {
                column.setOptional(true);
                column.setType(commonOptionColumnMap.get(columnName).getType().getValue());
            }
        }
        return metaDataColumns;
    }

    @Override
    public void explainSqlText(final int dsId, final String sqlText) {
        this.executeSqlText(dsId, sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> executeSqlText(final int dsId, final String sqlText) {
        final ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseMetaDataColumns(sqlText);
    }

    @Override
    public List<ReportQueryParamItem> executeQueryParamSqlText(final int dsId, final String sqlText) {
        final ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseQueryParamItems(sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> parseMetaColumns(final String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, ReportMetaDataColumn.class);
    }

    @Override
    public List<QueryParameterOptions> parseQueryParams(final String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, QueryParameterOptions.class);
    }

    @Override
    public ReportingOptions parseOptions(final String json) {
        if (StringUtils.isBlank(json)) {
            return ReportingOptions.builder().dataRange(7).layout(1).statColumnLayout(1).build();
        }
        return JSON.parseObject(json, ReportingOptions.class);
    }

    @Override
    public ReportDataSource getReportDataSource(final int dsId) {
        final DataSource ds = this.dsService.getById(dsId);
        Map<String, Object> options = new HashMap<>(3);
        if (StringUtils.isNotEmpty(ds.getOptions())) {
            options = JSON.parseObject(ds.getOptions());
        }
        return new ReportDataSource(
                ds.getUid(),
                ds.getDriverClass(),
                ds.getJdbcUrl(), ds.getUser(), ds.getPassword(),
                ds.getQueryerClass(),
                ds.getPoolClass(),
                options);
    }
}