package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.service.OmniTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author newex-team
 * @data 08/04/2018
 */

/**
 * @author newex-team
 * @data 29/03/2018
 */
@Slf4j
abstract public class AbstractScanOmniBlockJob extends AbstractScanBlockJob {
    //private final int PAGE_SIZE = 500;

    @Autowired
    OmniTransactionService omniTransactionService;

//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        AbstractScanOmniBlockJob.log.info("update {} transaction confirmation begin", currency.getName());
//        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
//        final PageInfo page = new PageInfo();
//        page.setPageSize(this.PAGE_SIZE);
//        page.setSortItem("id");
//        page.setSortType(PageInfo.SORT_TYPE_ASC);
//        page.setStartIndex(0);
//        final OmniTransactionExample example = new OmniTransactionExample();
//        example.createCriteria().andSpentTxIdEqualTo(UNSPENT_TX_ID).andConfirmNumLessThan(currency.getConfirmNum()).andOmniBalanceGreaterThan(BigDecimal.ZERO);
//
//        while (true) {
//            final List<OmniTransaction> omniTransactions = this.omniTransactionService.getByPage(page, example, table);
//            omniTransactions.parallelStream().forEach((omni) -> {
//                omni.setCurrency(currency.getIndex());
//                omni.setUpdateDate(Date.from(Instant.now()));
//                final Integer confirm = this.wallet.getConfirm(omni.getTxId());
//                omni.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);
//
//                OmniTransaction tmp = OmniTransaction.builder().id(omni.getId()).confirmNum(omni.getConfirmNum()).build();
//                this.omniTransactionService.editById(tmp, table);
//
//
//                final TransactionDTO dto = this.wallet.convertOmniTXToDto(omni);
//                this.txService.saveTransaction(dto);
//
//            });
//
//            if (omniTransactions.size() < this.PAGE_SIZE) {
//                break;
//            }
//            page.setStartIndex(page.getStartIndex() + this.PAGE_SIZE);
//        }
//
//        AbstractScanOmniBlockJob.log.info("update {} transaction confirmation end", currency.getName());
//    }
}
