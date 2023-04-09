package io.zl.test.future;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class FutureList {
    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("345");


        CompletableFuture<Void>[] futures = strings.stream().map(FutureList::getAsync).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures);

        System.out.println("future执行完毕");
    }

    static public CompletableFuture<Void> getAsync(String s){
        return CompletableFuture.runAsync(()->{
            System.out.println("s:"+s);
        });
    }
}
