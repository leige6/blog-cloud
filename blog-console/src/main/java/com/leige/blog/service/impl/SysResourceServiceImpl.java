package com.leige.blog.service.impl;


import com.leige.blog.common.ConstantStr;
import com.leige.blog.common.utils.RedisUtil;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.mapper.SysResourceMapper;
import com.leige.blog.model.SysResource;
import com.leige.blog.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * SysResource 服务层接口实现类
 *
 */
@Transactional
@Service
public class SysResourceServiceImpl implements SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public List<SysResource> selectAll() {
        return sysResourceMapper.selectAll();
    }

    @Override
    public List<SysResource> selectAllUserRes() {
        List<SysResource> all=(List<SysResource>)redisUtil.getValue("all_resource");
        if(all==null){
            all=sysResourceMapper.selectAllUserRes();
            redisUtil.setValue("all_resource",all);
        }
        return all;
    }

    @Override
    public List<SysResource> selectByRoleValue(String roleValue) {
        return sysResourceMapper.selectByRoleValue(roleValue);
    }

    @Override
    public List<SysResource> selectByUserId(long userId) {
        return sysResourceMapper.selectByUserId(userId);
    }

    public List<SysResource> selectByType(Integer type) {
        return sysResourceMapper.selectByType(type);
    }
    
    @Override
    public List<Tree> selectAllMenu() {
        List<Tree> trees = new ArrayList<Tree>();
        // 查询所有菜单
        List<SysResource> sysPermissions = this.selectByType(ConstantStr.MENU_YTPE);
        if (sysPermissions == null) {
            return trees;
        }
        for (SysResource sysResource : sysPermissions) {
            Tree tree = new Tree();
            tree.setId(sysResource.getId());
            tree.setPid(sysResource.getPid());
            tree.setText(sysResource.getName());
            tree.setIconCls(sysResource.getIcon());
            tree.setAttributes(sysResource.getUrl()==null?"":sysResource.getUrl());
            tree.setOpenMode(sysResource.getOpenMode());
            tree.setState(sysResource.getIsOpend());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectAllTree() {
        // 获取所有的资源 tree形式，展示
        List<Tree> trees = new ArrayList<Tree>();
        List<SysResource> sysPermissions = this.selectAll();
        if (sysPermissions == null) {
            return trees;
        }
        for (SysResource sysResource : sysPermissions) {
            Tree tree = new Tree();
            tree.setId(sysResource.getId());
            tree.setPid(sysResource.getPid());
            tree.setText(sysResource.getName());
            tree.setIconCls(sysResource.getIcon());
            tree.setAttributes(sysResource.getUrl()==null?"":sysResource.getUrl());
            tree.setOpenMode(sysResource.getOpenMode());
            tree.setState(sysResource.getIsOpend());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectTree(Long id) {
        List<Tree> trees = new ArrayList<Tree>();
        // shiro中缓存的用户角色
        List<SysResource>  menus=sysResourceMapper.selectByType(ConstantStr.MENU_YTPE);
        if (menus == null) {
            return trees;
        }
        for (SysResource sysResource : menus) {
            Tree tree = new Tree();
            tree.setId(sysResource.getId());
            tree.setPid(sysResource.getPid());
            tree.setText(sysResource.getName());
            tree.setIconCls(sysResource.getIcon());
            tree.setAttributes(sysResource.getUrl()==null?"":sysResource.getUrl());
            tree.setOpenMode(sysResource.getOpenMode());
            tree.setState(sysResource.getIsOpend());
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public int insertSysResource(SysResource sysResource) {
        return sysResourceMapper.insertSelective(sysResource);
    }

    @Override
    public int editSysResource(SysResource sysResource) {
        int res=sysResourceMapper.updateByPrimaryKeySelective(sysResource);
        return res;
    }

    @Override
    public int daleteSysResource(long id) {
        return sysResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysResource selectByid(long id) {
        return sysResourceMapper.selectByPrimaryKey(id);
    }

}