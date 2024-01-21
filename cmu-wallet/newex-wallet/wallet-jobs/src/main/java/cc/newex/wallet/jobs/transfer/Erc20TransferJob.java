package cc.newex.wallet.jobs.transfer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.impl.Erc20Wallet;
import cc.newex.wallet.wallet.impl.EthWallet;
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
@ElasticJobExtConfig(cron = "1 5/30 * * * ?")
@Slf4j
public class Erc20TransferJob implements SimpleJob {

    //存储eth数目不够的地址

    private final Set<String> addressSet = new ConcurrentSet<>();
    @Autowired
    AccountTransactionService accountTransactionService;
    @Autowired
    Erc20Wallet erc20Wallet;
    @Autowired
    EthWallet ethWallet;
    BigDecimal minEth = new BigDecimal("0.02");

    @Override
    public void execute(final ShardingContext shardingContext) {

        Erc20TransferJob.log.info("Transfer erc20 job begin");
        CurrencyEnum.ERC20_SET.parallelStream().forEach(this::transferErc20);

        //往eth余额不够的地址中转一些eth
        this.addressSet.parallelStream().forEach(addr -> {
            final WithdrawRecord record = WithdrawRecord.builder()
                    .address(addr)
                    .balance(this.minEth)
                    .currency(CurrencyEnum.ETH.getIndex())
                    .build();
            this.ethWallet.withdraw(record);
        });

        this.addressSet.clear();
        Erc20TransferJob.log.info("Transfer erc20 job end");

    }

    public void transferErc20(final CurrencyEnum currency) {
        //一次划转100个
        final int pageSize = 100;


        Erc20TransferJob.log.info("Transfer {} job begin", currency.getName());

        final AccountTransactionExample example = new AccountTransactionExample();
        Date deadline = new Date();
        example.createCriteria().andStatusEqualTo((byte) 0).andAddressNotEqualTo(this.erc20Wallet.getWithdrawAddress())
                .andConfirmNumGreaterThanOrEqualTo(currency.getWithdrawConfirmNum()).andBalanceGreaterThan(BigDecimal.ZERO);

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
        final List<AccountTransaction> accountTransactions = this.accountTransactionService.getByPage(pageInfo, example, table);
        log.info("{} transfer tx size : {}", currency.getName(), accountTransactions.size());
        final Set<String> addresses = accountTransactions.parallelStream().map(AccountTransaction::getAddress).collect(Collectors.toSet());
        log.info("{} transfer address size : {}", currency.getName(), addresses.size());
        addresses.parallelStream().forEach((address) -> {
            final BigDecimal ethBalance = this.ethWallet.getBalance(address);
            if (ethBalance.compareTo(this.minEth) < 0) {
                this.addressSet.add(address);
            } else {
                this.erc20Wallet.transfer(address, currency, deadline);
            }
        });
        Erc20TransferJob.log.info("Transfer {} job end", currency.getName());

    }


}
