package online.goudan.interceptor;

import io.lettuce.core.RedisClient;
import io.netty.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.desktop.PreferencesHandler;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author lcl
 * @date 2023/8/23 9:31
 * @desc RateLimitInterceptor
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private RedisScript<Boolean> redisScript;

    {
        try {
            redisScript = new DefaultRedisScript<>(new ResourceScriptSource(new ClassPathResource("lua/rate.lua")).getScriptAsString(), Boolean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getName();
            String method = handlerMethod.getMethod().getName();
            String ip = request.getRemoteAddr();
            if (stringRedisTemplate.execute(redisScript, Arrays.asList("goudan:limit:" + className + "." + method + "(" + ip + ")"), String.valueOf(10), String.valueOf(5))) {
                return true;
            } else {
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
