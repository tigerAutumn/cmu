package cc.newex.dax.extra.scheduler.xxljobs;

import cc.newex.dax.extra.scheduler.service.RatingTokenXxlService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ratingToken其他数据更新定时任务
 * 例如:团队信息
 *
 * @author better
 * @date create in 2018-12-03 10:59
 */
@Slf4j
@JobHandler(value = "ratingTokenOtherXxlJob")
@Component
public class RatingTokenOtherXxlJob extends IJobHandler {

    private final RatingTokenXxlService ratingTokenXxlService;

    /**
     * Instantiates a new Rating token other xxl job.
     *
     * @param ratingTokenXxlService the rating token xxl service
     */
    @Autowired
    public RatingTokenOtherXxlJob(final RatingTokenXxlService ratingTokenXxlService) {
        this.ratingTokenXxlService = ratingTokenXxlService;
    }

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.ratingTokenXxlService.getRatingTokenTeamInfo();
        return IJobHandler.SUCCESS;
    }
}
