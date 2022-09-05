package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.CategoryMapper;
import com.yiran.pojo.Category;
import com.yiran.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service(interfaceClass = CategoryService.class)
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 根据游戏id查询分类信息
     * @param gameId 游戏id
     * @return 分类列表
     */
    @Override
    public List<Category> findByGameId(String gameId) {
        return categoryMapper.findByGameId(gameId);
    }


    /**
     * 查询分类信息
     * @return 分类列表
     */
    @Override
    public List<Category> findAll() {

        return categoryMapper.findAll();
    }


    /**
     * 关联游戏-分类关系
     * @param map 数据
     */
    @Override
    public void connectWithGame(Map<String, Object> map) {

        categoryMapper.connectWithGame(map);


    }

    /**
     * 通过游戏id查询分类id
     * @param gameId 游戏id
     * @return
     */
    @Override
    public List<Integer> findIdByGameId(String gameId) {

        return categoryMapper.findIdByGameId(gameId);

    }
}
