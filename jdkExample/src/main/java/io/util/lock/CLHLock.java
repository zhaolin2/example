package io.util.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> localPred;
    ThreadLocal<QNode> localNode;

    public CLHLock(){
        tail=new AtomicReference<>(null);
        localNode =ThreadLocal.withInitial(()->{
            return new QNode();
        });

        localPred =ThreadLocal.withInitial(()->{
            return null;
        });
    }

    @Override
    public void lock() {
        QNode node = this.localNode.get();
        node.lock=true;
        QNode pred = tail.getAndSet(node);

        localPred.set(pred);

        //一直自旋 等待锁释放 适合锁占用时间短的情况
        while (pred.lock){

        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        QNode node = localNode.get();
        node.lock=false;
        localNode.set(localPred.get());
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
