package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysOrganization;
import com.leige.blog.service.SysOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author 张亚磊
 * @Description: 部门管理controller
 * @date 2018/3/6  14:54
 */
@Controller
@RequestMapping("organization")
public class OrganizationController extends BaseController {

    @Autowired
    private SysOrganizationService organizationService;

    /*
     *跳转到部门管理列表
     */
    @RequestMapping(value = "tolist")
    //@RequiresPermissions("org:list")
    public String list() {
        return "organization/list";
    }
    
    
    
    @RequestMapping(value = {"treeGrid"}, method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    //@RequiresPermissions("org:list")
    public List<SysOrganization> treeGrid(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<SysOrganization> trees = organizationService.selectTreeGrid();
        return trees;
    }

    /*
      *跳转到部门添加列表
      */
    @RequestMapping(value = "toAdd")
    //@RequiresPermissions("org:add")
    public String toAdd() {
        return "organization/add";
    }

    /*
       *获取部门树
       */
    @PostMapping(value = "/tree")
    //@RequiresPermissions(value={"org:add","org:edit"},logical= Logical.OR)
    @ResponseBody
    public List<Tree> tree() {
        return organizationService.selectTree();
    }
    
    
    /*
     *跳转到部门编辑列表
     */
    @RequestMapping(value = "toEdit")
    //@RequiresPermissions("org:edit")
    public String toEdit(Long id, Model model) {
        SysOrganization organization = organizationService.selectByid(id);
        model.addAttribute("organization", organization);
        return "organization/edit";
    }

    /*
    *保存部门
     */
    @RequestMapping("add")
    //@RequiresPermissions("org:add")
    @ResponseBody
    public Result add(@Valid SysOrganization organization) {
        organization.setCreatorTime(new Date());
        organization.setCreatorId(this.getUserId());
        organization.setIsDel(0);
        int r1 = organizationService.insertOrganization(organization);
        Long id=organization.getId();
        if(organization.getPid()!=null){
            SysOrganization parentSysOrg=organizationService.selectByid(organization.getPid());
            StringBuilder str=new StringBuilder(parentSysOrg.getValue()).append(String.valueOf(id)).append(",");
            organization.setValue(str.toString());
        }else{
            StringBuilder str=new StringBuilder(String.valueOf(id)).append(",");
            organization.setValue(str.toString());
        }
        int r2=organizationService.editOrganization(organization);
        return (r1+r2) > 1 ? ResultUtil.success(ResultEnum.ORG_ADD_SUCCESS, null) : ResultUtil.fail(ResultEnum.ORG_ADD_FAIL);
    }


    /*
    *编辑部门
     */
    @RequestMapping("edit")
    //@RequiresPermissions("org:edit")
    @ResponseBody
    public Result edit(@Valid SysOrganization organization) {
        organization.setUpdateTime(new Date());
        organization.setUpdateId(this.getUserId());
        organization.setUpdateCount(organization.getUpdateCount() == null ? 1 : (organization.getUpdateCount() + 1));
        Long id=organization.getId();
        if(organization.getPid()!=null){
            SysOrganization parentSysOrg=organizationService.selectByid(organization.getPid());
            StringBuilder str=new StringBuilder(parentSysOrg.getValue()).append(String.valueOf(id)).append(",");
            organization.setValue(str.toString());
        }else{
            StringBuilder str=new StringBuilder(String.valueOf(id)).append(",");
            organization.setValue(str.toString());
        }
        int res = organizationService.editOrganization(organization);
        return res > 0 ? ResultUtil.success(ResultEnum.ORG_EDIT_SUCCESS, null) : ResultUtil.fail(ResultEnum.ORG_EDIT_FAIL);
    }

    /*
    *保存部门
     */
    @RequestMapping("delete")
    //@RequiresPermissions("org:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res = organizationService.daleteOrganization(id);
        return res > 0 ? ResultUtil.success(ResultEnum.RESOURCE_DELETE_SUCCESS, null) : ResultUtil.fail(ResultEnum.RESOURCE_DELETE_FAIL);
    }

}
