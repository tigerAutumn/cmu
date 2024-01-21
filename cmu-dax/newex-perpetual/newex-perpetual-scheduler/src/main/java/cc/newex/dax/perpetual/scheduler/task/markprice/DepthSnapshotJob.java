package cc.newex.dax.perpetual.scheduler.task.markprice;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@JobHandler(value = "depthSnapshot")
@Component
public class DepthSnapshotJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.activity();
        return IJobHandler.SUCCESS;
    }

    /**
     * 发币xxl任务
     */
    public void activity() {
    }
}
