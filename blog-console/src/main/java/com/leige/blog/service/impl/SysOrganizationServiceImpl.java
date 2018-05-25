package com.leige.blog.service.impl;


import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.mapper.SysOrganizationMapper;
import com.leige.blog.model.SysOrganization;
import com.leige.blog.service.SysOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * SysOrganization 服务层接口实现类
 *
 */
@Service
@Transactional
public class SysOrganizationServiceImpl implements SysOrganizationService {

    @Autowired
    private SysOrganizationMapper organizationMapper;

    @Override
    public List<SysOrganization> selectTreeGrid() {
        return organizationMapper.selectAll();
    }

    @Override
    public List<Tree> selectTree() {
        List<SysOrganization> organizationList = selectTreeGrid();
        List<Tree> trees = new ArrayList<Tree>();
        if (organizationList != null) {
            for (SysOrganization organization : organizationList) {
                Tree tree = new Tree();
                tree.setId(organization.getId());
                tree.setText(organization.getName());
                tree.setIconCls(organization.getIcon());
                tree.setPid(organization.getPid());
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public int insertOrganization(SysOrganization Organization) {
        return organizationMapper.insertSelective(Organization);
    }

    @Override
    public int editOrganization(SysOrganization Organization) {
        return organizationMapper.updateByPrimaryKeySelective(Organization);
    }

    @Override
    public int daleteOrganization(long id) {
        return organizationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysOrganization selectByid(long id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

}