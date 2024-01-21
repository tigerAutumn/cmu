package cc.newex.dax.asset.jobs.xxljobs;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.dax.asset.service.UnlockedPositionService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 释放锁仓任务
 * @author: newex-team
 * @date: 2018/6/19 下午4:24
 */
@Slf4j
@JobHandler(value = "unlockPositionXxl")
@Component
public class UnlockPositionJob extends IJobHandler {

    @Autowired
    private UnlockedPositionService unlockedPositionService;

    @Override
    public ReturnT<String> execute(final String s) {
        UnlockPositionJob.log.info("UnlockPositionJob begin");
        try {
            this.unlockedPositionService.unlockedPositionJob(DateUtil.getToday());
        } catch (final Throwable e) {
            UnlockPositionJob.log.info("UnlockPositionJob error", e);
        }
        UnlockPositionJob.log.info("UnlockPositionJob end");
        return IJobHandler.SUCCESS;
    }

}
