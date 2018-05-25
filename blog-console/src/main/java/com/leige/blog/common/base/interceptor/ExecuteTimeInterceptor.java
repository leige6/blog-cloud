package com.leige.blog.common.base.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ExecuteTimeInterceptor implements HandlerInterceptor {

	private static final Log logger = LogFactory.getLog(ExecuteTimeInterceptor.class);
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	private Date processStartTime = null;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		processStartTime = new Date();
		String path = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
		logger.info("Execute Time：" + dateFormat.format(new Date()) + " Execute Request：" + path);
		return true;
	}



	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		Date processEndTime = new Date();
		logger.info("Execute Duration：" + (processEndTime.getTime() - processStartTime.getTime()) + "ms");
	}
}
