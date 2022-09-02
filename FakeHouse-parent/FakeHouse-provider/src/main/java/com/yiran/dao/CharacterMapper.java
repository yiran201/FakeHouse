package com.yiran.dao;

import com.yiran.pojo.Character;

public interface CharacterMapper {
    int deleteByPrimaryKey(String id);

    int insert(Character record);

    int insertSelective(Character record);

    Character selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Character record);

    int updateByPrimaryKey(Character record);
}