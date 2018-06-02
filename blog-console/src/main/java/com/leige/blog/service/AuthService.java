package com.leige.blog.service;

import com.leige.blog.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/5/27.
 */
public interface AuthService {
    SysUser register(SysUser userToAdd);
    void login(HttpServletRequest request, String username, String password);
}
