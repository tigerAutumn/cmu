package cc.newex.wallet.jobs.deposit;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 扫描打包在区块中的交易
 *
 * @author newex-team
 * @data 29/03/2018
 */
@Slf4j
public class AbstractScanOnlineBtcBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

}
