package com.yiran.service;

import com.yiran.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {


    /**
     * 根据游戏id查询分类信息
     * @param gameId 游戏id
     * @return 分类列表
     */
    List<Category> findByGameId(String gameId);


    /**
     * 查询分类信息
     * @return 分类列表
     */
    List<Category> findAll();


    /**
     * 关联游戏-分类关系
     * @param map 数据
     */
    void connectWithGame(Map<String, Object> map);
}
