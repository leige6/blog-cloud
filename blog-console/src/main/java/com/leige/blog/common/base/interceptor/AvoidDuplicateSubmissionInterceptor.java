package com.leige.blog.common.base.interceptor;

import com.leige.blog.common.base.annotation.AvoidDuplicateSubmission;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
  * @Title:
  * @Description: 防止重复提交过滤器
 * @param
  * @return
  * @author 张亚磊
  * @date 2018年03月15日 10:10:46
  */
@Component
public class AvoidDuplicateSubmissionInterceptor implements HandlerInterceptor {
    private static final Logger LOG = Logger.getLogger(AvoidDuplicateSubmissionInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//        User user = UserUtil.getUser(); 
//        if (user != null) { 
    	if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
            if (annotation != null) { 
                boolean needSaveSession = annotation.needSaveToken(); 
                if (needSaveSession) { 
                    request.getSession(false).setAttribute("token", generateToken(request));
                }
                boolean needRemoveSession = annotation.needRemoveToken(); 
                if (needRemoveSession) { 
                    if(isRepeatSubmit(request)) {
                        String xmlHttpRequest = request.getHeader("X-Requested-With");
                        if (StringUtils.isNotBlank(xmlHttpRequest)) {
                            if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
                                response.setHeader("duplicateSubmit", "505");
                                return false;
                            }
                        }
                        LOG.warn("请不要重复提交,url:" + request.getServletPath());
                        return false;
                    }
                    request.getSession(false).removeAttribute("token"); 
                } 
            } 
        } 
        return true; 
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) { 
            return true; 
        } 
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) { 
            return true; 
        } 
        if (!serverToken.equals(clinetToken)) { 
            return true;
        } 
        return false; 
    }
    private synchronized static String generateToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            byte id[] = session.getId().getBytes();
            byte now[] =
                new Long(System.currentTimeMillis()).toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(id);
            md.update(now);
            return (toHex(md.digest()));
        } catch (IllegalStateException e) {
            return (null);
        } catch (NoSuchAlgorithmException e) {
            return (null);
        }
    }
    
    private static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);

        for (int i = 0; i < buffer.length; i++) {
           sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }

        return sb.toString();
   }
  
}