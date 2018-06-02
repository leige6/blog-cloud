package com.leige.blog.controller;


import com.leige.blog.common.base.annotation.AvoidDuplicateSubmission;
import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysResource;
import com.leige.blog.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * @Description: 资源管理controller
 * @date 2018/3/6  14:54
 */
@Controller
@RequestMapping("resource")
public class SysResourceController extends BaseController {

    @Autowired
    private SysResourceService sysResourceService;

    /*
     *跳转到资源管理列表
     */
    @RequestMapping(value = "tolist")
    //@RequiresPermissions("resource:list")
    public String  list() {
        return "resource/list";
    }

    @RequestMapping(value = { "treeGrid" }, method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<SysResource> treeGrid(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<SysResource> trees= sysResourceService.selectAll();
        return trees;
    }

    /*
      *跳转到资源添加列表
      */
    @RequestMapping(value = "add")
    //@RequiresPermissions("resource:add")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public String add() {
        return "resource/add";
    }


    /*
     *跳转到资源编辑列表
     */
    @RequestMapping(value = "edit")
    //@RequiresPermissions("resource:edit")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public String edit(Long id, Model model) {
        SysResource sysResource=sysResourceService.selectByid(id);
        model.addAttribute("resource",sysResource);
        return "resource/edit";
    }
    /*
    *保存权限
     */
    @RequestMapping("save")
    //@RequiresPermissions("resource:save")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public Result add(@Valid SysResource sysPermission,HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        if((sysPermission.getId()!=null)&&(sysPermission.getId()!=0)){
            sysPermission.setUpdateTime(new Date());
            sysPermission.setUpdateId(this.getUserId(request));
            sysPermission.setUpdateCount(sysPermission.getUpdateCount()==null?1:(sysPermission.getUpdateCount()+1));
            int res=sysResourceService.editSysResource(sysPermission);
            return res>0? ResultUtil.success(ResultEnum.RESOURCE_EDIT_SUCCESS,null):ResultUtil.fail(ResultEnum.RESOURCE_EDIT_FAIL);
        }else{
            sysPermission.setCreatorTime(new Date());
            sysPermission.setCreatorId(this.getUserId(request));
            sysPermission.setIsDel(0);
            // 选择菜单时将openMode设置为null
            Integer type = sysPermission.getType();
            if (null != type && type == 0) {
                sysPermission.setOpenMode(null);
            }
            int res=sysResourceService.insertSysResource(sysPermission);
            return res>0?ResultUtil.success(ResultEnum.RESOURCE_ADD_SUCCESS,null):ResultUtil.fail(ResultEnum.RESOURCE_ADD_FAIL);
        }
    }

    /*
    *保存权限
     */
    @RequestMapping("delete")
    //@RequiresPermissions("resource:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res=sysResourceService.daleteSysResource(id);
        return res>0?ResultUtil.success(ResultEnum.RESOURCE_DELETE_SUCCESS,null):ResultUtil.fail(ResultEnum.RESOURCE_DELETE_FAIL);
    }

    /**
     * 查询所有的资源tree
     */
    @RequestMapping("/allTrees")
   // @RequiresAuthentication
    @ResponseBody
    public List<Tree> allTree() {
        return sysResourceService.selectAllTree();
    }

}
