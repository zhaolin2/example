package io.zl.test.valid;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

public class MethodValidTest {

    @Test
    public void testMethodValid() throws NoSuchMethodException {
        test("",0);
    }

    public void test(@NotBlank String name,@NotNull Integer t1) throws NoSuchMethodException {
//        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
//        StackTraceElement stackTrace = stackTraces[2];
//        String className = stackTrace.getClassName();
//        String methodName = stackTrace.getMethodName();
//
//        obtainExecutableValidator().validateParameters(this, MethodUtil.getMethod(Class.forName(className)),)

        // 校验逻辑
        Method currMethod = this.getClass().getMethod("test", String.class, Integer.class);
        Set<ConstraintViolation<MethodValidTest>> validResult = obtainExecutableValidator().validateParameters(this, currMethod, new Object[]{name,t1});
        if (!validResult.isEmpty()) {
            // ... 输出错误详情validResult
            validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
            throw new IllegalArgumentException("参数错误");
        }
        System.out.println("test");


        Set<ConstraintViolation<MethodValidTest>> validate = obtainValidator().validate(this, MethodValidTest.class);

    }

    // 用于方法校验的校验器
    private ExecutableValidator obtainExecutableValidator() {
        return obtainValidator().forExecutables();
    }

    // 用于Java Bean校验的校验器
    private Validator obtainValidator() {
        // 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 2、得到一个校验器
//        validatorFactory.
        return validatorFactory.getValidator();
    }
}
