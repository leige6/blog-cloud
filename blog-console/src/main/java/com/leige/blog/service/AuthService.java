package com.leige.blog.service;

import com.leige.blog.model.SysUser;

/**
 * Created by Administrator on 2018/5/27.
 */
public interface AuthService {
    SysUser register(SysUser userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
