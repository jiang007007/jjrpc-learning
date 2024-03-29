package io.jiangjie;

import org.jiangjie.rpc.constants.RpcConstants;

import java.util.concurrent.*;

/**
 * 单例模式
 * 线程池
 */
public class ConcurrentThreadPool {
    private ThreadPoolExecutor threadPoolExecutor;

    private static volatile ConcurrentThreadPool instance;

    private ConcurrentThreadPool() {

    }

    private ConcurrentThreadPool(int corePoolSize, int maximumPoolSize) {
        if (corePoolSize <= 0) {
            corePoolSize = RpcConstants.DEFAULT_CORE_POOL_SIZE;
        }
        if (maximumPoolSize <= 0) {
            maximumPoolSize = RpcConstants.DEFAULT_MAXI_NUM_POOL_SIZE;
        }
        if (corePoolSize > maximumPoolSize) {
            maximumPoolSize = corePoolSize;
        }
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, RpcConstants.DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(RpcConstants.DEFAULT_QUEUE_CAPACITY));
    }

    public static ConcurrentThreadPool getInstance(int corePoolSize, int maximumPool) {
        if (instance == null) {
            synchronized (ConcurrentThreadPool.class) {
                if (instance == null) {
                    instance = new ConcurrentThreadPool(corePoolSize, maximumPool);
                }
            }
        }
        return instance;
    }

    public void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public <T> T submit(Callable<T> task) {
        Future<T> future = threadPoolExecutor.submit(task);
        if (future == null) {
            return null;
        }
        try {
            return future.get();
        } catch (Exception e) {

        }
        return null;
    }

    public void stop() {
        threadPoolExecutor.shutdown();
    }
}
