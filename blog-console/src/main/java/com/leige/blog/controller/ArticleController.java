package com.leige.blog.controller;


import com.leige.blog.common.base.controller.BaseController;
import com.leige.blog.common.enums.ResultEnum;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.common.utils.result.Result;
import com.leige.blog.common.utils.result.ResultUtil;
import com.leige.blog.model.Article;
import com.leige.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张亚磊
 * @Description: 文章管理controller
 * @date 2018/3/6  14:54
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;
    /*
     *跳转到文章管理列表
     */
    @RequestMapping(value = "list")
    //@RequiresPermissions("article:list")
    public String  list() {
        return "article/list";
    }

    @RequestMapping(value ="dataGrid", method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    //@RequiresPermissions("article:list")
    @ResponseBody
    public PageResult treeGrid(Long sectionId, String title, String publishor, Date startTime, Date endTime, Integer page, Integer rows, @RequestParam(defaultValue = "id")String sort, @RequestParam(defaultValue = "asc")String order) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (sectionId != null) {
            params.put("sectionId", sectionId);
        }
        if (StringUtils.isNotBlank(title)) {
            params.put("title", title);
        }
        if (StringUtils.isNotBlank(publishor)) {
            params.put("publishor", publishor);
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
        return articleService.selectDataGrid(params,page, rows, sort,order);
    }

    /*
      *跳转到文章添加列表
      */
    @RequestMapping(value = "toAdd")
    //@RequiresPermissions("article:add")
    public String toAdd() {
        return "article/add";
    }

    /*
     *跳转到文章编辑列表
     */
    @RequestMapping(value = "toEdit")
    //@RequiresPermissions("article:edit")
    public String toEdit(Long id, Model model) {
        Article article=articleService.selectById(id);
        model.addAttribute("article",article);
        return "article/edit";
    }


    /*
    *保存文章
     */
    @RequestMapping("add")
    //@RequiresPermissions("article:add")
    @ResponseBody
    public Result add(@Valid Article article,HttpServletRequest request) {
        article.setHateNumber(0);
        article.setLikesNumber(0);
        article.setReplyNumber(0);
        article.setViewNumber(0);
        article.setIllegalNumber(0);
        Date date = new Date();

        if(article.getPublishStatus()!=null&&article.getPublishStatus()==1){
            article.setPublishStatus(1);
            article.setPublishId(this.getUserId(request));
            article.setPublishTime(new Date());
            article.setCreatorTime(new Date());
            article.setCreatorId(this.getUserId(request));
            article.setCreatorTime(new Date());
            article.setCreatorId(this.getUserId(request));
            article.setIsQuick(1);
            article.setIsDel(0);
            int res=articleService.insertArticle(article);
            return res>0? ResultUtil.success(ResultEnum.ARTICLE_PUBLISH_SUCCESS,null):ResultUtil.fail(ResultEnum.ARTICLE_PUBLISH_FAIL);
        }else{
            article.setIsQuick(0);
            article.setPublishStatus(0);
            article.setCreatorTime(new Date());
            article.setCreatorId(this.getUserId(request));
            article.setCreatorTime(new Date());
            article.setCreatorId(this.getUserId(request));
            article.setIsDel(0);
            int res=articleService.insertArticle(article);
            return res>0?ResultUtil.success(ResultEnum.ARTICLE_ADD_SUCCESS,null):ResultUtil.fail(ResultEnum.ARTICLE_ADD_FAIL);
        }
    }

    /*
   *保存文章
    */
    @RequestMapping("edit")
    //@RequiresPermissions("article:edit")
    @ResponseBody
    public Result edit(@Valid Article article,HttpServletRequest request) {
        article.setUpdateTime(new Date());
        article.setUpdateId(this.getUserId(request));
        article.setUpdateCount(article.getUpdateCount()==null?1:(article.getUpdateCount()+1));
        int res=articleService.updateArticle(article);
        return res>0?ResultUtil.success(ResultEnum.ARTICLE_EDIT_SUCCESS,null):ResultUtil.fail(ResultEnum.ARTICLE_EDIT_FAIL);
    }


    /*
    *删除文章
     */
    @RequestMapping("delete")
    //@RequiresPermissions("article:delete")
    @ResponseBody
    public Result delete(Long id) {
        int res=articleService.deleteArticle(id);
        return res>0?ResultUtil.success(ResultEnum.ARTICLE_DELETE_SUCCESS,null):ResultUtil.fail(ResultEnum.ARTICLE_DELETE_FAIL);
    }

    /**
      * @Title:
      * @Description: 取消、发布文章
      * @param
      * @return
      * @author 张亚磊
      * @date 2018年04月03日 15:10:51
      */
    @RequestMapping("publish")
    //@RequiresPermissions("article:publish")
    @ResponseBody
    public Result lock(Long id,Integer isPublished,HttpServletRequest request) {
        Article article=new Article();
        article.setId(id);
        article.setPublishStatus(isPublished==1?0:1);
        article.setPublishId(this.getUserId(request));
        article.setPublishTime(new Date());
        int res = articleService.updateArticle(article);
        if(isPublished==0){
            return res > 0 ? ResultUtil.success(ResultEnum.ARTICLE_PUBLISH_SUCCESS, null) : ResultUtil.fail(ResultEnum.ARTICLE_PUBLISH_FAIL);
        }else{
            return res > 0 ? ResultUtil.success(ResultEnum.ARTICLE_UNPUBLISH_SUCCESS, null) : ResultUtil.fail(ResultEnum.ARTICLE_UNPUBLISH_FAIL);
        }
    }
}
