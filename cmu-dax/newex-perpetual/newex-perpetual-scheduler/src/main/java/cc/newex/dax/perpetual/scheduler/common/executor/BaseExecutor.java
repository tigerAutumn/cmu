package cc.newex.dax.perpetual.scheduler.common.executor;

public interface BaseExecutor<I> {

    /**
     * 执行任务
     *
     * @param param
     * @throws Exception
     */
    void exec(I param) throws Exception;
}
