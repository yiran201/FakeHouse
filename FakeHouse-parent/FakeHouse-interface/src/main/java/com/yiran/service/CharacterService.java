package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Character;

import java.util.List;

public interface CharacterService {

    /**
     * 通过id删除
     * @param charaIds id集合
     */
    void deleteByList(List<String> charaIds);


    /**
     * 分页查询
     * @param queryPageBean 分页和查询条件
     * @param column 查询字段
     * @return 分页查询结果
     */
    PageResult findPage(QueryPageBean queryPageBean, Integer column);


    /**
     * 添加数据
     * @param character 游戏特色数据
     */
    void add(Character character);


    /**
     * 通过id修改数据
     * @param character 游戏特色数据
     */
    void updateById(Character character);


    /**
     * 通过id进行查询
     * @param id 游戏特色id
     * @return
     */
    Character findById(String id);


    /**
     * 通过id删除
     * @param id 游戏特色id
     */
    void deleteById(String id);


    /**
     * 通过detailGame的id删除其关联的所有 character数据
     * @param detailId  detailGame的id
     */
    void deleteByDetailGameId(String detailId);
}
