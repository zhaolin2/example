package com.vertx.rx3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * io.reactivex.Flowable：发送0个N个的数据，支持Reactive-Streams和背压
 * io.reactivex.Observable：发送0个N个的数据，不支持背压，
 * io.reactivex.Single：只能发送单个数据或者一个错误
 * io.reactivex.Completable：没有发送任何数据，但只处理 onComplete 和 onError 事件。
 * io.reactivex.Maybe：能够发射0或者1个数据，要么成功，要么失败
 */
public class BaseAbleMain {
    public static void main(String[] args) {

        Consumer<String> consumer = System.out::println;
        Flowable.just("123").subscribe(consumer);

        Observable<String> observable=new Observable<String>() {
            @Override
            protected void subscribeActual(@NonNull Observer<? super String> observer) {
                observer.onNext("123");
                observer.onNext("456");
                observer.onComplete();
            }
        };

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("获取到next,s:"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("完成");
            }
        });

        Single.just("123").subscribe(consumer);

    }
}
