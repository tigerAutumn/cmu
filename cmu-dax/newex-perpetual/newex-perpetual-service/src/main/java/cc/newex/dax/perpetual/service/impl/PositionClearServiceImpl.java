package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.enums.PositionClearEnum;
import cc.newex.dax.perpetual.criteria.PositionClearExample;
import cc.newex.dax.perpetual.data.PositionClearRepository;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.service.PositionClearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户持仓清算流水表 服务实现
 *
 * @author newex-team
 * @date 2018-12-07 18:40:15
 */
@Slf4j
@Service
public class PositionClearServiceImpl extends AbstractCrudService<PositionClearRepository, PositionClear, PositionClearExample, Long> implements PositionClearService {
    @Autowired
    private PositionClearRepository positionClearRepository;

    @Override
    protected PositionClearExample getPageExample(final String fieldName, final String keyword) {
        final PositionClearExample example = new PositionClearExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<PositionClear> getPositionClear(final Integer brokerId, final Long userId, final String contractCode, PageInfo pageInfo) {
        final PositionClearExample example = new PositionClearExample();
        final PositionClearExample.Criteria criteria = example.createCriteria();
        criteria.andBrokerIdEqualTo(brokerId);
        criteria.andUserIdEqualTo(userId);
        criteria.andContractCodeEqualTo(contractCode);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<PositionClear> getPositionClear(final Integer brokerId, final Long contractId) {
        final PositionClearExample example = new PositionClearExample();
        final PositionClearExample.Criteria criteria = example.createCriteria();
        criteria.andBrokerIdEqualTo(brokerId);
        criteria.andContractIdEqualTo(contractId);
        return this.getByExample(example);
    }

    @Override
    public List<PositionClear> getPositionClear(final String contractCode, final PositionClearEnum status, final Long startId, final int size) {
        final PositionClearExample example = new PositionClearExample();
        final PositionClearExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contractCode);
        criteria.andStatusEqualTo(status.getCode());
        criteria.andIdGreaterThan(startId);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(size);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public void batchUpdateStatus(final List<Long> ids, final PositionClearEnum status) {
        final PositionClearExample example = new PositionClearExample();
        final PositionClearExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        final PositionClear record = PositionClear.builder().status(status.getCode()).build();
        this.editByExample(record, example);
    }
}