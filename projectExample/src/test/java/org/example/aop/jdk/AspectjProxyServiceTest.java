package org.example.aop.jdk;

import org.example.aop.DefaultTestService;
import org.example.aop.ITestService;
import org.example.aop.aspect.AspectjProxyService;
import org.junit.Test;

public class AspectjProxyServiceTest {

    @Test
    public void getProxyObject() {
        DefaultTestService defaultTestService = new DefaultTestService();
        DefaultTestService userLogProxy = (DefaultTestService) new AspectjProxyService().getUserLogProxy(defaultTestService);

        userLogProxy.test();

        userLogProxy.testClazz();

    }
}