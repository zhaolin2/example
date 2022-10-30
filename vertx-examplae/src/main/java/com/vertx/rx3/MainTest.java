package com.vertx.rx3;

import java.math.BigDecimal;

public class MainTest {
    public static void main(String[] args) {
        BigDecimal t1=new BigDecimal("1740");
        BigDecimal t2=new BigDecimal("0.664");
        BigDecimal t3=new BigDecimal("0.795");

        System.out.println(t1.multiply (BigDecimal.ONE.add(t2.multiply(t3))));

        //2658.511200

        //2628
        //2584
        //2658
    }
}
