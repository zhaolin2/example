package org.example;

import org.example.aop.DefaultTestService;
import org.example.aop.ITestService;
import org.example.aop.jdk.JdkProxyService;

public class Main {
    public static void main(String[] args) {
        ITestService defaultTestService = new DefaultTestService();
        JdkProxyService jdkProxyService = new JdkProxyService(defaultTestService);
        ITestService proxyObject = jdkProxyService.getProxyObject();
        proxyObject.test();
    }
}