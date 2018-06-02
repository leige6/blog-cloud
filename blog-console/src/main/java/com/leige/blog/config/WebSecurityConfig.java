package com.leige.blog.config;

import com.leige.blog.common.utils.MD5Util;
import com.leige.blog.security.CustomUserService;
import com.leige.blog.security.MyFilterSecurityInterceptor;
import com.leige.blog.security.UnauthorizedEntryPoint;
import com.leige.blog.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Spring Security 配置类.
 * @author 张亚磊
 * @Description:
 * @date 2018/5/24  11:19
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedHandler;
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    protected void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.customUserService).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 对于获取token的rest api要允许匿名访问
                // 除上面外的所有请求全部需要鉴权认证
                .antMatchers("/**/*.shtml","/").authenticated().and().csrf().disable();
        // 添加JWT filter
        //httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();

     }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/login.shtml","/common/**","/login/**.shtml","/error.shtml","/api/**","/css/**", "/js/**","/image/**","/plugs/**","*/**/*.png","*/**/*.jpg","/**.ico");
    }



    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter jtAuthenticationTokenFilter=new JwtAuthenticationTokenFilter();
        return jtAuthenticationTokenFilter;
    }

}
