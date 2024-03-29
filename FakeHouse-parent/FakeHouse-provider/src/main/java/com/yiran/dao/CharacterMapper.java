package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Character;

import java.util.List;
import java.util.Map;

public interface CharacterMapper {
    int deleteByPrimaryKey(String id);

    int insert(Character record);

    int insertSelective(Character record);

    Character selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Character record);

    int updateByPrimaryKey(Character record);


    /**
     * 通过id删除
     * @param charaIds id集合
     */
    void deleteByList(List<String> charaIds);


    /**
     * 删除与 detailGame的 关联关系
     * @param id character的id
     */
    void disconnectWithDetailGame(String id);


    /**
     * 通过detailGame的id删除其关联的所有 character数据
     * @param detailId  detailGame的id
     */
    void deleteByDetailGameId(String detailId);


    /**
     * 分页查询
     * @return
     */
    Page<Character> findPage();


    /**
     * 通过游戏id进行分页查询
     * @param queryString 游戏id
     * @return
     */
    Page<Character> findPageByGameId(String queryString);


    /**
     * 通过游戏详情id进行查询
     * @param queryString 游戏详情id
     * @return
     */
    Page<Character> findPageByDetailGameId(String queryString);


    /**
     * 通过detailId进行查询
     * @param detailId 游戏详情id
     * @return
     */
    List<Character> findByDetailGameId(String detailId);

    /**
     * 添加关联关系  与 detailGame
     * @param map 数据
     */
    void connectWithDetailGame(Map<String, String> map);
}