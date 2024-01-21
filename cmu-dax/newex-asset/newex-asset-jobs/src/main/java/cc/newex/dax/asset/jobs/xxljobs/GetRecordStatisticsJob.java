package cc.newex.dax.asset.jobs.xxljobs;

import cc.newex.dax.asset.service.BalanceSummaryService;
import cc.newex.dax.asset.service.TransferRecordService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 统计用户的充值和提现数据
 * 首充人数、充值人数、充值金额（BTC）、提现人数、提现笔数、提现金额 等
 *
 * @author newex-team
 * @data 2018/6/25
 */

@Slf4j
@JobHandler(value = "getRecordStatisticsJobXXL")
@Component
public class GetRecordStatisticsJob extends IJobHandler {

    @Autowired
    private TransferRecordService transferRecordService;

    @Autowired
    private BalanceSummaryService balanceSummaryService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        log.info("GetRecordStatisticsJob begin");
        try {
            this.transferRecordService.analyseTransferData();
            final Date oneWeekAgo = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            this.balanceSummaryService.removeDataBeforeTime(oneWeekAgo);
        } catch (final Throwable e) {
            log.error("GetRecordStatisticsJob error", e);
        }
        log.info("GetRecordStatisticsJob end");
        return IJobHandler.SUCCESS;
    }
}
