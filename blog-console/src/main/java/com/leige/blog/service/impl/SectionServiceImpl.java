package com.leige.blog.service.impl;


import com.leige.blog.common.utils.result.Tree;
import com.leige.blog.mapper.SectionMapper;
import com.leige.blog.model.Section;
import com.leige.blog.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Section 服务层接口实现类
 *
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Override
    public List<Section> selectTreeGrid() {
        return sectionMapper.selectAll();
    }

    @Override
    public List<Tree> selectTree() {
        List<Section> sections = selectTreeGrid();
        List<Tree> trees = new ArrayList<Tree>();
        if (sections != null) {
            for (Section section : sections) {
                Tree tree = new Tree();
                tree.setId(section.getId());
                tree.setText(section.getName());
                tree.setIconCls("fi-list");
                tree.setPid(section.getPid());
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public int insertSection(Section column) {
        return sectionMapper.insertSelective(column);
    }

    @Override
    public int editSection(Section column) {
        return sectionMapper.updateByPrimaryKeySelective(column);
    }

    @Override
    public int daleteSection(long id) {
        return sectionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Section selectByid(long id) {
        return sectionMapper.selectByPrimaryKey(id);
    }

}