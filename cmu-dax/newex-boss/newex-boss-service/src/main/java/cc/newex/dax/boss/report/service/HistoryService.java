package cc.newex.dax.boss.report.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.report.criteria.HistoryExample;
import cc.newex.dax.boss.report.domain.History;

import java.util.List;

/**
 * 报表历史信息表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface HistoryService
        extends CrudService<History, HistoryExample, Integer> {
    /**
     * @param page
     * @param reportId
     * @param fieldName
     * @param keyword
     * @return
     */
    List<History> getByPage(PageInfo page, int reportId, String fieldName, String keyword);
}
