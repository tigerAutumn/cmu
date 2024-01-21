package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.report.engine.data.ColumnType;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.dax.boss.report.criteria.ConfExample;
import cc.newex.dax.boss.report.data.ConfRepository;
import cc.newex.dax.boss.report.domain.Conf;
import cc.newex.dax.boss.report.service.ConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表元数据配置字典表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class ConfServiceImpl
        extends AbstractCrudService<ConfRepository, Conf, ConfExample, Integer>
        implements ConfService {

    /**
     * 统计列对应的配置字典表中的Key
     */
    private static final String STAT_COLUMN = "statColumn";
    /**
     * 日期列对应的配置字典表中的Key
     */
    private static final String DATE_COLUMN = "dateColumn";
    /**
     * 常见维度对应的配置字典表中的Key
     */
    private static final String DIM_COLUMN = "dimColumn";
    /**
     * 常见可选列对应的配置字典表中的Key
     */
    private static final String OPTION_COLUMN = "optionalColumn";

    @Autowired
    private ConfRepository confRepos;

    @Override
    protected ConfExample getPageExample(final String fieldName, final String keyword) {
        final ConfExample example = new ConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public List<Conf> getByPage(final PageInfo pageInfo, final Integer pid) {
        final ConfExample example = new ConfExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Conf> getByParentId(final Integer parentId) {
        return this.confRepos.selectByParentId(parentId);
    }

    @Override
    public List<Conf> getByParentKey(final String key) {
        return this.confRepos.selectByParentKey(key);
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonColumns() {
        final Map<String, ReportMetaDataColumn> commonColumnMap =
                this.listToMap(this.getByParentKey(STAT_COLUMN), ColumnType.STATISTICAL, false);
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DATE_COLUMN), ColumnType.LAYOUT, false));
        commonColumnMap.putAll(this.listToMap(this.getByParentKey(DIM_COLUMN), ColumnType.DIMENSION, false));
        return commonColumnMap;
    }

    @Override
    public Map<String, ReportMetaDataColumn> getCommonOptionalColumns() {
        return this.listToMap(this.getByParentKey(OPTION_COLUMN), ColumnType.STATISTICAL, true);
    }

    private Map<String, ReportMetaDataColumn> listToMap(final List<Conf> confItems, final ColumnType type,
                                                        final boolean isOptional) {
        if (CollectionUtils.isEmpty(confItems)) {
            return new HashMap<>(0);
        }

        final Map<String, ReportMetaDataColumn> optionalColumnMap = new HashMap<>(confItems.size());
        for (final Conf confItem : confItems) {
            final String key = confItem.getCode().trim().toLowerCase();
            if (!optionalColumnMap.containsKey(key)) {
                final ReportMetaDataColumn metaDataColumn =
                        new ReportMetaDataColumn(confItem.getCode(), confItem.getValue(), type);
                metaDataColumn.setOptional(isOptional);
                optionalColumnMap.put(key, metaDataColumn);
            }
        }
        return optionalColumnMap;
    }
}