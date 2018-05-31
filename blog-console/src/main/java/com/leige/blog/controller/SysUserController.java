package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.model.SysRole;
import com.leige.blog.model.SysUser;
import com.leige.blog.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

/**
 * @author 张亚磊
 * @Description: 系统用户管理controller
 * @date 2018/3/6  14:54
 */
@Controller
@RequestMapping("sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /*
     *跳转到系统用户管理列表
     */
    @RequestMapping(value = "tolist")
    //@RequiresPermissions("sysUser:list")
    public String list() {
        return "sysUser/list";
    }

    @RequestMapping(value ="dataGrid", method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public PageResult dataGrid(String userName, String name, Long orgId, Date startTime, Date endTime, Integer page, Integer rows, @RequestParam(defaultValue = "id")String sort, @RequestParam(defaultValue = "asc")String order, Model model) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(userName)) {
            params.put("userName", userName);
        }
        if (StringUtils.isNotBlank(name)) {
            params.put("name", name);
        }
        if (orgId != null) {
            params.put("orgId", orgId);
        }
        if (startTime != null) {
            params.put("startTime",startTime);
        }
        if (endTime != null) {
            params.put("endTime", endTime);
        }
        if(!"id".equals(sort)&&!"seq".equals(sort)){
            sort="id";//防止sql注入
        }
        if(!"asc".equals(order)&&!"desc".equals(order)){
            order="asc";//防止sql注入
        }
        return sysUserService.selectDataGrid(params,page, rows, sort,order);
    }
    
   
    /*
      *跳转到系统用户添加列表
      */
    @RequestMapping(value = "toAdd")
    //@RequiresPermissions("sysUser:add")
    public String toAdd() {
        return "sysUser/add";
    }


    
    
    /*
     *跳转到系统用户编辑列表
     */
    @RequestMapping(value = "toEdit")
    //@RequiresPermissions("sysUser:edit")
    public String toEdit(Long id, Model model) {
        SysUser sysUser = sysUserService.selectByid(id);
        List<SysRole> sysRoles=sysUser.getSysRoles();
        List<Long> roleIds=new ArrayList<Long>();
        if(sysRoles!=null&&sysRoles.size()>0){
            for(SysRole sysRole :sysRoles){
                roleIds.add(sysRole.getId());
            }
        }
        model.addAttribute("roleIds", roleIds);
        model.addAttribute("sysUser", sysUser);
        return "sysUser/edit";
    }

    /*
    *保存系统用户
     */
    @RequestMapping("add")
    //@RequiresRoles("admin")
    //@RequiresPermissions("sysUser:add")
    @ResponseBody
    public Result add(@Valid SysUser sysUser) {
        String password=sysUser.getPassword();
        if(StringUtils.isNotBlank(password)){
            sysUser.setPassword(DigestUtils.md5Hex(password));
        }else{
            sysUser.setPassword(DigestUtils.md5Hex("123456"));
        }
        sysUser.setIsLocked(0);
        sysUser.setCreatorTime(new Date());
        sysUser.setCreatorId(this.getUserId());
        sysUser.setIsDel(0);
        int res = sysUserService.insertSysUser(sysUser);
        return res > 0 ? ResultUtil.success(ResultEnum.USER_ADD_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_ADD_FAIL);
    }


    /*
    *编辑系统用户
     */
    @RequestMapping("edit")
    //@RequiresRoles("admin")
    //@RequiresPermissions("sysUser:edit")
    @ResponseBody
    public Result edit(@Valid SysUser sysUser) {
        String password=sysUser.getPassword();
        if(StringUtils.isNotBlank(password)){
            sysUser.setPassword(DigestUtils.md5Hex(password));
        }else{
            sysUser.setPassword(null);
        }
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(this.getUserId());
        sysUser.setUpdateCount(sysUser.getUpdateCount() == null ? 1 : (sysUser.getUpdateCount() + 1));
        int res = sysUserService.upadteSysUser(sysUser);
        return res > 0 ? ResultUtil.success(ResultEnum.USER_EDIT_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_EDIT_FAIL);
    }

   /**
     * @Title:
     * @Description: 用户删除
     * @param
     * @return
     * @author 张亚磊
     * @date 2018年03月14日 16:09:43
     */
    @RequestMapping("delete")
    //@RequiresRoles("admin")
    //@RequiresPermissions("sysUser:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res = sysUserService.daleteSysUser(id);
        return res > 0 ? ResultUtil.success(ResultEnum.USER_DELETE_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_DELETE_FAIL);
    }

    /**
      * @Title:
      * @Description: 用户解锁
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月14日 16:09:58
      */
    @RequestMapping("lock")
    //@RequiresRoles("admin")
    //@RequiresPermissions("sysUser:lock")
    @ResponseBody
    public Result lock(Long id,Integer isLocked) {
        SysUser sysUser=new SysUser();
        sysUser.setId(id);
        sysUser.setIsLocked(isLocked==1?0:1);
        sysUser.setLoginFailureCount(isLocked==1?0:5);
        sysUser.setLockedDate(new Date());
        int res = sysUserService.upadteSysUser(sysUser);
        if(isLocked==1){
            return res > 0 ? ResultUtil.success(ResultEnum.USER_UNLOCK_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_UNLOCK_FAIL);
        }else{
            return res > 0 ? ResultUtil.success(ResultEnum.USER_LOCK_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_LOCK_FAIL);
        }
    }

    /**
      * @Title:
      * @Description: 用户修改密码
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月14日 17:33:58
      */
    @RequestMapping(value = "toEditPwd")
    //@RequiresPermissions("sysUser:editPwd")
    public String toEditPwd() {
        return "sysUser/editPwd";
    }


    /**
      * @Title:
      * @Description:用户密码修改
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年03月14日 17:48:27
      */
    @RequestMapping("editPwd")
    //@RequiresPermissions("sysUser:editPwd")
    @ResponseBody
    public Result editPwd(String oldPwd,String pwd) {
        Long userId=this.getUserId();
        SysUser sysUser=sysUserService.selectByid(userId);
        String oldPwdstr= DigestUtils.md5Hex(oldPwd);
        if(!oldPwdstr.equals(sysUser.getPassword())){
            return ResultUtil.fail(ResultEnum.USER_EDIT_PWD_UN_EQUAL);
        }
        SysUser newPwdSysUser=new SysUser();
        newPwdSysUser.setId(userId);
        newPwdSysUser.setPassword(DigestUtils.md5Hex(pwd));
        int res = sysUserService.upadteSysUser(newPwdSysUser);
        return res > 0 ? ResultUtil.success(ResultEnum.USER_EDIT_PWD_SUCCESS, null) : ResultUtil.fail(ResultEnum.USER_EDIT_PWD_FAIL);
    }
}
