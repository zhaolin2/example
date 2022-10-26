package com.vertx.rx3;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

//onSuccess或者onError
public class MaybeMain {
    public static void main(String[] args) {
        //被观察者
        Maybe<String> maybe = Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> e) throws Exception {
                e.onSuccess("test");//发送一个数据的情况，或者onError，不需要再调用onComplete(调用了也不会触发onComplete回调方法)
                //e.onComplete();//不需要发送数据的情况，或者onError
            }
        });

        //订阅观察者
        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                //发送一个数据时，相当于onNext和onComplete，但不会触发另一个方法onComplete
                System.out.println("success,s:"+ s);
            }

            @Override
            public void onComplete() {
                //无数据发送时候的onComplete事件
                System.out.println("onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
