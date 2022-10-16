package com.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

import java.time.Instant;

public class BlockVerticle extends AbstractVerticle {

    @Override
    public void start() {
//        try {
//            System.out.println("sleep");
//            Thread.sleep(4000);
//        } catch (final Exception ex) {
//            ex.printStackTrace();
//        }
        periodSleep();
    }

    public void periodSleep(){
//        Vertx vertx = Vertx.vertx();
//        MessageProducer<String> messageProducer = vertx.eventBus().publisher("sleep");
        long timer = vertx.setPeriodic(200, id -> {
            System.out.println("sleep,id:" + id);
            //                Thread.sleep(2000);
            String message = "sleep,time:" + Instant.now().getEpochSecond();
            vertx.eventBus().<String>request("sleep",message,result -> {
                if (result.succeeded()){
                    System.out.println("发送消息成功，message:"+message);
                    Message<String> replyMessage = result.result();
                    System.out.println("获取到结果,reply:"+replyMessage.body());
                }else {
                    System.out.println("发送消息失败,message:"+message);
                }
            });
        });
//        vertx.cancelTimer(timer);
    }
}
