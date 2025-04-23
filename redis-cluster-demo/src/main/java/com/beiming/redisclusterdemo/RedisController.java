package com.beiming.redisclusterdemo;

import jakarta.annotation.Resource;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RedisController
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RedisController.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private Redisson redisson;

    @PostMapping("set")
    public Boolean set(@RequestBody Map<String, String> map) {
        String key = map.get("key");
        String value = map.get("value");
        stringRedisTemplate.opsForValue().set(key, value);
        return true;
    }

    @GetMapping("get")
    public String get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        log.info("RedisController.get:  {}", value);
        return value;
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    AtomicInteger atomicInteger = new AtomicInteger();

    @GetMapping("rate")
    public String rate(String key, Integer rate) {
        RRateLimiter rRateLimiter = redisson.getRateLimiter("limiter:" + key);
        rRateLimiter.trySetRate(RateType.OVERALL, rate, 5, RateIntervalUnit.SECONDS);
        boolean b = rRateLimiter.tryAcquire();
        return rRateLimiter.availablePermits() + ": " + b;
    }
}
