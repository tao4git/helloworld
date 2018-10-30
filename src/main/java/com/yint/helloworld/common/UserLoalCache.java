package com.yint.helloworld.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yint.helloworld.domain.ShareBasic;
import com.yint.helloworld.domain.User;

import java.util.concurrent.TimeUnit;

public class UserLoalCache {

    private static Long expireTime = 10L;
    private static Cache<String,User> userCache = null;
    private static Caffeine<Object,Object> shareBasicCache = Caffeine.newBuilder();

    public static void setExpireTime(Long time){
        expireTime = time;
    }

    public static Cache<String,String> getBasicShareCache(){
        return shareBasicCache.expireAfterAccess(1,TimeUnit.DAYS).build();
    }


    private static Cache<String,User> getUserCache(){
        if(null == userCache){
            userCache = Caffeine.newBuilder()
                    .expireAfterAccess(expireTime, TimeUnit.SECONDS)
                    .build();
        }
        return userCache;
    }

    public static void saveUser(User user){
        getUserCache().put(user.getUname(),user);
    }

    public static User getUser(String key){
        return getUserCache().getIfPresent(key);
    }

}
