package com.yiran.service;

import com.yiran.pojo.Category;

import java.util.List;

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

}
