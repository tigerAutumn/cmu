package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.service.AccountTransactionService;
import cc.newex.wallet.wallet.impl.NeoWallet;
import cc.newex.wallet.wallet.impl.Nep5Wallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫描打包在区块中的交易
 *
 * @author newex-team
 * @data 29/03/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@Slf4j
public class ScanNeoBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    Nep5Wallet nep5Wallet;
    @Autowired
    protected AccountTransactionService accountTransactionService;



    @Autowired
    public ScanNeoBlockJob(final NeoWallet neoWallet) {
        this.wallet = neoWallet;
    }

//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        //super.updateTXConfirmation(CurrencyEnum.NEO);
//        CurrencyEnum.NEO_ASSET.parallelStream().forEach(super::updateTXConfirmation);
//        CurrencyEnum.NEP5_SET.parallelStream().forEach(this::updateAccountTXConfirmation);
//    }


//    private void updateAccountTXConfirmation(final CurrencyEnum currency) {
//        log.info("update {} transaction confirmation begin", currency.getName());
//        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
//        final PageInfo page = new PageInfo();
//        final int size = 500;
//        page.setPageSize(size);
//        page.setSortItem("id");
//        page.setSortType(PageInfo.SORT_TYPE_ASC);
//        page.setStartIndex(0);
//
//        final AccountTransactionExample example = new AccountTransactionExample();
//        example.createCriteria().andConfirmNumLessThan(currency.getConfirmNum());
//        while (true) {
//            final List<AccountTransaction> acTxs = this.accountTransactionService.getByPage(page, example, table);
//            acTxs.parallelStream().forEach((tx) -> {
//                tx.setCurrency(currency.getIndex());
//                tx.setUpdateDate(Date.from(Instant.now()));
//
//                final Integer confirm = this.wallet.getConfirm(tx.getTxId());
//                tx.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);
//                this.accountTransactionService.editById(tx, table);
//                final TransactionDTO dto = this.wallet.convertAccountTxToDto(tx);
//                this.txService.saveTransaction(dto);
//
//            });
//
//            if (acTxs.size() < size) {
//                break;
//            }
//            page.setStartIndex(page.getStartIndex() + size);
//        }
//
//        log.info("update {} transaction confirmation end", currency.getName());
//    }
}
