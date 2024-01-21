package cc.newex.dax.push.task;

import java.lang.reflect.Field;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cc.newex.dax.push.cache.PushCache;
import lombok.extern.slf4j.Slf4j;

/**
 * 检查内存和idle回话
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Slf4j
@Component
public class MonitorTask {

  @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
  public void monitor() {
    log.info("MonitorTask monitor begin");
    try {
      final Class<?> c = Class.forName("java.nio.Bits");
      final Field maxMemory = c.getDeclaredField("maxMemory");
      maxMemory.setAccessible(true);
      final Field reservedMemory = c.getDeclaredField("reservedMemory");
      reservedMemory.setAccessible(true);
      final Object maxMemoryValue = maxMemory.get(null);
      final Object reservedMemoryValue = reservedMemory.get(null);
      log.info("MonitorTask monitor Drirect maxMemory: {} reservedMemory: {}", maxMemoryValue,
          reservedMemoryValue);
    } catch (final Exception e) {
      log.error("MonitorTask monitor error: ", e);
    }
  }

  @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
  public void idleCheck() {
    log.info("MonitorTask idleCheck begin");
    try {
      PushCache.dealIdle();
      log.info("MonitorTask idleCheck end");
    } catch (final Exception e) {
      log.error("MonitorTask idleCheck error: ", e);
    }
  }
}
