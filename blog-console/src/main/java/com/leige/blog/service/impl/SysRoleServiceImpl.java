package com.leige.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.mapper.SysRoleMapper;
import com.leige.blog.mapper.SysRoleResourceMapper;
import com.leige.blog.model.SysRole;
import com.leige.blog.model.SysRoleResource;
import com.leige.blog.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * SysRole 服务层接口实现类
 *
 */
@Transactional
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public PageResult selectDataGrid(Integer page, Integer rows, String sort, String order) {
        PageHelper.startPage(page,rows);
        Map params=new HashMap<String,Object>();
        params.put("sort",sort);
        params.put("order",order);
        List<SysRole> sysRoles=sysRoleMapper.selectDataGrid(params);
        PageInfo<SysRole> result=new PageInfo<SysRole>(sysRoles);
        PageResult pageResult=new PageResult();
        pageResult.setRows(sysRoles);
        pageResult.setTotal(result.getTotal());
        return pageResult;
    }

    @Override
    public int insertSysRole(SysRole sysRole) {
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public int editSysRole(SysRole sysRole) {
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public int daleteSysRole(long id) {
        return sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysRole selectByid(long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return sysRoleMapper.selectResourceIdListByRoleId(id);
    }

    @Override
    public int updateSysRoleResource(Long rid, String resourceIds) {
        // 先删除后添加,有点爆力
        sysRoleResourceMapper.deleteByRoleId(rid);

        // 如果资源id为空，判断为清空角色资源
        if (StringUtils.isBlank(resourceIds)) {
            return 0;
        }
        List<SysRoleResource> sysRoleResources=new ArrayList<>();
        String[] resourceIdArray = resourceIds.split(",");
        for (String resourceId : resourceIdArray) {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(rid);
            sysRoleResource.setPerId(Long.parseLong(resourceId));
            sysRoleResources.add(sysRoleResource);
        }
        sysRoleResourceMapper.insertByBatch(sysRoleResources);
        return 1;
    }

    @Override
    public List<Tree> selectAllTree() {
        List<SysRole> sysRoleList = sysRoleMapper.selectAll();
        List<Tree> trees = new ArrayList<Tree>();
        if (sysRoleList != null) {
            for (SysRole sysRole : sysRoleList) {
                Tree tree = new Tree();
                tree.setId(sysRole.getId());
                tree.setText(sysRole.getName());
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public List<SysRole> selectAll() {
        return sysRoleMapper.selectAll();
    }


}