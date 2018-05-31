package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.SysRole;
import com.leige.blog.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author 张亚磊
 * @Description: 角色管理controller
 * @date 2018/3/6  14:54
 */
@Controller
@RequestMapping("role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    /*
     *跳转到角色管理列表
     */
    @RequestMapping(value = "tolist")
    //@RequiresPermissions("role:tolist")
    public String  list() {
        return "role/list";
    }

    @RequestMapping(value ="dataGrid", method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public PageResult treeGrid(Integer page, Integer rows, @RequestParam(defaultValue = "id")String sort, @RequestParam(defaultValue = "asc")String order) {
        if(!"id".equals(sort)&&!"seq".equals(sort)){
            sort="id";//防止sql注入
        }
        if(!"asc".equals(order)&&!"desc".equals(order)){
            order="asc";//防止sql注入
        }
        return sysRoleService.selectDataGrid(page, rows, sort,order);
    }





    /*
      *跳转到角色添加列表
      */
    //@RequiresRoles("admin")
    @RequestMapping(value = "add")
    //@RequiresPermissions("role:add")
    public String add() {
        return "role/add";
    }


    /*
     *跳转到角色编辑列表
     */
    //@RequiresRoles("admin")
    @RequestMapping(value = "edit")
    //@RequiresPermissions("role:edit")
    public String edit(Long id, Model model) {
        SysRole SysRole=sysRoleService.selectByid(id);
        model.addAttribute("role",SysRole);
        return "role/edit";
    }
    /*
    *保存权限
     */
    //@RequiresRoles("admin")
    @RequestMapping("save")
    //@RequiresPermissions("role:save")
    @ResponseBody
    public Result add(@Valid SysRole sysRole) {
        if((sysRole.getId()!=null)&&(sysRole.getId()!=0)){
            sysRole.setUpdateTime(new Date());
            sysRole.setUpdateId(this.getUserId());
            sysRole.setUpdateCount(sysRole.getUpdateCount()==null?1:(sysRole.getUpdateCount()+1));
            int res=sysRoleService.editSysRole(sysRole);
            return res>0? ResultUtil.success(ResultEnum.ROLE_EDIT_SUCCESS,null):ResultUtil.fail(ResultEnum.ROLE_EDIT_FAIL);
        }else{
            sysRole.setCreatorTime(new Date());
            sysRole.setCreatorId(this.getUserId());
            sysRole.setIsDel(0);
            int res=sysRoleService.insertSysRole(sysRole);
            return res>0?ResultUtil.success(ResultEnum.ROLE_ADD_SUCCESS,null):ResultUtil.fail(ResultEnum.ROLE_ADD_FAIL);
        }
    }

    /*
    *保存权限
     */
    //@RequiresRoles("admin")
    @RequestMapping("delete")
    //@RequiresPermissions("role:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res=sysRoleService.daleteSysRole(id);
        return res>0?ResultUtil.success(ResultEnum.ROLE_DELETE_SUCCESS,null):ResultUtil.fail(ResultEnum.ROLE_DELETE_FAIL);
    }


    /**
      * @Title:
      * @Description: 跳转到角色授权列表
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月09日 11:37:58
      */
    //@RequiresRoles("admin")
    @RequestMapping(value = "toGrant")
    //@RequiresAuthentication
    public String toGrant(Long rid, Model model) {
        model.addAttribute("rid",rid);
        return "role/grant";
    }

    /**
      * @Title:
      * @Description: 查询角色权限
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月09日 11:36:21
      */
    @RequestMapping("findResourceIdListByRoleId")
    //@RequiresAuthentication
    @ResponseBody
    public Result selectResourceIdListByRoleId(Long id) {
        List<Long> result=sysRoleService.selectResourceIdListByRoleId(id);
        return ResultUtil.success(ResultEnum.SYS_REQUEST_SUCCESS,result);
    }

    /**
      * @Title:
      * @Description: 授权成功
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月09日 13:51:45
      */
    //@RequiresRoles("admin")
    //@RequiresPermissions("role:grant")
    @RequestMapping("/grant")
    @ResponseBody
    public Result grant(Long id, String resourceIds) {
        int rtesult=sysRoleService.updateSysRoleResource(id, resourceIds);
        return rtesult==1?ResultUtil.success(ResultEnum.ROLE_GRANT_SUCCESS,null):ResultUtil.fail(ResultEnum.ROLE_GRANT_FAIL);
    }

    /**
      * @Title:
      * @Description: 获取角色树
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月14日 10:52:27
      */
    @RequestMapping(value = { "tree" }, method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    //@RequiresAuthentication
    public List<Tree> getAllTree(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Tree> trees= sysRoleService.selectAllTree();
        return trees;
    }
}
