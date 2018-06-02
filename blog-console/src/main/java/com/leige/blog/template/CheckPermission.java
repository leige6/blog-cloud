package com.leige.blog.template;

import com.leige.blog.security.jwt.JwtUser;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/2.
 */
@Component
public class CheckPermission implements TemplateDirectiveModel {



    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        // 禁止循环变量
        if (loopVars.length != 0) {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }
        Object paramValue = params.get("permission");
        String permission = ((SimpleScalar) paramValue).getAsString();
        if(StringUtils.isEmpty(permission)){
            return;
        }
        HttpServletRequest req=getRequest();
        SecurityContext context=(SecurityContext)req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication=context.getAuthentication();
        if (null == authentication) {
            return;
        }
        List<GrantedAuthority > authorities=(List<GrantedAuthority >)authentication.getAuthorities();
        boolean flag = false;
       for(GrantedAuthority ga : authorities) {//authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
            if(permission.trim().equals(ga.getAuthority())) {
                 body.render(env.getOut());
                 flag = true;
                 break;
            }
        }
    }


    private HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        return request;
    }
}
