package cc.newex.dax.perpetual.scheduler.common.proxy;

import cc.newex.dax.perpetual.scheduler.common.executor.ShareExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class ShareExecutorProxy<T> {

    private final List<T> list;

    private final Function<List<T>, Void> function;

    private final Integer maxPoolSize;

    public ShareExecutorProxy(final List<T> list, final Function<List<T>, Void> function) {
        this(list, function, null);
    }

    /**
     * 构建 切分任务
     *
     * @param list        需要批量处理的数据
     * @param function    批量处理方法
     * @param maxPoolSize 最大线程数
     */
    public ShareExecutorProxy(final List<T> list, final Function<List<T>, Void> function, final Integer maxPoolSize) {
        this.list = list;
        this.function = function;
        this.maxPoolSize = maxPoolSize;
    }

    public void exec() throws Exception {
        if (CollectionUtils.isEmpty(this.list)) {
            return;
        }
        new ShareExecutor<Void, T>() {
            @Override
            protected List<T> takeList(final Void param) {
                return ShareExecutorProxy.this.list;
            }

            @Override
            protected void shareExec(final List<T> list, final Void param) {
                ShareExecutorProxy.this.function.apply(list);
            }

            @Override
            protected Integer getMaxPoolSize() {
                return (ShareExecutorProxy.this.maxPoolSize == null) ? super.getMaxPoolSize() : ShareExecutorProxy.this.maxPoolSize;
            }
        }.exec(null);
    }

}
