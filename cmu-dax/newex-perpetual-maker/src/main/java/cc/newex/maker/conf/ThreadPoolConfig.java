package cc.newex.maker.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author cmx-sdk-team
 * @date 2018-04-02
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "commonTaskExecutor")
    public TaskExecutor commonTaskExecutor() {
        final int poolSize = 500;
        final String namePrefix = "market-maker-";

        return this.initTaskExecutor(poolSize, namePrefix);
    }

    @Bean(name = "robotTaskExecutor")
    public TaskExecutor robotTaskExecutor() {
        final int poolSize = 300;
        final String namePrefix = "robot-maker-";

        return this.initTaskExecutor(poolSize, namePrefix);
    }

    private TaskExecutor initTaskExecutor(final int poolSize, final String namePrefix) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(poolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(poolSize);
        // 设置队列容量
        executor.setQueueCapacity(1024);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix(namePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        return executor;
    }

}
