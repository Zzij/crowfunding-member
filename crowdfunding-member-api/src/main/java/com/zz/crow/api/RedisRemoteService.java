package com.zz.crow.api;

import com.zz.crow.response.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@FeignClient("zz-crow-redis")
public interface RedisRemoteService {

    @RequestMapping("set/redis/value/remote")
    ResultEntity<String> setRedisValueRemote(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping("set/redis/value/timeout/remote")
    ResultEntity<String> setRedisValueWithTimeoutRemote(@RequestParam("key") String key,
                                                        @RequestParam("value") String value,
                                                        @RequestParam("timeout") long timeout,
                                                        @RequestParam("timeUnix") TimeUnit timeUnit);

    @RequestMapping("get/redis/value/remote")
    ResultEntity<String> getRedisValueRemote(@RequestParam("key") String key);

    @RequestMapping("del/redis/value/remote")
    ResultEntity<String> delRedisValueRemote(@RequestParam("key") String key);
}
