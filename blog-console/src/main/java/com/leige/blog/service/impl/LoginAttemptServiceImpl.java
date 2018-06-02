package com.leige.blog.service.impl;

import com.leige.blog.common.utils.RedisUtil;
import com.leige.blog.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/3.
 */
@Service
public class LoginAttemptServiceImpl  implements LoginAttemptService {
    private final int MAX_ATTEMPT = 3;
    private static  final String failNameHead="loginFailCount:";
    @Autowired
    private RedisUtil redisUtil;

    public LoginAttemptServiceImpl() {

    }

    public void loginSucceeded(String key) {
        redisUtil.deleteKey(failNameHead+key);
    }

    public void loginFailed(String key) {
        redisUtil.increment(failNameHead+key,1L);
        redisUtil.expire(failNameHead+key,60L, TimeUnit.SECONDS);
    }

    public boolean isBlocked(String key) {
        Integer attempts=(Integer)redisUtil.getValue(failNameHead+key);
        return attempts==null?false:(attempts>= MAX_ATTEMPT);
    }
}
