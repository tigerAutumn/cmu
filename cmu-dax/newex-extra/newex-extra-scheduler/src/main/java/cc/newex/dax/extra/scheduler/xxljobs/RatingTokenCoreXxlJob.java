package cc.newex.dax.extra.scheduler.xxljobs;

import cc.newex.dax.extra.scheduler.service.RatingTokenXxlService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ratingToken核心数据更新定时任务
 * 例如: 基础信息, 评分信息
 *
 * @author better
 * @date create in 2018-12-03 10:59
 */
@Slf4j
@JobHandler(value = "ratingTokenCoreXxlJob")
@Component
public class RatingTokenCoreXxlJob extends IJobHandler {

    private final RatingTokenXxlService ratingTokenXxlService;

    /**
     * Instantiates a new Rating token core xxl job.
     *
     * @param ratingTokenXxlService the rating token xxl service
     */
    @Autowired
    public RatingTokenCoreXxlJob(final RatingTokenXxlService ratingTokenXxlService) {
        this.ratingTokenXxlService = ratingTokenXxlService;
    }

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        this.ratingTokenXxlService.getRatingTokenRankListInfo();
        this.ratingTokenXxlService.getRatingTokenBaseInfo();
        this.ratingTokenXxlService.getRatingTokenScoreInfo();
        return IJobHandler.SUCCESS;
    }
}
