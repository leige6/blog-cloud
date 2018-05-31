package com.leige.blog.security;

import com.leige.blog.common.utils.RedisUtil;
import com.leige.blog.model.SysResource;
import com.leige.blog.model.SysUser;
import com.leige.blog.security.jwt.JwtUser;
import com.leige.blog.security.jwt.JwtUserFactory;
import com.leige.blog.service.SysResourceService;
import com.leige.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/24  12:28
 */
@Component
public class CustomUserService implements UserDetailsService {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private RedisUtil redisUtil;

    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserService.getByUserName(username);
        if (user != null) {
            List<SysResource> permissions = sysResourceService.selectByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (SysResource permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getValue());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            JwtUser jwtUser=JwtUserFactory.create(user,grantedAuthorities);
            redisUtil.setValue(username,jwtUser);
            return jwtUser;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }


}

