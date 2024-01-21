package cc.newex.dax.market.spider.common.config;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.TaskUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Configuration
@Slf4j
public class SchedulingConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(this.taskExecutor());
    }

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadGroupName("market-spider");
        scheduler.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
        return scheduler;
    }

    @Bean("executorService")
    public ExecutorService splitExecutor() {
        return ExecutorServiceUtil.newExecutorService();
    }

    @Bean("moveExecutor")
    public TaskExecutor moveHistoryExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.setThreadNamePrefix("moveHistory");
        taskExecutor.setThreadGroupName("move");
        return taskExecutor;
    }
}
