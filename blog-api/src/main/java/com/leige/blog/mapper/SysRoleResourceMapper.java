package com.leige.blog.mapper;

import com.leige.blog.model.SysRoleResource;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface SysRoleResourceMapper extends Mapper<SysRoleResource> {

    public int deleteByRoleId(long rid);

    public void insertByBatch(List<SysRoleResource> sysRoleResources);
}