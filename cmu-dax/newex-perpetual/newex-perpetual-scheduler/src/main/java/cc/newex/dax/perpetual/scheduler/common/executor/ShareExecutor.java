package cc.newex.dax.perpetual.scheduler.common.executor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public abstract class ShareExecutor<I, T> implements BaseExecutor<I> {

    private final Integer THREAD_POOL_SIZE = 8;
    private final ExecutorService executorService = new ThreadPoolExecutor(this.THREAD_POOL_SIZE, this.THREAD_POOL_SIZE,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    @Override
    public void exec(final I param) throws Exception {
        final List<T> list = this.takeList(param);
        if (CollectionUtils.isEmpty(list)) {
            ShareExecutor.log.info("job list is empty.");
            return;
        }

        final Integer size = list.size() < this.getMaxPoolSize() ? list.size() : this.getMaxPoolSize();
        final CountDownLatch latch = new CountDownLatch(size);
        final List<Future<Boolean>> callbackList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final List<T> jl = this.shareJob(list, i, size);
            final ExecutorHandler handler = new ExecutorHandler(jl, param, latch);
            final Future<Boolean> future = this.executorService.submit(handler);
            callbackList.add(future);
        }
        latch.await();
        while (callbackList.size() > 0) {
            final Boolean result = callbackList.get(0).get();
            if (!result) {
                throw new RuntimeException("exec task failed");
            }
            callbackList.remove(0);
        }
    }

    protected Integer getMaxPoolSize() {
        return this.THREAD_POOL_SIZE;
    }

    /**
     * 获取任务列表
     *
     * @param param 参数
     * @return 任务列表
     */
    protected abstract List<T> takeList(I param);

    /**
     * 切片任务列表
     *
     * @param list  总任务列表
     * @param index 切片游标
     * @param size  总片数
     * @return 分片后的任务列表
     */
    protected List<T> shareJob(final List<T> list, final Integer index, final Integer size) {

        final int dataSize = list.size();
        final int len = (dataSize + (size - dataSize % size)) / size;
        final int fromIndex = index * len;
        final int toIndex = (index + 1) * len;
        return list.subList(fromIndex, Math.min(toIndex, dataSize));
    }

    /**
     * 分片执行任务
     *
     * @param list  切分后的任务列表
     * @param param 参数
     */
    protected abstract void shareExec(List<T> list, I param);

    /**
     * 任务执行器
     */
    private class ExecutorHandler implements Callable<Boolean> {

        private final CountDownLatch latch;
        private final List<T> jobList;
        private final I param;

        public ExecutorHandler(final List<T> jobList, final I param, final CountDownLatch latch) {
            this.jobList = jobList;
            this.param = param;
            this.latch = latch;
        }


        @Override
        public Boolean call() throws Exception {
            try {
                if (CollectionUtils.isEmpty(this.jobList)) {
                    return true;
                }
                ShareExecutor.this.shareExec(this.jobList, this.param);
            } catch (final Exception e) {
                ShareExecutor.log.error("do share job failed", e);
                return false;
            } finally {
                this.latch.countDown();
            }
            return true;
        }
    }
}
