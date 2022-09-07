package com.yiran.dao;

import com.github.pagehelper.Page;
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


    /**
     * 通过名字进行模糊查询 通过PageHelper进行分页
     * @param queryString 查询字符串
     * @return
     */
    Page<Category> findPageByName(String queryString);


    /**
     * 查询所有, 通过PageHelper进行分页
     * @return
     */
    Page<Category> findPage();


    /**
     * 删除中间表数据
     * @param id 分类id
     */
    void disconnectWithGame(Integer id);


    /**
     * 通过名称查询
     * @param name 分类名称
     * @return
     */
    Category findByName(String name);


    /**
     * 通过游戏id分页查询分类数据
     * @param s 游戏id
     * @return
     */
    Page<Category> findPageByGameId(String s);
}