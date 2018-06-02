package com.leige.blog.controller;

import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.model.SysUser;
import com.leige.blog.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login/auth", method = {RequestMethod.POST})
    public Result login(String username, String password) throws AuthenticationException{
        if(StringUtils.isEmpty(username)){
            return  ResultUtil.fail(ResultEnum.USERNAME_NOT_BLANK);
        }
        if(StringUtils.isEmpty(password)){
            return  ResultUtil.fail(ResultEnum.PASSWORD_NOT_BLANK);
        }

        HashMap<String,Object> data=new HashMap<>();
       // data.put("token",token);
        return  ResultUtil.success(ResultEnum.LOGIN_IN_SUCCESS,data);
    }

    @RequestMapping(value = "/login/register", method = RequestMethod.POST)
    public SysUser register(@RequestBody SysUser addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }
}
