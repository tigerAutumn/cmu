package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.service.AccountTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
abstract public class AbstractScanAccountBlockJob extends AbstractScanBlockJob {

    @Autowired
    protected AccountTransactionService accountTransactionService;

}
