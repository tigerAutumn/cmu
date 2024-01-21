package cc.newex.dax.market.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.TaskUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 */
@Configuration
public class SchedulingConfigurerConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadGroupName("indexes");
        scheduler.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
        return scheduler;
    }

    @Bean("threadpoolExecutor")
    public Executor threadpoolExecutor() {
        return new ThreadPoolExecutor(64, 64,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Bean("klineThreadpoolExecutor")
    public Executor klineThreadpoolExecutor() {
        return new ThreadPoolExecutor(64, 64,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }
}