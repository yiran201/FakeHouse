package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Game;

public interface GameMapper {
    int deleteByPrimaryKey(String id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);


    /**
     *  查询游戏数据, 携带游戏查看数和下载数
     * @return
     */
    Page<Game> findAll();

    /**
     * 通过游戏名称查询游戏数据, 携带游戏查看数和下载数
     * @param queryString  游戏名称
     * @return
     */
    Page<Game> findByName(String queryString);
}