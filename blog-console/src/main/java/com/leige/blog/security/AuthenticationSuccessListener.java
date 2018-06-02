package com.leige.blog.security;

import com.leige.blog.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/6/3.
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        SecuritySysUser account = (SecuritySysUser)authenticationSuccessEvent.getAuthentication().getPrincipal();
        loginAttemptService.loginSucceeded(account.getUsername());
    }
}
