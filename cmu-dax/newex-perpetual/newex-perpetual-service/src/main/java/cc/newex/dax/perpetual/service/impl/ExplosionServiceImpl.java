package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.criteria.ExplosionExample;
import cc.newex.dax.perpetual.data.ExplosionRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Explosion;
import cc.newex.dax.perpetual.domain.HistoryExplosion;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.ExplosionService;
import cc.newex.dax.perpetual.service.HistoryExplosionService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.CloseLiquidateService;
import cc.newex.dax.perpetual.service.common.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 爆仓 服务实现
 *
 * @author newex-team
 * @date 2018-11-02 11:19:54
 */
@Slf4j
@Service
public class ExplosionServiceImpl
        extends AbstractCrudService<ExplosionRepository, Explosion, ExplosionExample, Long>
        implements ExplosionService {
    @Autowired
    private ExplosionRepository explosionRepository;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private MarketService marketService;
    @Resource(name = "explosionCloseService")
    private CloseLiquidateService<Explosion> closeLiquidateService;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private HistoryExplosionService historyExplosionService;


    @Override
    protected ExplosionExample getPageExample(final String fieldName, final String keyword) {
        final ExplosionExample example = new ExplosionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Explosion> listExplosion(final long id, final int size, final String contractCode) {
        return this.explosionRepository.listExplosion(id, size, contractCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void explosion(final Contract contract, final List<Contract> allContract,
                          final List<Explosion> explosion) {
        final Map<String, MarkIndexPriceDTO> priceMap = this.marketService.allMarkIndexPrice();
        final MarkIndexPriceDTO markIndex = priceMap.get(contract.getContractCode());
        if (markIndex == null) {
            ExplosionServiceImpl.log.error("not found market price, contractCode : {}", contract.getContractCode());
            return;
        }
        // 检查状态
        List<UserPosition> positions = this.closeLiquidateService.preCheck(contract, markIndex.getMarkPrice(), explosion);
        if (CollectionUtils.isEmpty(positions)) {
            return;
        }
        positions = this.closeLiquidateService.lockBalanceAndCheckStatus(contract, allContract, positions, explosion, (list) -> {
            final Date date = new Date();
            final List<HistoryExplosion> historyExplosions = new ArrayList<>(list.size());
            for (final UserPosition userPosition : list) {
                final HistoryExplosion historyExplosion = HistoryExplosion.builder()
                        .beforePositionQuantity(userPosition.getAmount())
                        .afterPositionQuantity(BigDecimal.ZERO)
                        .brokerId(userPosition.getBrokerId())
                        .brokerPrice(userPosition.getBrokerPrice())
                        .contractCode(contract.getContractCode())
                        .closePositionQuantity(BigDecimal.ZERO)
                        .createdDate(date)
                        .liqudatePrice(userPosition.getLiqudatePrice())
                        .marketPrice(markIndex.getMarkPrice())
                        .modifyDate(date)
                        .preLiqudatePrice(userPosition.getPreLiqudatePrice())
                        .userId(userPosition.getUserId())
                        .side(userPosition.getSide()).build();
                historyExplosions.add(historyExplosion);
            }

            final Map<Integer, Map<Long, Explosion>> recordMap = explosion.stream()
                    .collect(Collectors
                            .groupingBy(Explosion::getBrokerId, Collectors.toMap(Explosion::getUserId, Function.identity(), (x, y) -> x)));

            this.historyExplosionService.batchInsertWithId(historyExplosions);

            for (final HistoryExplosion historyExplosion : historyExplosions) {
                final Explosion record = recordMap.get(historyExplosion.getBrokerId()).get(historyExplosion.getUserId());
                record.setHistoryExplosionId(historyExplosion.getId());
                record.setModifyTime(date);
            }
            this.explosionRepository.batchUpdate(explosion);
        });
        if (CollectionUtils.isEmpty(positions)) {
            return;
        }

        final Map<Long, Explosion> explosionMap = explosion.stream().collect(Collectors.toMap(Explosion::getId, Function.identity(), (x, y) -> x));

        final List<UserPosition> placeOrderPosition = new ArrayList<>();
        final List<UserPosition> contraTradePosition = new ArrayList<>();
        for (final UserPosition position : positions) {
            final Explosion ep = explosionMap.get(position.getId());
            if (ep.getCloseOrderId() == null) {
                placeOrderPosition.add(position);
            } else {
                contraTradePosition.add(position);
            }
        }

        if (CollectionUtils.isNotEmpty(placeOrderPosition)) {
            this.closeLiquidateService.placeCloseOrder(contract, markIndex.getMarkPrice(), placeOrderPosition, explosion);
        }

        if (CollectionUtils.isNotEmpty(contraTradePosition)) {

            final Map<String, List<UserPosition>> sideGroup = contraTradePosition.stream().collect(Collectors.groupingBy(UserPosition::getSide));

            final Set<Map.Entry<String, List<UserPosition>>> entries = sideGroup.entrySet();
            for (final Map.Entry<String, List<UserPosition>> entry : entries) {
                final OrderSideEnum sideEnum = OrderSideEnum.LONG.getSide().equals(entry.getKey())
                        ? OrderSideEnum.SHORT
                        : OrderSideEnum.LONG;
                final List<UserPosition> userPositions = entry.getValue();
                int index = 0;
                final List<Explosion> explosionList = new LinkedList<>();
                for (final UserPosition u : userPositions) {
                    explosionList.add(explosionMap.get(u.getId()));
                }
                boolean finished = false;
                while (!finished) {
                    final List<UserPositionService.UserRank> userRank = this.takeUserRank(userPositions, contract, sideEnum, index);
                    if (CollectionUtils.isEmpty(userRank)) {
                        this.userPositionService.sortUserPosition(contract.getContractCode());
                        break;
                    }
                    finished = this.closeLiquidateService.contraTrade(contract, allContract, explosionList, userRank, priceMap);
                    index += userRank.size();
                }
            }
        }
    }

    @Override
    public int batchInsertOnDuplicateKeyDoNothing(final List<Explosion> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return this.explosionRepository.batchInsertOnDuplicateKeyDoNothing(list);
    }

    @Override
    public void removeInById(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        final ExplosionExample example = new ExplosionExample();
        example.createCriteria().andIdIn(ids);
        this.removeByExample(example);
    }

    private List<UserPositionService.UserRank> takeUserRank(final List<UserPosition> positions, final Contract contract, final OrderSideEnum sideEnum, final int index) {
        if (CollectionUtils.isEmpty(positions)) {
            return new ArrayList<>();
        }
        BigDecimal amount = BigDecimal.ZERO;
        for (final UserPosition u : positions) {
            amount = amount.add(u.getAmount());
        }

        final List<UserPositionService.UserRank> userRank = this.userPositionService.getUserRank(contract.getContractCode(), sideEnum);
        if (CollectionUtils.isEmpty(userRank)) {
            return new ArrayList<>();
        }
        if (index > userRank.size()) {
            return new ArrayList<>();
        }
        final List<UserPositionService.UserRank> tempList = userRank.subList(index, userRank.size());

        final List<UserPositionService.UserRank> result = new ArrayList<>();
        for (final UserPositionService.UserRank u : tempList) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            result.add(u);
            amount = amount.subtract(u.getAmount());
        }
        return result;
    }

}
