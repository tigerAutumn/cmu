package cc.newex.dax.users.common.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration("myEventBusConfig")
public class EventBusConfig {

    @Bean("myAsyncEventBus")
    public EventBus asyncEventBus() {
        final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("async-event-%d")
                .build();

        final ExecutorService pool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(), 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(3000), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return new AsyncEventBus("AsyncEventBus", pool);
    }
}

