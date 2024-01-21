package cc.newex.dax.extra.scheduler.xxljobs;

import cc.newex.dax.extra.scheduler.service.WorkOrderJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/6/19
 */
@JobHandler(value = "completeWorkOrderXxl")
@Component
public class CompleteWorkOrderXxlJob extends IJobHandler {

    @Autowired
    private WorkOrderJobService workOrderJobService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.completeWorkOrder();
        return IJobHandler.SUCCESS;
    }

    public void completeWorkOrder() {
        this.workOrderJobService.completeWorkOrder();
    }
}
