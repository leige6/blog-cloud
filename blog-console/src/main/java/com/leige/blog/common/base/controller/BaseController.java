package com.leige.blog.common.base.controller;


import com.leige.blog.security.jwt.JwtUser;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/1  14:36
 */
public abstract class BaseController {


    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

	/**
	 * redirect跳转
	 * @param url 目标url
	 */
	protected String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}

	protected Long getUserId(HttpServletRequest request){
		SecurityContext context=(SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		JwtUser jwtUser=(JwtUser) context.getAuthentication().getPrincipal();
		return jwtUser.getId();
	}

}
