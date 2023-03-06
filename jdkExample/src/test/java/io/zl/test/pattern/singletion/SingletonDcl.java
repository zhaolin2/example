package io.zl.test.pattern.singletion;

import java.util.Objects;

public class SingletonDcl {

    /**
     * read、load、use动作必须连续出现。
     * assign、store、write动作必须连续出现。
     * <p>
     * 还有lock unlock
     */
    private static volatile SingletonDcl instance;

    private SingletonDcl() {
        Object o = new Object();
    }

    public static SingletonDcl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (SingletonDcl.class) {
                if (Objects.isNull(instance)) {
                    /**
                     * 1.分配内存空间
                     * 2.初始化对象(包括赋值属性等)
                     * 3.设置instance为指向申请的内存空间
                     *
                     * 当执行一半时 可能另外的线程会进行判断 这个时候不需要拿到锁就直接返回了
                     */
                    instance = new SingletonDcl();

                    
                    System.out.println("instance:" + instance);
                }
            }
        }

        return instance;
    }

}
