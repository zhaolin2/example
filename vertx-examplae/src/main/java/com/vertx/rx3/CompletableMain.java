package com.vertx.rx3;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

//只关心完成或者错误事件
public class CompletableMain {
    public static void main(String[] args) {
        Completable.create(new CompletableOnSubscribe() {//被观察者

            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                e.onComplete();//单一onComplete或者onError
            }

        }).subscribe(new CompletableObserver() {//观察者
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: ");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
