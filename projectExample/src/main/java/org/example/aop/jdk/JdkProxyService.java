package org.example.aop.jdk;

import org.example.aop.ITestService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyService {

    ITestService target;
    public JdkProxyService(ITestService testService){
        this.target=testService;
    }
    public ITestService getProxyObject(){
        ClassLoader classLoader = target.getClass().getClassLoader();

        Class[] interfaces = {ITestService.class};

        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("准备反射调用目标方法");
                Object invoke = method.invoke(target, args);
                System.out.println("反射调用成功");
                return invoke;
            }
        };
        return (ITestService) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
