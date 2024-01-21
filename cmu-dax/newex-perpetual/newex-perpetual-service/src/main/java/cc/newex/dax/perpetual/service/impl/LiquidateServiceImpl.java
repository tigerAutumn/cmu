package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.LiquidateExample;
import cc.newex.dax.perpetual.data.LiquidateRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Liquidate;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.LiquidateService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.common.CloseLiquidateService;
import cc.newex.dax.perpetual.service.common.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 强平表 服务实现
 *
 * @author newex-team
 * @date 2018-11-02 11:16:40
 */
@Slf4j
@Service
public class LiquidateServiceImpl
        extends AbstractCrudService<LiquidateRepository, Liquidate, LiquidateExample, Long>
        implements LiquidateService {
    @Autowired
    private LiquidateRepository liquidateRepository;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private MarketService marketService;
    @Resource(name = "liquidateCloseService")
    private CloseLiquidateService<Liquidate> closeLiquidateService;


    @Override
    protected LiquidateExample getPageExample(final String fieldName, final String keyword) {
        final LiquidateExample example = new LiquidateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Liquidate> listLiquidate(final long id, final int size, final String contractCode) {
        return this.liquidateRepository.listLiquidate(id, size, contractCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void liquidate(final Contract contract, final List<Contract> allContract,
                          final List<Liquidate> liquidates) {

        final MarkIndexPriceDTO markIndex = this.marketService.getMarkIndex(contract);
        if (markIndex == null) {
            LiquidateServiceImpl.log.error("not found market price, contractCode : {}", contract.getContractCode());
            return;
        }
        // 检查状态
        List<UserPosition> positions = this.closeLiquidateService.preCheck(contract, markIndex.getMarkPrice(), liquidates);
        if (CollectionUtils.isEmpty(positions)) {
            return;
        }
        positions = this.closeLiquidateService.lockBalanceAndCheckStatus(contract, allContract, positions, liquidates, null);
        if (CollectionUtils.isEmpty(positions)) {
            return;
        }
        this.closeLiquidateService.placeCloseOrder(contract, markIndex.getMarkPrice(), positions, liquidates);
    }

    @Override
    public int batchInsertOnDuplicateKeyDoNothing(final List<Liquidate> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return this.liquidateRepository.batchInsertOnDuplicateKeyDoNothing(list);
    }

    @Override
    public void removeInById(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        final LiquidateExample example = new LiquidateExample();
        example.createCriteria().andIdIn(ids);
        this.removeByExample(example);
    }

}
