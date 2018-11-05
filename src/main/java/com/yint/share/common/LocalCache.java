package com.yint.share.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class LocalCache {

    private static Long expireTime = 10L;
    private static Cache<String,String> shareBasicCache;
    private static Cache<String,String> shareDayCache;
    private static Cache<String,String> tradeDayCache;

    public static Cache<String,String> getBasicShareCache(){
        if(null != shareBasicCache){
            return shareBasicCache;
        }
        shareBasicCache = Caffeine.newBuilder()
                .expireAfterAccess(1,TimeUnit.DAYS).build();
        return shareBasicCache;
    }

    public static Cache<String,String> getShareDayCache(){
        if(null != shareDayCache){
            return shareDayCache;
        }
        shareDayCache = Caffeine.newBuilder()
                .expireAfterAccess(1,TimeUnit.DAYS).build();
        return shareDayCache;
    }

    public static Cache<String,String> getTradeDayCache(){
        if(null != tradeDayCache){
            return tradeDayCache;
        }
        tradeDayCache = Caffeine.newBuilder()
                .expireAfterAccess(300,TimeUnit.DAYS).build();
        return tradeDayCache;
    }

}
