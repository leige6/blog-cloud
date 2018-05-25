package com.leige.blog.interceptor;

import com.leige.blog.common.utils.HTTPUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/25  10:14
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        if (HTTPUtils.isAjaxRequest(request)) {// AJAX请求,使用response发送403
            response.sendError(403);
        } else if (!response.isCommitted()) {// 非AJAX请求，跳转系统默认的403错误界面，在web.xml中配置
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    accessDeniedException.getMessage());
        }
    }
}
