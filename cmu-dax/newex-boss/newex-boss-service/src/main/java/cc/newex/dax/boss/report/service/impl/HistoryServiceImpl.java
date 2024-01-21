package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.report.criteria.HistoryExample;
import cc.newex.dax.boss.report.data.HistoryRepository;
import cc.newex.dax.boss.report.domain.History;
import cc.newex.dax.boss.report.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报表历史信息表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class HistoryServiceImpl
        extends AbstractCrudService<HistoryRepository, History, HistoryExample, Integer>
        implements HistoryService {

    @Autowired
    private HistoryRepository historyRepos;

    @Override
    protected HistoryExample getPageExample(final String fieldName, final String keyword) {
        final HistoryExample example = new HistoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<History> getByPage(final PageInfo page, final int reportId, final String fieldName,
                                         final String keyword) {
        final HistoryExample example = new HistoryExample();
        final HistoryExample.Criteria criteria = example.or().andReportIdEqualTo(reportId);
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }

        return this.getByPage(page, example);
    }
}