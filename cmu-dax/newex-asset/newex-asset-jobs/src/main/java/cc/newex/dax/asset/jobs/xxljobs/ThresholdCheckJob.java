package cc.newex.dax.asset.jobs.xxljobs;

import cc.newex.dax.asset.service.BalanceSummaryService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 对账-阈值告警
 * @author: newex-team
 * @date: 2018/6/19 下午4:24
 */
@Slf4j
@JobHandler(value = "thresholdCheckXxl")
@Component
public class ThresholdCheckJob extends IJobHandler {

    @Autowired
    private BalanceSummaryService balanceSummaryService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        ThresholdCheckJob.log.info("balanceThresholdAlert begin");
        try {
            balanceSummaryService.getAndCheckBalanceSummary();
        } catch (Throwable e) {
            ThresholdCheckJob.log.info("balanceThresholdAlert error", e);
        }
        ThresholdCheckJob.log.info("balanceThresholdAlert end");
        return IJobHandler.SUCCESS;
    }
}
