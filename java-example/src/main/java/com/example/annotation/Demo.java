package com.example.annotation;

import cn.hutool.core.annotation.AnnotationUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/7/28 15:43
 * @desc Demo
 */
public class Demo {

    public static void main(String[] args) throws Exception {
//        Method method = Demo.class.getDeclaredMethod("say");

//        MyValue myValue = AnnotatedElementUtils.getMergedAnnotation(method, MyValue.class);
//        System.out.println(myValue);

        Basic basic = new BasicImpl();
        Method method = basic.getClass().getMethod("say");
        MyNameValue annotation = AnnotatedElementUtils.findMergedAnnotation(method, MyNameValue.class);
        System.out.println(annotation);

        MyNameValue myNameValue = AnnotatedElementUtils.findMergedAnnotation(basic.getClass(), MyNameValue.class);
        System.out.println(myNameValue);

        //修改注解值

        Basic basic1 = (Basic) Proxy.newProxyInstance(Demo.class.getClassLoader(),
                new Class[]{Basic.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(method.getName().equals("say")){
                            System.out.println("我能说话了，不是哑巴了");
                        }
                        return null;
                    }
                });
       basic1.say();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Basic.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(method.getName().equals("say")){
                    System.out.println("我能说话了" );
                }
                Object invoke = methodProxy.invoke(basic1, objects);
                return invoke;
            }
        });
        Basic basic2 = (Basic) enhancer.create();
        basic2.say();
    }

    @MyNameValue(name = "hgfsaaaga")
    public void say() {

    }

}
