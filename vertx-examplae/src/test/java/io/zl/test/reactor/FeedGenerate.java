package io.zl.test.reactor;

import jdk.jfr.EventFactory;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.List;

public class FeedGenerate {

    @Test
    public void fluxGenerate(){
        Flux<String> flux = Flux.generate(
                //初始状态
                () -> 0,
                //state 用来循环之中变化
                //sink 用来输出来后边的流
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });

        flux.subscribe(new SystemSubscriber());
    }

    //生产时 可以来自多个线程
//    public void fluxCreate(){
//
//
//        Flux<String> bridge = Flux.create(sink -> {
//            myEventProcessor.register(
//                    new MyEventListener<String>() {
//                        public void onDataChunk(List<String> chunk) {
//                            for(String s : chunk) {
//                                sink.next(s);
//                            }
//                        }
//
//                        public void processComplete() {
//                            sink.complete();
//                        }
//                    });
//        });
//    }


    //适合处理来自单个生产者的事件
//    public void pushTest(){
//        Flux<String> bridge = Flux.push(sink -> {
//            myEventProcessor.register(
//                    new SingleThreadEventListener<String>() {
//
//                        public void onDataChunk(List<String> chunk) {
//                            for(String s : chunk) {
//                                sink.next(s);
//                            }
//                        }
//
//                        public void processComplete() {
//                            sink.complete();
//                        }
//
//                        public void processError(Throwable e) {
//                            sink.error(e);
//                        }
//                    });
//        });
//    }


}
