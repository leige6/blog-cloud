package com.leige.blog.service;

/**
 * Created by Administrator on 2018/6/3.
 */
public interface LoginAttemptService {
    public void loginSucceeded(String key);
    public void loginFailed(String key);
    public boolean isBlocked(String key);
}
