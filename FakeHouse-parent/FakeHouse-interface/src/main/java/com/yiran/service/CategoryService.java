package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
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


    /**
     * 通过游戏id查询分类id
     * @param gameId 游戏id
     * @return
     */
    List<Integer> findIdByGameId(String gameId);



    /**
     * 查询分页数据
     * @param queryPageBean 查询条件封装
     * @param column
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean, Integer column);


    /**
     * 添加分类数据
     * @param category 分类数据
     */
    boolean add(Category category);


    /**
     * 通过id进行查询
     * @param id 分类id
     * @return
     */
    Category findById(Integer id);


    /**
     * 修改分类数据
     * @param category 分类信息
     */
    boolean update(Category category);


    /**
     * 通过id删除
     * @param id 分类id
     */
    void deleteById(Integer id);
}
