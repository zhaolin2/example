package com.vertx.verticle;

import java.util.ArrayList;

public class MianTest {
    public static void main(String[] args) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        arr1.add(1);

        ArrayList<Integer> arr2 = arr1;
        arr1.clear();

        System.out.println(arr2);
    }
}
