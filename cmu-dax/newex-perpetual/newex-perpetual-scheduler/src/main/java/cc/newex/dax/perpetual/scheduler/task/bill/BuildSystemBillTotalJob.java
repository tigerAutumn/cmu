package cc.newex.dax.perpetual.scheduler.task.bill;

import cc.newex.dax.perpetual.service.SystemBillTotalService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 系统对账任务
 *
 * @author xionghui
 * @date 2018/11/16
 */
@Slf4j
@JobHandler(value = "BuildSystemBillTotalXxlJob")
@Component
public class BuildSystemBillTotalJob extends IJobHandler {
  @Autowired
  private SystemBillTotalService systemBillTotalService;

  @Override
  public ReturnT<String> execute(final String s) throws Exception {
    this.createSystemBillTotal();
    return ReturnT.SUCCESS;
  }

  /**
   * 一分钟执行一次
   */
  public void createSystemBillTotal() {
    this.systemBillTotalService.buildSystemBillTotal();
  }
}
