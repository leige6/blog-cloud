package com.leige.blog.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.mapper.ArticleMapper;
import com.leige.blog.model.Article;
import com.leige.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * Article 服务层接口实现类
 *
 */
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageResult selectDataGrid(Map params, Integer page, Integer rows, String sort, String order) {
        PageHelper.startPage(page,rows);
        params.put("sort",sort);
        params.put("order",order);
        List<Article> articles=articleMapper.selectDataGrid(params);
        PageInfo<Article> result=new PageInfo<Article>(articles);
        PageResult pageResult=new PageResult();
        pageResult.setRows(articles);
        pageResult.setTotal(result.getTotal());
        return pageResult;
    }

    @Override
    public int insertArticle(Article article) {
        return articleMapper.insertSelective(article);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public int deleteArticle(Long id) {
        return articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Article selectById(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

}