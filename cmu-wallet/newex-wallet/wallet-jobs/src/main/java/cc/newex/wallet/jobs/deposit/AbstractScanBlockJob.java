package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.criteria.BestBlockHeightExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.currency.CurrencyOnlineEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.BestBlockHeight;
import cc.newex.wallet.service.BestBlockHeightService;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.wallet.AbstractWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @data 2018/4/29
 */
@Slf4j
abstract public class AbstractScanBlockJob {

    protected AbstractWallet wallet;

    @Autowired
    protected TransactionService txService;

    @Autowired
    BestBlockHeightService bestHeightService;

    @Value("${newex.app.env.name}")
    private String env;

    private final CurrencyEnum run = CurrencyEnum.BAIC;

    public void execute(final ShardingContext shardingContext) {
        if ("dev".equals(this.env)) {
            if (this.wallet.getCurrency() != this.run) {
                return;
            }
        }

        final CurrencyEnum currencyEnum = this.wallet.getCurrency();

        if (currencyEnum.getOnline() == CurrencyOnlineEnum.OFFLINE.getCode()) {
            log.info("not scan offline currency: {}", currencyEnum.getName());

            return;
        }

        log.info("scan {} tx begin", currencyEnum.getName());

        Long bestHeight = null;
        try {
            //先更新数据库中已有的交易的确认数
            this.wallet.updateTXConfirmation(this.wallet.getCurrency());

            //查询链上的最新区块高度
            bestHeight = this.wallet.getBestHeight();

            log.info("scan {} tx begin, chain height: {}", currencyEnum.getName(), bestHeight);

            //查询数据库中已经同步的区块高度
            BestBlockHeight bestBlockHeight = this.getDbBestBlockHeight();

            Long storedHeight = 0L;
            if (!ObjectUtils.isEmpty(bestBlockHeight)) {
                storedHeight = bestBlockHeight.getHeight();
            }

            if (currencyEnum.equals(CurrencyEnum.BTC)) {
                // 一次最多遍历10个区块
                bestHeight = Math.min(storedHeight + 10L, bestHeight);
            } else {
                bestHeight = Math.min(storedHeight + 100L, bestHeight);
            }

            //初始化best block height 表
            if (ObjectUtils.isEmpty(bestBlockHeight)) {
                bestBlockHeight = new BestBlockHeight();
                final boolean insertFlag = this.initCurrencyBestHeight(bestBlockHeight, bestHeight);
                if (!insertFlag) {
                    return;
                }
            }

            //判断数据库中的区块高度高于链上的高度
            if (storedHeight >= bestHeight) {
                log.error("scan {} tx end, database height is ge best height, stored height: {}, best height: {}",
                        currencyEnum.getName(), storedHeight, bestHeight);

                return;
            }

            log.info("scan {} tx begin, stored height: {}, best height: {}", currencyEnum.getName(), storedHeight, bestHeight);

            //循环扫描某个阶段的区块
            for (long begin = bestBlockHeight.getHeight() - this.wallet.getCurrency().getDepositConfirmNum(); begin <= bestHeight; begin++) {
                log.info("scan {} tx, height: {}, stored height: {}, best height: {}", currencyEnum.getName(), begin, storedHeight, bestHeight);

                //具体扫描区块处理逻辑
                final List<TransactionDTO> transactions = this.wallet.findRelatedTxs(begin);

                //当前区块没有交易数据 进入下一区块
                if (transactions == null) {
                    bestHeight = begin - 1;
                    break;
                }

                if (transactions.size() == 0) {
                    continue;
                }

                //推送查询到的交易数据
                this.txService.saveTransaction(transactions);
            }

            //更新数据库最新扫描完的区块
            this.updateStoreHeight(bestHeight, bestBlockHeight);

            //更新币种余额
            this.wallet.updateTotalCurrencyBalance();

        } catch (final Throwable e) {
            log.error("scan {} tx error, current height: {}, msg: {}", currencyEnum.getName(), bestHeight, e.getMessage(), e);
        }

        log.info("scan {} tx end, current height: {}", currencyEnum.getName(), bestHeight);
    }

    /**
     * 更新数据库扫描到的高度
     *
     * @param bestHeight
     * @param storedHeight
     */
    protected void updateStoreHeight(final Long bestHeight, final BestBlockHeight storedHeight) {
        storedHeight.setHeight(bestHeight);
        storedHeight.setUpdateDate(Date.from(Instant.now()));
        this.bestHeightService.editById(storedHeight);
    }

    /**
     * 查询数据库中已经扫描到的高度
     *
     * @return
     */
    protected BestBlockHeight getDbBestBlockHeight() {
        final BestBlockHeightExample example = new BestBlockHeightExample();
        example.createCriteria().andCurrencyEqualTo(this.wallet.getCurrency().getIndex());
        return this.bestHeightService.getOneByExample(example);
    }

    /**
     * 初始化扫描的区块高度
     *
     * @param storedHeight
     * @param bestHeight
     * @return
     */
    protected boolean initCurrencyBestHeight(final BestBlockHeight storedHeight, final Long bestHeight) {
        storedHeight.setHeight(bestHeight);
        storedHeight.setCurrency(this.wallet.getCurrency().getIndex());

        final int insertFlag = this.bestHeightService.add(storedHeight);

        if (insertFlag < 1) {
            log.error("init best block height fail, currency:{}", this.wallet.getCurrency().getName());

            return false;
        }

        return true;
    }
}
