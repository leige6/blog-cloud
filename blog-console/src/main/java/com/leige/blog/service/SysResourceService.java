package com.leige.blog.service;

import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysResource;

import java.util.List;

/**
 *
 * Resource 表数据服务层接口
 *
 */
public interface SysResourceService {

    List<SysResource> selectAll();

    List<SysResource> selectAllUserRes();

    List<SysResource> selectByRoleValue(String roleValue);

    List<SysResource> selectByUserId(long userId);

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(Long id);

    int insertSysResource(SysResource sysResource);

    int editSysResource(SysResource sysResource);

    int daleteSysResource(long id);

    SysResource selectByid(long id);
}