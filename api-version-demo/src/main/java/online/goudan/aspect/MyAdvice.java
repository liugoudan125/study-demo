package online.goudan.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.weaver.Advice;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lcl
 * @date 2023/8/11 18:04
 * @desc MyAdvice
 */
public class MyAdvice implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            System.out.println("1111111111111111111111111111111");
//            return methodProxy.invokeSuper(o, objects);
            return "aa";
        } finally {
            System.out.println("2222222222222222222222222222222222");
        }
    }
}
