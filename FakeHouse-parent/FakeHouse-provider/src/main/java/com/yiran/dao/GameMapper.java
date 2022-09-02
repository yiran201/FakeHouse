package com.yiran.dao;

import com.yiran.pojo.Game;

public interface GameMapper {
    int deleteByPrimaryKey(String id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);
}