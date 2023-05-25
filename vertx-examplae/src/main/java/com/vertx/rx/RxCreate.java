//package com.vertx.rx;
//
//import io.reactivex.Observable;
//import io.reactivex.Single;
//import io.reactivex.SingleEmitter;
//import io.reactivex.SingleOnSubscribe;
//import io.reactivex.annotations.NonNull;
//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;
//
//
//public class RxCreate {
//
//    public static void main(final String[] args) {
//        Single.create(new SingleOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(final SingleEmitter<Integer> singleEmitter)
//                    throws Exception {
//                singleEmitter.onSuccess(120);
//            }
//        });
//
//        Single.create(item -> item.onSuccess(120));
//
////        Observable<String> observable= Observable.create(observableEmitter -> {
////            observableEmitter.onNext("123");
////            observableEmitter.onNext("456");
////            observableEmitter.onComplete();
////        });
////
////        Subscriber<? extends String> subscriber=new Subscriber<String>() {
////            @Override
////            public void onSubscribe(Subscription subscription) {
////
////            }
////            @Override
////            public void onNext(String s) {
////                System.out.println("获取到next,s:"+s);
////            }
////
////            @Override
////            public void onError(Throwable throwable) {
////
////            }
////
////            @Override
////            public void onComplete() {
////
////            }
////        };
////
////        observable.subscribe(subscriber);
//    }
//}
