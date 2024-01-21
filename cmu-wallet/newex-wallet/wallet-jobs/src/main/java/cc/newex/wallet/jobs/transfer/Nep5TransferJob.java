package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.impl.Nep5Wallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "5 1/30 * * * ?")
@Slf4j
public class Nep5TransferJob implements SimpleJob {
    @Autowired
    Nep5Wallet nep5Wallet;

    @Autowired
    AccountTransactionService accountTransactionService;


    @Override
    public void execute(final ShardingContext shardingContext) {

        log.info("Transfer nep5 job begin");
        CurrencyEnum.NEP5_SET.parallelStream().forEach(this::transferNep5);

        log.info("Transfer nep5 job end");

    }

    public void transferNep5(final CurrencyEnum currency) {
        //一次划转100个
        final int pageSize = 100;


        log.info("Transfer {} job begin", currency.getName());

        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andStatusEqualTo((byte) 0).andAddressNotEqualTo(this.nep5Wallet.getWithdrawAddress())
                .andConfirmNumGreaterThanOrEqualTo(currency.getWithdrawConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final List<AccountTransaction> accountTransactions = this.accountTransactionService.getByPage(pageInfo, example, table);
        final Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
        Date deadline = new Date();
        addresses.parallelStream().forEach((address) -> {
            this.nep5Wallet.transfer(address, currency, deadline);
        });
        log.info("Transfer {} job end", currency.getName());

    }
}
