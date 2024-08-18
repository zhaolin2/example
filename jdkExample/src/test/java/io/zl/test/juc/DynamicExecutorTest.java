package io.zl.test.juc;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class DynamicExecutorTest {

    @Test
    public void test() throws InterruptedException {
        DynamicExecutorBean bean = new DynamicExecutorBean(3);
        ThreadPoolExecutor executor = bean.getExecutor();

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 1000; i++) {
                executor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + "::" + LocalDateTime.now() + "::begin---");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(threadName + "::"+ LocalDateTime.now() + "::intert--");
                    }
                    System.out.println(threadName + "::" + LocalDateTime.now() + "end---");
                });

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        TimeUnit.SECONDS.sleep(10);
        bean.setCoreSize(8);
        System.out.println("bean:"+bean);

        TimeUnit.SECONDS.sleep(10);
        bean.setCoreSize(3);
        System.out.println("bean:"+bean);




//        executor.shutdown();
//        executor.awaitTermination(600, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(30000);


    }

    private void addRunnable(ThreadPoolExecutor executor) {
        executor.execute(() -> {
//            for (int i = 0; i < 10; i++) {

//            }

        });
    }
}
