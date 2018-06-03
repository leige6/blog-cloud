package com.leige.blog.mapper;


import com.leige.blog.model.Article;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Component
public interface ArticleMapper extends Mapper<Article> {

    public List<Article> selectDataGrid(Map params);

}