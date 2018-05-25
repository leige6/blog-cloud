package com.leige.blog.service;


import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.model.Section;

import java.util.List;

/**
 *
 * Section表数据服务层接口
 *
 */
public interface SectionService {


    List<Section> selectTreeGrid();

    List<Tree> selectTree();

    int insertSection(Section section);

    int editSection(Section section);

    int daleteSection(long id);

    Section selectByid(long id);

}