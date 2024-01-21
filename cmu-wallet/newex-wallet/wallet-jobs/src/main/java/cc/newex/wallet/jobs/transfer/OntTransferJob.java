package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.impl.OntWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 17/04/2018
 */
// @Component
// @ElasticJobExtConfig(cron = "1 5/30 * * * ?")
@Slf4j
public class OntTransferJob implements SimpleJob {

    @Autowired
    AccountTransactionService accountTransactionService;
    @Autowired
    OntWallet ontWallet;

    @Override
    public void execute(final ShardingContext shardingContext) {

        OntTransferJob.log.info("Transfer ont job begin");
        CurrencyEnum.ONT_ASSET.parallelStream().forEach(this::transferOnt);
        OntTransferJob.log.info("Transfer ont job end");

    }

    public void transferOnt(final CurrencyEnum currency) {
        //一次划转100个
        final int pageSize = 100;


        OntTransferJob.log.info("Transfer {} job begin", currency.getName());

        final AccountTransactionExample example = new AccountTransactionExample();
        final Date deadline = new Date();
        example.createCriteria().andStatusEqualTo((byte) 0).andAddressNotEqualTo(this.ontWallet.getWithdrawAddress())
                .andConfirmNumGreaterThan(currency.getWithdrawConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final List<AccountTransaction> accountTransactions = this.accountTransactionService.getByPage(pageInfo, example, table);
        final Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
        addresses.parallelStream().forEach((address) -> {
            this.ontWallet.transfer(address, currency, deadline);
        });
        OntTransferJob.log.info("Transfer {} job end", currency.getName());

    }


}
