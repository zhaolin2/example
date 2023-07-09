package io.zl.test.promise;

import io.vertx.core.Future;

import java.util.ArrayList;

public class PromiseTest {

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("123");
        arrayList.add("gahdfghs");
        arrayList.add("fsghdfgds");

        arrayList.stream().map(ele -> Future.succeededFuture(ele)).forEach(ele -> {
            System.out.println(ele.result());
        });
    }
}
