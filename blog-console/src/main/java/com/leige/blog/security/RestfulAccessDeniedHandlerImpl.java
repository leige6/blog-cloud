package com.leige.blog.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/25  16:32
 */
@Component
public class RestfulAccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        PrintWriter writer;
        String returnStr = "{exception:{name:'" + accessDeniedException.getClass()
                + "',message:'" + accessDeniedException.getMessage() + "'}}";
        System.out.println(this.getClass().toString()+":"+returnStr);
        writer = response.getWriter();
        writer.write(returnStr);
        writer.flush();
        writer.close();
    }

}

