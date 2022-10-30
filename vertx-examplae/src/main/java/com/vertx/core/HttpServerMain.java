package com.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class HttpServerMain extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new HttpServerMain());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setTcpKeepAlive(true).setPort(8080);

        HttpServer httpServer = vertx.createHttpServer(httpServerOptions);

        Router router = Router.router(vertx);

        router.post("/test").handler(routerContext -> {
            HttpServerRequest request = routerContext.request();
            HttpServerResponse response = routerContext.response();
        });

        httpServer.requestHandler(reuqest -> {
            System.out.println("request:"+reuqest.absoluteURI());

            HttpServerResponse response = reuqest.response();

            if (reuqest.method().equals(HttpMethod.PUT)){
                response.send(reuqest);
                response.setStatusCode(200).send("OK");
            }else if (reuqest.method().equals(HttpMethod.POST)){
                response.send(reuqest);
            } else {
//                response.send(reuqest);
                response.setStatusCode(400)
                        .putHeader("content-type","text/html;charset=utf-8")
                        .send(Buffer.buffer("只支持put方法"));
            }
            response.end();
        }).listen(res -> {
            if (res.succeeded()){
                System.out.println("listener成功");
            }else {
                res.cause().printStackTrace();
                System.out.println("监听失败");
            }
        });
    }
}
