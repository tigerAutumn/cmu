package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.AbstractWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Slf4j
@Data
abstract public class AbstractTransferJob implements SimpleJob {
    @Autowired
    AccountTransactionService accountTransactionService;

    AbstractWallet wallet;

    @Override
    public void execute(ShardingContext shardingContext) {
        //一次划转100个
        int pageSize = 100;

        log.info("Transfer {} job begin", wallet.getCurrency().getName());

        try {
            AccountTransactionExample example = new AccountTransactionExample();
            example.createCriteria().andStatusEqualTo((byte) 0).andAddressNotEqualTo(this.wallet.getWithdrawAddress())
                    .andConfirmNumGreaterThanOrEqualTo(this.wallet.getCurrency().getWithdrawConfirmNum()).andBalanceGreaterThan
                    (BigDecimal.ZERO);


            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(pageSize);
            pageInfo.setStartIndex(0);
            pageInfo.setSortItem("id");
            pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
            Date deadline = new Date();
            ShardTable table = ShardTable.builder().prefix(this.wallet.getCurrency().getName()).build();
            List<AccountTransaction> accountTransactions = this.accountTransactionService.getByPage(pageInfo, example, table);
            Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
            addresses.parallelStream().forEach((address) -> {
                this.wallet.transfer(address, this.wallet.getCurrency(), deadline);
            });
        } catch (Throwable e) {
            log.info("TransferEth job error", e);

        }
        log.info("TransferEth job end");

    }
}
