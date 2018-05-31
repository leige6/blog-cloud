package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.Section;
import com.leige.blog.service.SectionService;
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
  * @Title:
  * @Description: 栏目管理controller
  * @param
  * @return
  * @author 张亚磊
  * @date 2018年04月02日 14:16:38
  */
@Controller
@RequestMapping("section")
public class SectionController extends BaseController {

    @Autowired
    private SectionService sectionService;

    /*
     *跳转到栏目管理列表
     */
    @RequestMapping(value = "list")
    //@RequiresPermissions("section:list")
    public String list() {
        return "section/list";
    }
    
    
    
    @RequestMapping(value = {"treeGrid"}, method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    //@RequiresPermissions("section:list")
    public List<Section> treeGrid(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Section> trees = sectionService.selectTreeGrid();
        return trees;
    }

    /*
      *跳转到栏目添加列表
      */
    @RequestMapping(value = "toAdd")
    //@RequiresPermissions("section:add")
    public String toAdd() {
        return "section/add";
    }

    /*
       *获取栏目树
       */
    @PostMapping(value = "/tree")
    //@RequiresPermissions(value={"section:add","section:edit"},logical= Logical.OR)
    @ResponseBody
    public List<Tree> tree() {
        return sectionService.selectTree();
    }
    
    
    /*
     *跳转到栏目编辑列表
     */
    @RequestMapping(value = "toEdit")
    //@RequiresPermissions("section:edit")
    public String toEdit(Long id, Model model) {
        Section section = sectionService.selectByid(id);
        model.addAttribute("section", section);
        return "section/edit";
    }

    /*
    *保存栏目
     */
    @RequestMapping("add")
    //@RequiresPermissions("section:add")
    @ResponseBody
    public Result add(@Valid Section section) {
        section.setCreatorTime(new Date());
        section.setCreatorId(this.getUserId());
        section.setIsDel(0);
        int r1 = sectionService.insertSection(section);
        Long id=section.getId();
        if(section.getPid()!=null){
            Section parentSection=sectionService.selectByid(section.getPid());
            StringBuilder str=new StringBuilder(parentSection.getValue()).append(String.valueOf(id)).append(",");
            section.setValue(str.toString());
        }else{
            StringBuilder str=new StringBuilder(String.valueOf(id)).append(",");
            section.setValue(str.toString());
        }
        int r2=sectionService.editSection(section);
        return (r1+r2) > 1 ? ResultUtil.success(ResultEnum.SECTION_ADD_SUCCESS, null) : ResultUtil.fail(ResultEnum.SECTION_ADD_FAIL);
    }


    /*
    *编辑栏目
     */
    @RequestMapping("edit")
    //@RequiresPermissions("section:edit")
    @ResponseBody
    public Result edit(@Valid Section Section) {
        Section.setUpdateTime(new Date());
        Section.setUpdateId(this.getUserId());
        Section.setUpdateCount(Section.getUpdateCount() == null ? 1 : (Section.getUpdateCount() + 1));
        Long id=Section.getId();
        if(Section.getPid()!=null){
            Section parentSection=sectionService.selectByid(Section.getPid());
            StringBuilder str=new StringBuilder(parentSection.getValue()).append(String.valueOf(id)).append(",");
            Section.setValue(str.toString());
        }else{
            StringBuilder str=new StringBuilder(String.valueOf(id)).append(",");
            Section.setValue(str.toString());
        }
        int res = sectionService.editSection(Section);
        return res > 0 ? ResultUtil.success(ResultEnum.SECTION_EDIT_SUCCESS, null) : ResultUtil.fail(ResultEnum.SECTION_EDIT_FAIL);
    }

    /*
    *保存栏目
     */
    @RequestMapping("delete")
    //@RequiresPermissions("section:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res = sectionService.daleteSection(id);
        return res > 0 ? ResultUtil.success(ResultEnum.SECTION_DELETE_SUCCESS, null) : ResultUtil.fail(ResultEnum.SECTION_DELETE_FAIL);
    }

}
