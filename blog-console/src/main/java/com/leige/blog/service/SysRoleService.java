package com.leige.blog.service;


import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysRole;

import java.util.List;

/**
 *
 * Role 表数据服务层接口
 *
 */
public interface SysRoleService {

    PageResult selectDataGrid(Integer page, Integer rows, String sort, String order);

    /*List<Tree> selectAllTree();

    List<Tree> selectTree(CurrentUser currentUser);*/

    int insertSysRole(SysRole sysRole);

    int editSysRole(SysRole sysRole);

    int daleteSysRole(long id);

    SysRole selectByid(long id);

    public List<Long> selectResourceIdListByRoleId(Long id);

    int updateSysRoleResource(Long id, String resourceIds);

    List<Tree> selectAllTree();

    List<SysRole> selectAll();

}