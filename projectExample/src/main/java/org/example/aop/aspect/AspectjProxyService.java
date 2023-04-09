package org.example.aop.aspect;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class AspectjProxyService implements MethodInterceptor {

    /**
     * 业务类对象，供代理方法中进行真正的业务方法调用
     */
    private Object target;

    public Object getUserLogProxy(Object target) {
        //给业务对象赋值
        this.target = target;



        CallbackHelper callbackHelper= new CallbackHelper(this.target.getClass(),new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getReturnType().equals(String.class)) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "hello cglib";
                        }
                    };
                } else {
                    return null;
                }
            }
        };


        //创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        enhancer.setSuperclass(this.target.getClass());
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
// log - before method
        System.out.println("[before] execute method: " + method.getName());

        // call method
        Object result = methodProxy.invokeSuper(o, args);

        // log - after method
        System.out.println("[after] execute method: " + method.getName() + ", return value: " + result);
        return null;
    }
}
