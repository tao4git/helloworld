package com.yint.helloworld.caffcache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * caffeine缓存使用
 */
public class LocalCache {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, Object> manualCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();

        String key = "yintao";

        manualCache.put(key,System.currentTimeMillis());

        System.out.println(manualCache.getIfPresent(key));
        Thread.sleep(6000);
        System.out.println(manualCache.getIfPresent(key));
    }
}
