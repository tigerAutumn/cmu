package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.PreLiquidateAlertExample;
import cc.newex.dax.perpetual.data.PreLiquidateAlertRepository;
import cc.newex.dax.perpetual.domain.PreLiquidateAlert;
import cc.newex.dax.perpetual.service.PreLiquidateAlertService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 风险告警表 服务实现
 *
 * @author newex-team
 * @date 2018-11-14 12:09:15
 */
@Slf4j
@Service
public class PreLiquidateAlertServiceImpl extends AbstractCrudService<PreLiquidateAlertRepository, PreLiquidateAlert, PreLiquidateAlertExample, Long> implements PreLiquidateAlertService {
    @Autowired
    private PreLiquidateAlertRepository preLiquidateAlertRepository;

    @Override
    protected PreLiquidateAlertExample getPageExample(final String fieldName, final String keyword) {
        final PreLiquidateAlertExample example = new PreLiquidateAlertExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public int batchInsertOnDuplicateKeyDoNothing(final List<PreLiquidateAlert> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return this.preLiquidateAlertRepository.batchInsertOnDuplicateKeyDoNothing(list);
    }

    @Override
    public List<PreLiquidateAlert> alertList(final long index, final int size, final String contractCode) {

        final PreLiquidateAlertExample example = new PreLiquidateAlertExample();
        final PreLiquidateAlertExample.Criteria criteria = example.createCriteria();
        criteria.andTimesEqualTo(0);
        criteria.andIdGreaterThan(index);
        criteria.andContractCodeEqualTo(contractCode);

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(size);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);

        return this.getByPage(pageInfo, example);
    }

    @Override
    public void removeExpireList(final String contractCode) {
        final PreLiquidateAlertExample example = new PreLiquidateAlertExample();
        final PreLiquidateAlertExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contractCode);
        criteria.andTimesGreaterThan(0);
        criteria.andExpireTimeLessThanOrEqualTo(new Date());
        this.removeByExample(example);
    }
}