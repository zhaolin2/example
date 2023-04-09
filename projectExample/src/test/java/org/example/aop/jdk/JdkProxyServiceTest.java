package org.example.aop.jdk;

import org.example.aop.DefaultTestService;
import org.example.aop.ITestService;
import org.junit.Test;

public class JdkProxyServiceTest {

    @Test
    public void getProxyObject() {
        ITestService defaultTestService = new DefaultTestService();
        JdkProxyService jdkProxyService = new JdkProxyService(defaultTestService);
        ITestService proxyObject = jdkProxyService.getProxyObject();
        proxyObject.test();
    }
}