package com.leige.blog.security;

import com.alibaba.fastjson.JSON;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.HTTPUtils;
import com.leige.blog.common.utils.result.ResultUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;




/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/24  12:28
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(HTTPUtils.isAjaxRequest(request)){
            String result=null;
            if(authException instanceof BadCredentialsException){
               result=JSON.toJSONString(ResultUtil.success(ResultEnum.NEED_LOGIN,null));
            }else{
                result=JSON.toJSONString(ResultUtil.success(ResultEnum.USER_UNAUTHOR,null));
            }
            response.setHeader("content-type", "application/json;charset=UTF-8");
            response.getOutputStream().write(result.getBytes("UTF-8"));
            response.getOutputStream().flush();
        }else{
            response.sendRedirect("/login");
        }

    }
}
