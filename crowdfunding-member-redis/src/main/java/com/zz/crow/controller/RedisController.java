package com.zz.crow.controller;

import com.zz.crow.response.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("set/redis/value/remote")
    public ResultEntity<String> setRedisValueRemote(@RequestParam("key") String key, @RequestParam("value") String value){
        if(StringUtils.isEmpty(key)){
            return ResultEntity.failed("key is empty");
        }
        redisTemplate.opsForValue().set(key, value);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("set/redis/value/timeout/remote")
    public ResultEntity<String> setRedisValueWithTimeoutRemote(@RequestParam("key") String key,
                                                        @RequestParam("value") String value,
                                                        @RequestParam("timeout") long timeout,
                                                        @RequestParam("timeUnix") TimeUnit timeUnit){

        if(StringUtils.isEmpty(key)){
            return ResultEntity.failed("key is empty");
        }
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("get/redis/value/remote")
    public ResultEntity<String> getRedisValueRemote(@RequestParam("key") String key){
        if(StringUtils.isEmpty(key)){
            return ResultEntity.failed("key is empty");
        }
        String value = redisTemplate.opsForValue().get(key);
        return ResultEntity.successWithData(value);
    }

    @RequestMapping("del/redis/value/remote")
    public ResultEntity<String> delRedisValueRemote(@RequestParam("key") String key){
        if(StringUtils.isEmpty(key)){
            return ResultEntity.failed("key is empty");
        }
        redisTemplate.delete(key);
        return ResultEntity.successWithoutData();
    }
}
