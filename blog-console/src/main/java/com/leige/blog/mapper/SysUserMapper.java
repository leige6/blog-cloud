package com.leige.blog.mapper;

import com.leige.blog.model.SysUser;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Component
public interface SysUserMapper extends Mapper<SysUser> {

    public SysUser getByUserName(String userName);

    public SysUser getByUserId(Long id);

    public List<SysUser> selectDataGrid(Map params);

}
