package io.zl.test.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExample {
    public static void main(String[] args) throws InterruptedException {
        int threadCount=12;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(()->{
                try {
                    System.out.println(finalI +":123");
                }catch (Exception e){
                    System.out.println("异常发生"+e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        System.out.println("执行完成");
        executorService.shutdown();
    }
}
