package com.leige.blog.mapper;

import com.leige.blog.model.SysRole;
import com.leige.blog.model.SysUserRole;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Component
public interface SysRoleMapper extends Mapper<SysRole> {
      public List<SysRole> selectSysRoleByUserId(String uid);

      public List<SysRole> selectDataGrid(Map params);

      public List<Long> selectResourceIdListByRoleId(Long rid);

      public int insertSysUserRoleByBatch(List<SysUserRole> sysUserRoles);

      public int deleteByUserId(long userId);
}