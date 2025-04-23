package online.goudan.util;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author goudan
 * @date 2023/7/31 10:02
 * @desc ThreadLocalUtil
 */
public class ThreadLocalUtil {
    private static final Map<String, ThreadLocal<Object>> THREADLOCAL_CACHE = new ConcurrentHashMap<>();

    public static <T> void set(String serviceName, T data) {
        ThreadLocal<Object> threadLocal = getThreadLocal(serviceName);
        threadLocal.set(data);
    }

    public static <T> T get(String serviceName) {
        ThreadLocal<Object> threadLocal = getThreadLocal(serviceName);
        return (T) threadLocal.get();
    }

    public static void remove(String serviceName) {
        ThreadLocal<Object> threadLocal = getThreadLocal(serviceName);
        threadLocal.remove();
    }

    private static ThreadLocal<Object> getThreadLocal(String serviceName) {
        ThreadLocal<Object> threadLocal = THREADLOCAL_CACHE.get(serviceName);
        if (null == threadLocal) {
            synchronized (THREADLOCAL_CACHE) {
                if (null == THREADLOCAL_CACHE.get(serviceName)) {
                    threadLocal = new ThreadLocal<>();
                    THREADLOCAL_CACHE.put(serviceName, threadLocal);
                }
            }
        }
        return threadLocal;
    }
}
