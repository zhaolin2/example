package io.zl.test.thread.comm.semaphore;

import org.junit.Test;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    Integer MAX = 27;
    Integer index = 0;


    @Test
    public void semaphoreExample() throws InterruptedException {
        Semaphore semaphore0 = new Semaphore(1);
        Semaphore semaphore1 = new Semaphore(0);

        Thread threadA = new Thread(() -> {
            while (index <= MAX) {
                try {
                    runnable(semaphore0, semaphore1);
                } catch (InterruptedException e) {
                    System.out.println("线程被打断" + e.getMessage());
                    break;
                }
            }

        }, "threadA");


        Thread threadB = new Thread(() -> {
            while (index <= MAX) {
                try {
                    runnable(semaphore1, semaphore0);
                } catch (InterruptedException e) {
                    System.out.println("线程被打断" + e.getMessage());
                    break;
                }
            }
        }, "threadB");

        threadB.start();
        threadA.start();

        threadA.join();
        threadB.join();


        System.out.println("执行完毕");
    }

    private void runnable(Semaphore currentSemaphore, Semaphore nextSemaphore) throws InterruptedException {
        try {
            currentSemaphore.acquire();
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + "获取到锁，准备打印.[atomic:" + index++);

            nextSemaphore.release();
            System.out.println(Thread.currentThread().getName() + "开始等待");


        } catch (Exception e) {
            System.out.println("打印错误" + e.getMessage());
            throw e;
        }
    }
}
