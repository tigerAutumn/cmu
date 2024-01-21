package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.impl.RbtcWallet;
import cc.newex.wallet.wallet.impl.RskTokenWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import io.netty.util.internal.ConcurrentSet;
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
 * @data 17/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 30 1/1 * * ?")
//@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class RskTokenTransferJob implements SimpleJob {

    //存储rbtc数目不够的地址
    private final Set<String> addressSet = new ConcurrentSet<>();
    @Autowired
    AccountTransactionService accountTransactionService;
    @Autowired
    RskTokenWallet rskTokenWallet;
    @Autowired
    RbtcWallet rbtcWallet;
    BigDecimal minRbtc = new BigDecimal("0.0001");

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("rsk token Transfer job begin");
        CurrencyEnum.RSK_TOKEN_ASSET.parallelStream().forEach(this::transferRskToken);

        //往rbtc余额不够的地址中转一些rbtc
        addressSet.parallelStream().forEach(addr -> {
            WithdrawRecord record = WithdrawRecord.builder()
                    .address(addr)
                    .balance(minRbtc)
                    .currency(CurrencyEnum.RBTC.getIndex())
                    .build();
            rbtcWallet.withdraw(record);
        });
        addressSet.clear();
        log.info("rsk token Transfer job end");
    }

    public void transferRskToken(CurrencyEnum currency) {
        //一次划转100个
        int pageSize = 100;
        log.info("Transfer {} job begin", currency.getName());
        AccountTransactionExample example = new AccountTransactionExample();
        Date deadline = new Date();
        example.createCriteria().andStatusEqualTo((byte) 0).andAddressNotEqualTo(rskTokenWallet.getWithdrawAddress())
                .andConfirmNumGreaterThanOrEqualTo(currency.getWithdrawConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        List<AccountTransaction> accountTransactions = accountTransactionService.getByPage(pageInfo, example, table);
        log.info("{} transfer tx size : {}", currency.getName(), accountTransactions.size());
        Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
        log.info("{} transfer address size : {}", currency.getName(), addresses.size());
        addresses.parallelStream().forEach((address) -> {
            BigDecimal rbtcBalance = rbtcWallet.getBalance(address);
            if (rbtcBalance.compareTo(minRbtc) < 0) {
                addressSet.add(address);
            } else {
                rskTokenWallet.transfer(address, currency, deadline);
            }
        });
        log.info("rsk token {} Transfer job end", currency.getName());
    }
}
