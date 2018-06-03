package com.leige.blog.security.jwt;

import com.leige.blog.model.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(SysUser user,List<GrantedAuthority> authorities) {
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                authorities,
                user.getLastPasswordResetDate()
        );
    }
}

