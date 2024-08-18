package io.zl.test.juc;

import java.util.StringJoiner;
import java.util.concurrent.*;


public class DynamicExecutorBean extends ThreadPoolExecutor {
    public DynamicExecutorBean(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        System.out.println("setCoreSize"+coreSize);
        this.coreSize = coreSize;
        executor.setCorePoolSize(coreSize);

    }

    @Override
    public String toString() {
        return new StringJoiner(", ","123" + "[", "]")
                .add("executor=" + executor)
//                .add("coreSize=" +executor.getCorePoolSize())
//                .add("MaxSize="+executor.getMaximumPoolSize())
//                .add("queue.size="+executor.getQueue().size())
                .toString();
    }
}
