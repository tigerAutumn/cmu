package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.service.UtxoTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author newex-team
 * @data 09/04/2018
 */

@Slf4j
abstract public class AbstractScanUtxoBlockJob extends AbstractScanBlockJob {

    //private final int PAGE_SIZE = 500;
    @Autowired
    UtxoTransactionService utxoTransactionService;


//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        AbstractScanUtxoBlockJob.log.info("{} update transaction confirmation begin", this.wallet.getCurrency().getName());
//        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();
//        final PageInfo page = new PageInfo();
//        page.setPageSize(this.PAGE_SIZE);
//        page.setSortItem("id");
//        page.setSortType(PageInfo.SORT_TYPE_ASC);
//        page.setStartIndex(0);
//        final UtxoTransactionExample example = new UtxoTransactionExample();
//        example.createCriteria().andSpentTxIdEqualTo(UNSPENT_TX_ID).andConfirmNumLessThan(currency.getConfirmNum());
//
//        while (true) {
//            final List<UtxoTransaction> utxos = this.utxoTransactionService.getByPage(page, example, table);
//            utxos.parallelStream().forEach((utxo) -> {
//                utxo.setCurrency(currency.getIndex());
//                utxo.setUpdateDate(Date.from(Instant.now()));
//                final Integer confirm = this.wallet.getConfirm(utxo.getTxId());
//                utxo.setConfirmNum(confirm.longValue() > 0 ? confirm.longValue() : 0);
//                UtxoTransaction tmp = UtxoTransaction.builder().id(utxo.getId()).confirmNum(utxo.getConfirmNum()).build();
//                this.utxoTransactionService.editById(tmp, table);
//                final TransactionDTO dto = this.wallet.convertUtxoToDto(utxo);
//                this.txService.saveTransaction(dto);
//
//            });
//
//            if (utxos.size() < this.PAGE_SIZE) {
//                break;
//            }
//            page.setStartIndex(page.getStartIndex() + this.PAGE_SIZE);
//        }
//
//        AbstractScanUtxoBlockJob.log.info("update transaction confirmation end");
//    }


}
