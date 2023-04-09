package io.zl.test.thread.comm.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    Lock lock=new ReentrantLock();

//    AtomicInteger atomicInteger=new AtomicInteger(0);
    Integer index=0;
    Integer MAX=27;

    //要求A B轮流打印
    @Test
    public void testLockAndPrint() throws InterruptedException {

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
            lock.tryLock(1, TimeUnit.MINUTES);
            if (index % 2 ==num){
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName()+"获取到锁，准备打印.[atomic:"+index++);
            }else {
                System.out.println(Thread.currentThread().getName()+"获取到锁，空轮询");
            }

        } catch (Exception e) {
            System.out.println("打印错误"+e.getMessage());
            throw e;
        }finally {
            lock.unlock();
        }
    }


}
