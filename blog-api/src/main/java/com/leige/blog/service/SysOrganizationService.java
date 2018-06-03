package com.leige.blog.service;


import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysOrganization;

import java.util.List;

/**
 *
 * Organization表数据服务层接口
 *
 */
public interface SysOrganizationService {


    List<SysOrganization> selectTreeGrid();

    List<Tree> selectTree();

    int insertOrganization(SysOrganization organization);

    int editOrganization(SysOrganization organization);

    int daleteOrganization(long id);

    SysOrganization selectByid(long id);

}