package com.vertx.rx3;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

public class SingleMain {
    public static void main(String[] args) {
        //被观察者
        Single<String> single = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                e.onSuccess("test");
                e.onSuccess("test2");//错误写法，重复调用也不会处理，因为只会调用一次
            }
        });

        //订阅观察者SingleObserver
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                //相当于onNext和onComplete
                System.out.println("success,s:"+s);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
