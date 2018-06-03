package com.leige.blog.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.mapper.SysRoleMapper;
import com.leige.blog.mapper.SysUserMapper;
import com.leige.blog.model.SysUser;
import com.leige.blog.model.SysUserRole;
import com.leige.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/1  15:54
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public int insertSysUser(SysUser sysUser) {
        int res=sysUserMapper.insertSelective(sysUser);
        if(res>0){
            Long id = sysUser.getId();
            String[] roles = sysUser.getRoleIds().split(",");
            List<SysUserRole> sysUserRoles=new ArrayList<>();
            for (String string : roles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(id);
                sysUserRole.setRoleId(Long.valueOf(string));
                sysUserRoles.add(sysUserRole);
            }
            int res1=sysRoleMapper.insertSysUserRoleByBatch(sysUserRoles);
            res+=res1;
        }
        return res;
    }

    @Override
    public int daleteSysUser(long id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysUser selectByid(long id) {
        return sysUserMapper.getByUserId(id);
    }

    public List<SysUser> getSysUsers(){
        List<SysUser> result=sysUserMapper.selectAll();
        return  result;
    }

    @Override
    public int upadteSysUser(SysUser sysUser) {
        int res=sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(res>0&&sysUser.getRoleIds()!=null){
            sysRoleMapper.deleteByUserId(sysUser.getId());
            Long id = sysUser.getId();
            String[] roles = sysUser.getRoleIds().split(",");
            List<SysUserRole> sysUserRoles=new ArrayList<>();
            for (String string : roles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(id);
                sysUserRole.setRoleId(Long.valueOf(string));
                sysUserRoles.add(sysUserRole);
            }
            int res1=sysRoleMapper.insertSysUserRoleByBatch(sysUserRoles);
            res+=res1;
        }
        return res;
    }

    @Override
    public SysUser getByUserName(String userName) {
        return sysUserMapper.getByUserName(userName);
    }

    @Override
    public PageResult selectDataGrid(Map params, Integer page, Integer rows, String sort, String order) {
        PageHelper.startPage(page,rows);
        params.put("sort",sort);
        params.put("order",order);
        List<SysUser> sysUsers=sysUserMapper.selectDataGrid(params);
        PageInfo<SysUser> result=new PageInfo<SysUser>(sysUsers);
        PageResult pageResult=new PageResult();
        pageResult.setRows(sysUsers);
        pageResult.setTotal(result.getTotal());
        return pageResult;
    }

}
