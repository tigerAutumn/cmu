package cc.newex.wallet.task;

import cc.newex.wallet.annotation.StartThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Slf4j
@Component
public class SignContent implements InitializingBean, ApplicationContextAware {
    private ApplicationContext context;
    private final int THREAD_COUNT = 5;
    ExecutorService pool = new ThreadPoolExecutor(this.THREAD_COUNT, this.THREAD_COUNT * 2, 5,
            TimeUnit.MINUTES, new ArrayBlockingQueue<>(this.THREAD_COUNT));

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, Object> threads = this.context.getBeansWithAnnotation(StartThread.class);
        threads.forEach((key, obj) -> {
            this.pool.execute((Runnable) obj);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SignContent.log.info("ExecutorService quit begin");
            this.pool.shutdown();
            SignContent.log.info("ExecutorService quit");
        }));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
