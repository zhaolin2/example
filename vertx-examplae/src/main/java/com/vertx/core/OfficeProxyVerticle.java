package com.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerResponse;

/**
 * https://github.com/vert-x3/vertx-examples/blob/4.x/core-examples/src/main/java/io/vertx/example/core/http/proxy/Proxy.java
 */
public class OfficeProxyVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpClientOptions clientOptions = new HttpClientOptions();
        clientOptions.setDefaultHost("www.baidu.com");
        clientOptions.setDefaultPort(443);
        clientOptions.setKeepAlive(true);
        clientOptions.setTrustAll(true);

        HttpClient client = vertx.createHttpClient(clientOptions);

        vertx.createHttpServer().requestHandler(serverRequest -> {
            System.out.println("proxy request:"+serverRequest.absoluteURI());

            serverRequest.pause();
            HttpServerResponse serverResponse = serverRequest.response();

            client.request(serverRequest.method(),serverRequest.uri())
                    .onSuccess(clientRequest -> {
                        serverRequest.resume();

                        clientRequest.headers().setAll(serverRequest.headers());
                        clientRequest.send(serverRequest).onSuccess(clientResponse -> {
                            System.out.println("proxy response:"+clientResponse.statusCode());

                            serverResponse.setStatusCode(clientResponse.statusCode());
                            serverResponse.headers().setAll(clientResponse.headers());
                            serverResponse.send(clientResponse);
                        });
                    }).onFailure(err -> {
                System.out.println("cant connect");
                err.printStackTrace();
                serverResponse.setStatusCode(500);
            });

        }).listen(8080);
    }
}
