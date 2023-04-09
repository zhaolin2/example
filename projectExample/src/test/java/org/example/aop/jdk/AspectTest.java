package org.example.aop.jdk;

import net.sf.cglib.beans.*;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.reflect.MethodDelegate;
import net.sf.cglib.reflect.MulticastDelegate;
import org.example.aop.*;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class AspectTest {

    @Test(expected = IllegalStateException.class)
    public void testImmutableBean() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world!");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
        assertEquals("Hello world!", immutableBean.getValue());
        bean.setValue("Hello world, again!");
        assertEquals("Hello world, again!", immutableBean.getValue());
        immutableBean.setValue("你好 cglib!"); // 导致异常。
    }

    @Test
    public void testBeanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object myBean = beanGenerator.create();

        Method setter = myBean.getClass().getMethod("setValue", String.class);
        setter.invoke(myBean, "Hello cglib!");
        Method getter = myBean.getClass().getMethod("getValue");
        assertEquals("Hello cglib!", getter.invoke(myBean));
    }

    @Test
    public void testBeanCopier() throws Exception {
        BeanCopier copier = BeanCopier.create(SampleBean.class, OtherSampleBean.class, false);
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        OtherSampleBean otherBean = new OtherSampleBean();
        copier.copy(bean, otherBean, null);
        assertEquals("Hello cglib!", otherBean.getValue());
    }


    @Test
    public void testBulkBean() throws Exception {
        BulkBean bulkBean = BulkBean.create(SampleBean.class,
                new String[]{"getValue"},
                new String[]{"setValue"},
                new Class[]{String.class});
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world!");
        assertEquals(1, bulkBean.getPropertyValues(bean).length);
        assertEquals("Hello world!", bulkBean.getPropertyValues(bean)[0]);
        bulkBean.setPropertyValues(bean, new Object[] {"Hello cglib!"});
        assertEquals("Hello cglib!", bean.getValue());
    }


    @Test
    public void testBeanGeneratorWithBeanMap() throws Exception {
        SampleBean bean = new SampleBean();
        BeanMap map = BeanMap.create(bean);
        bean.setValue("Hello cglib!");
        assertEquals("Hello cglib", map.get("value"));
    }

    @Test
    public void testInterfaceMaker() throws Exception {
        Signature signature = new Signature("foo", Type.DOUBLE_TYPE, new Type[]{Type.INT_TYPE});
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(signature, new Type[0]);
        Class iface = interfaceMaker.create();
        assertEquals(1, iface.getMethods().length);
        assertEquals("foo", iface.getMethods()[0].getName());
        assertEquals(double.class, iface.getMethods()[0].getReturnType());
    }


    @Test
    public void testMethodDelegate() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(
                bean, "getValue", BeanDelegate.class);
        assertEquals("Hello world!", delegate.getValueFromDelegate());
    }


    @Test
    public void testMulticastDelegate() throws Exception {
        MulticastDelegate multicastDelegate = MulticastDelegate.create(
                DelegatationProvider.class);
        SimpleMulticastBean first = new SimpleMulticastBean();
        SimpleMulticastBean second = new SimpleMulticastBean();
        multicastDelegate = multicastDelegate.add(first);
        multicastDelegate = multicastDelegate.add(second);
        DelegatationProvider provider = (DelegatationProvider)multicastDelegate;
        provider.setValue("Hello world!");
        assertEquals("Hello world!", first.getValue());
        assertEquals("Hello world!", second.getValue());
    }
}
