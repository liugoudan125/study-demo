package online.goudan.util;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author goudan
 * @date 2023/8/4 14:28
 * @desc CGLibUtil
 */
public class CGLibUtil {

    public static <T> T createProxyObject(T target, Class<T> tClass, ProxyHandler proxyHandler) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                proxyHandler.before(method, objects);
                Object returnValue = methodProxy.invoke(target, objects);
                return proxyHandler.after(returnValue);
            }
        });
        return (T) enhancer.create();
    }

    public  interface ProxyHandler {
        void before(Method method, Object[] objects);

        default Object after(Object returnValue) {
            return returnValue;
        }
    }
}
