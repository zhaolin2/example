package io.zl.test.thread.comm.wait;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class WaitNotifyExample {

    static final Object lock=new Object();

    Integer MAX=27;
    Integer index=0;


    @Test
    public void waitTest() throws InterruptedException {
        Thread threadA = new Thread(()-> {
            while (index<=MAX){
                try {
                    runnable(0);
                } catch (InterruptedException e) {
                    System.out.println("线程被打断"+e.getMessage());
                    break;
                }
            }

        },"threadA");
//        threadA.start();


        Thread threadB = new Thread(()->{
            while (index<=MAX){
                try {
                    runnable(1);
                } catch (InterruptedException e) {
                    System.out.println("线程被打断"+e.getMessage());
                    break;
                }
            }
        },"threadB");
        threadB.start();

        threadA.start();

        threadA.join();
        threadB.join();;


        System.out.println("执行完毕");
    }


    private void runnable(Integer num) throws InterruptedException {
        try {
            synchronized (lock){
                if (index % 2 ==num){
                    Thread.sleep(50);
                    System.out.println(Thread.currentThread().getName()+"获取到锁，准备打印.[atomic:"+index++);

                    lock.notify();
                }else {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"开始等待");
                }
            }


        } catch (Exception e) {
            System.out.println("打印错误"+e.getMessage());
            lock.notifyAll();
            throw e;
        }
    }
}
