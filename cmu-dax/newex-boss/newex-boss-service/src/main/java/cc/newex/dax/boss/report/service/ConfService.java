package cc.newex.dax.boss.report.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.report.engine.data.ReportMetaDataColumn;
import cc.newex.dax.boss.report.criteria.ConfExample;
import cc.newex.dax.boss.report.domain.Conf;

import java.util.List;
import java.util.Map;

/**
 * 报表元数据配置字典表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface ConfService
        extends CrudService<Conf, ConfExample, Integer> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Conf> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param parentId
     * @return
     */
    List<Conf> getByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<Conf> getByParentKey(String key);

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonColumns();

    /**
     * @return
     */
    Map<String, ReportMetaDataColumn> getCommonOptionalColumns();
}
