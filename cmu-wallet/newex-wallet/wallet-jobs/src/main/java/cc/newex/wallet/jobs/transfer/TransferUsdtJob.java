package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.criteria.OmniTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.OmniTransaction;
import cc.newex.wallet.service.OmniTransactionService;
import cc.newex.wallet.wallet.impl.UsdtWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 定时把地址上的usdt转到提现地址
 *
 * @author newex-team
 * @data 08/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/30 * * * ?")
@Slf4j
public class TransferUsdtJob implements SimpleJob {

    @Autowired
    OmniTransactionService omniTransactionService;

    @Autowired
    UsdtWallet usdtWallet;


    @Value("${newex.usdt.withdraw.address}")
    private String withdrawAddress;

    @Override
    public void execute(final ShardingContext shardingContext) {
        //一次划转20个
        final int pageSize = 20;
        TransferUsdtJob.log.info("TransferUsdt job begin");
        try {
            final OmniTransactionExample example = new OmniTransactionExample();
            example.createCriteria().andStatusEqualTo((byte) 0).andSpentEqualTo((byte) 0).andAddressNotEqualTo(this.withdrawAddress)
                    .andConfirmNumGreaterThanOrEqualTo(CurrencyEnum.USDT.getConfirmNum()).andOmniBalanceGreaterThan(BigDecimal.ZERO);

            final PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(pageSize);
            pageInfo.setStartIndex(0);
            pageInfo.setSortItem("id");
            pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
            final ShardTable table = ShardTable.builder().prefix(CurrencyEnum.USDT.getName()).build();
            final List<OmniTransaction> omniTransactions = this.omniTransactionService.getByPage(pageInfo, example, table);
            omniTransactions.parallelStream().forEach((omni) -> {
                this.usdtWallet.transferUsdt(omni);
            });
        } catch (final Throwable e) {
            TransferUsdtJob.log.error("TransferUsdt job error", e);

        }
        TransferUsdtJob.log.info("TransferUsdt job end");


    }
}
