package com.leige.blog.mapper;

import com.leige.blog.model.SysResource;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface SysResourceMapper extends Mapper<SysResource> {

   public List<SysResource> selectSysPermissionByRoleId(String rid);

   public List<SysResource> selectByRoleValue(String roleValue);

   public List<SysResource> selectByType(Integer type);
}