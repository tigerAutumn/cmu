package cc.newex.dax.perpetual.matching.task;

import cc.newex.dax.perpetual.matching.dealing.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 刷新market和fee config的配置
 *
 * @author xionghui
 * @date 2018/10/18
 */
@Slf4j
@Component
public class ContractTask {
  @Autowired
  private Worker worker;

  @Scheduled(initialDelay = 10_000, fixedDelay = 10_000)
  public void refresh() {
    ContractTask.log.info("ContractTask refresh begin");
    try {
      this.worker.refresh();
    } catch (final Exception e) {
      ContractTask.log.error("ContractTask worker refresh error: ", e);
    }
    ContractTask.log.info("ContractTask refresh end");
  }
}
