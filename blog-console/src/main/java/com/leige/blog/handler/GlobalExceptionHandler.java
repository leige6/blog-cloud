package com.leige.blog.handler;

import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.handler.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by BlueT on 2017/3/4.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView result=new ModelAndView("forward:/error.shtml");
        if(e instanceof  GlobalException){
            GlobalException globalException=(GlobalException)e;
            result.addObject("msg",globalException.getCm().getMsg());
            result.addObject("code",globalException.getCm().getCode());
        }else if(e instanceof AccessDeniedException){
            result.addObject("msg", ResultEnum.USER_UNAUTHOR.getMsg());
            result.addObject("code",ResultEnum.USER_UNAUTHOR.getCode());
        }
        return  result;
    }
}
