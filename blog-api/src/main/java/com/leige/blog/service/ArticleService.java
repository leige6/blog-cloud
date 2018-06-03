package com.leige.blog.service;

import com.leige.blog.common.utils.result.PageResult;
import com.leige.blog.model.Article;

import java.util.Map;

public interface ArticleService {

    PageResult selectDataGrid(Map params, Integer page, Integer rows, String sort, String order);

    int deleteArticle(Long id);

    int insertArticle(Article article);

    Article selectById(Long id);

    int updateArticle(Article article);

}