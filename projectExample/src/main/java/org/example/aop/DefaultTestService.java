package org.example.aop;

public class DefaultTestService  implements ITestService {
    @Override
    public void test() {
        System.out.println("this is the default Service");
    }


    public void testClazz(){
        System.out.println("this is the default testClazz");
    }
}
