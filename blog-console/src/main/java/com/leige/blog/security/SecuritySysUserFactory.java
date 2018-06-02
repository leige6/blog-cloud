package com.leige.blog.security;

import com.leige.blog.model.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public final class SecuritySysUserFactory {

    private SecuritySysUserFactory() {
    }

    public static SecuritySysUser create(SysUser user, List<GrantedAuthority> authorities) {
        return new SecuritySysUser(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                authorities,
                user.getLastPasswordResetDate()
        );
    }
}

