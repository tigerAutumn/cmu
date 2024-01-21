package cc.newex.dax.extra.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池封装
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
public class SimpleThreadPool {

    private static ExecutorService executorService;

    private static final int nThreads = 10;

    private SimpleThreadPool() {
    }

    static {
        init();
    }

    /**
     * 初始化全局线程池
     */
    synchronized public static void init() {
        if (executorService != null) {
            executorService.shutdownNow();
        }

        executorService = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 关闭公共线程池
     *
     * @param isNow 是否立即关闭而不等待正在执行的线程
     */
    synchronized public static void shutdown(final boolean isNow) {
        if (executorService != null) {
            if (isNow) {
                executorService.shutdownNow();
            } else {
                executorService.shutdown();
            }
        }

    }

    /**
     * 直接在公共线程池中执行线程
     *
     * @param runnable 可运行对象
     */
    public static void execute(final Runnable runnable) {
        try {
            executorService.execute(runnable);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 执行有返回值的异步方法<br>
     * Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
     *
     * @param <T>  执行的Task
     * @param task {@link Callable}
     * @return Future
     */
    public static <T> Future<T> submit(final Callable<T> task) {
        return executorService.submit(task);
    }

}
