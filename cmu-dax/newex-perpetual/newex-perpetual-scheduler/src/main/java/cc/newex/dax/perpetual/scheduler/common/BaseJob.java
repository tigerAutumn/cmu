package cc.newex.dax.perpetual.scheduler.common;

import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.scheduler.common.executor.BaseExecutor;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseJob extends IJobHandler {

    /**
     * 对 xxl job 进行封装，统一处理任务日志
     *
     * @param s xxl 调度器 输入参数
     * @return xxl job 返回模板
     * @throws Exception job 执行异常
     */
    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        try {
            final BaseExecutor<String> executor = this.takeBaseExecutor(s);
            if (executor != null) {
                executor.exec(s);
            }
            return IJobHandler.SUCCESS;
        } catch (final BizException e) {
            BaseJob.log.error("do job failed", e);
        }
        return IJobHandler.FAIL;
    }

    /**
     * 获取执行 job 的 job executor
     *
     * @param param xxl job 请求参数
     * @return job executor
     */
    protected abstract BaseExecutor<String> takeBaseExecutor(String param);
}
