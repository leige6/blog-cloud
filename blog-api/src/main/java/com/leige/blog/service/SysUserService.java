package com.leige.blog.service;


import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/1  14:36
 */
public interface SysUserService {
    int insertSysUser(SysUser sysUser);
    int daleteSysUser(long id);
    SysUser selectByid(long id);
    public List<SysUser> getSysUsers();
    public int upadteSysUser(SysUser sysUser);
    public SysUser getByUserName(String userName);

    PageResult selectDataGrid(Map params, Integer page, Integer rows, String sort, String order);
}
