package cc.newex.dax.perpetual.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.TaskUtils;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class SchedulingConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(final ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(this.taskExecutor());
    }

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(100);
        scheduler.setThreadGroupName("newex-perpetual-scheduler");
        scheduler.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
        return scheduler;
    }
}
