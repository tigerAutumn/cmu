package cc.newex.dax.perpetual.scheduler.task.position;

import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.domain.*;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.service.common.MarketService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 检查风险率
 */
@Slf4j
@Component
@JobHandler("CheckUserPositionJob")
public class CheckUserPositionJob extends IJobHandler {

    private static final int POOL_SIZE = 8;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("check-pst-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService =
            new ThreadPoolExecutor(CheckUserPositionJob.POOL_SIZE, CheckUserPositionJob.POOL_SIZE, 0,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.threadFactory);
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private PreLiquidateAlertService preLiquidateAlertService;
    @Autowired
    private ExplosionService explosionService;
    @Autowired
    private LiquidateService liquidateService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final List<Contract> contractList = this.contractService.getUnExpiredContract();

        if (CollectionUtils.isEmpty(contractList)) {
            CheckUserPositionJob.log.warn("contract list is empty");
            return ReturnT.SUCCESS;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(contractList.size());

        for (final Contract c : contractList) {
            this.executorService.execute(new Worker(countDownLatch, c));
        }

        countDownLatch.await();
        return ReturnT.SUCCESS;
    }

    private class Worker implements Runnable {

        private final CountDownLatch countDownLatch;
        private final Contract contract;
        private final int size;

        private Worker(final CountDownLatch countDownLatch, final Contract contract) {
            this.countDownLatch = countDownLatch;
            this.contract = contract;
            this.size = 1000;
        }


        @Override
        public void run() {
            try {
                final MarkIndexPriceDTO priceByPairCode = CheckUserPositionJob.this.marketService
                        .getMarkIndex(this.contract);
                if (priceByPairCode == null) {
                    CheckUserPositionJob.log.error("not found market price, contractCode : {}", this.contract.getContractCode());
                    return;
                }
                long index = 0L;
                while (true) {
                    // 标记价格
                    final BigDecimal marketPrice = priceByPairCode.getMarkPrice();
                    final List<UserPosition> userPositions =
                            CheckUserPositionJob.this.userPositionService.getHighRiskUserPosition(index,
                                    this.size, marketPrice, this.contract.getContractCode());

                    if (CollectionUtils.isEmpty(userPositions)) {
                        break;
                    }

                    final List<PreLiquidateAlert> preLiquidateAlerts = new LinkedList<>();
                    final List<Liquidate> liquidates = new LinkedList<>();
                    final List<Explosion> explosions = new LinkedList<>();
                    for (final UserPosition u : userPositions) {
                        // 记录最大的 id
                        index = Math.max(index, u.getId());
                        if (u.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                            continue;
                        }
                        // 判断多仓方向
                        if (OrderSideEnum.LONG.getSide().equalsIgnoreCase(u.getSide())) {
                            // 报警
                            if (u.getPreLiqudatePrice().compareTo(marketPrice) >= 0) {
                                preLiquidateAlerts.add(this.buildPreLiquidateAlert(u));
                            }
                            // 爆仓
                            if (u.getBrokerPrice().compareTo(marketPrice) >= 0) {
                                explosions.add(this.buildExplosion(u));
                                continue;
                            }
                            // 强平
                            if (u.getLiqudatePrice().compareTo(marketPrice) >= 0) {
                                liquidates.add(this.buildLiquidate(u));
                                continue;
                            }

                        } else { // 判断空仓方向
                            // 报警
                            if (u.getPreLiqudatePrice().compareTo(marketPrice) <= 0) {
                                preLiquidateAlerts.add(this.buildPreLiquidateAlert(u));
                            }
                            // 爆仓
                            if (u.getBrokerPrice().compareTo(marketPrice) <= 0) {
                                explosions.add(this.buildExplosion(u));
                                continue;
                            }
                            // 强平
                            if (u.getLiqudatePrice().compareTo(marketPrice) <= 0) {
                                liquidates.add(this.buildLiquidate(u));
                                continue;
                            }

                        }
                    }
                    // 插入数据，如果数据重复则不做任何处理
                    CheckUserPositionJob.this.explosionService.batchInsertOnDuplicateKeyDoNothing(explosions);
                    CheckUserPositionJob.this.liquidateService.batchInsertOnDuplicateKeyDoNothing(liquidates);
                    CheckUserPositionJob.this.preLiquidateAlertService
                            .batchInsertOnDuplicateKeyDoNothing(preLiquidateAlerts);
                }
            } catch (final Exception e) {
                CheckUserPositionJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }

        private PreLiquidateAlert buildPreLiquidateAlert(final UserPosition userPosition) {
            final Date date = new Date();
            return PreLiquidateAlert.builder().brokerId(userPosition.getBrokerId()).createTime(date)
                    .expireTime(date).id(userPosition.getId()).modifyTime(date).times(0)
                    .userId(userPosition.getUserId()).contractCode(userPosition.getContractCode()).build();
        }

        private Liquidate buildLiquidate(final UserPosition userPosition) {
            final Date date = new Date();
            return Liquidate.builder().brokerId(userPosition.getBrokerId()).createTime(date)
                    .id(userPosition.getId()).modifyTime(date).userId(userPosition.getUserId())
                    .contractCode(userPosition.getContractCode()).build();
        }

        private Explosion buildExplosion(final UserPosition userPosition) {
            final Date date = new Date();
            return Explosion.builder().brokerId(userPosition.getBrokerId()).createTime(date)
                    .id(userPosition.getId()).modifyTime(date).userId(userPosition.getUserId())
                    .contractCode(userPosition.getContractCode()).build();
        }
    }

}
