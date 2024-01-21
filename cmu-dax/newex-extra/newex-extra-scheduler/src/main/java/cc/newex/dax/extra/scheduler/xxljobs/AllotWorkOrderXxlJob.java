package cc.newex.dax.extra.scheduler.xxljobs;


import cc.newex.dax.extra.scheduler.service.WorkOrderJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author newex-team
 */
@Slf4j
@JobHandler(value = "allotWorkOrderXxl")
@Component
public class AllotWorkOrderXxlJob extends IJobHandler {


    @Autowired
    private WorkOrderJobService workOrderJobService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.allotWorkOrder();
        return IJobHandler.SUCCESS;
    }

    public void allotWorkOrder() {
        this.workOrderJobService.allotWorkOrder();
    }
}
