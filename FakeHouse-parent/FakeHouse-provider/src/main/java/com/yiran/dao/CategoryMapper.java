package com.yiran.dao;

import com.yiran.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

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
     * 插入数据到 t_game_category 表中, 建立游戏-分类的关联关系
     * @param map 数据
     */
    void connectWithGame(Map<String, Object> map);


    /**
     * 通过游戏id查询分类id
     * @param gameId 游戏id
     * @return
     */
    List<Integer> findIdByGameId(String gameId);
}