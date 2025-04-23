package com.beiming.demo.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RedisController
 */
@RestController
@RequestMapping("redis")
public class RedisController {
    private static final RedisScript<Long> INCREX_SCRIPT = RedisScript.of(
            """
                    local currentTime = redis.call('TIME')
                    local timestamp = currentTime[1]
                    local microseconds = currentTime[2]
                    return timestamp
                    """
            , Long.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("incrx")
    public Number increx(){
      return   stringRedisTemplate.execute(INCREX_SCRIPT, List.of("aaaaaa"));
    }

}
