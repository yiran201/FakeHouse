package com.yiran.dao;

import com.yiran.pojo.Character;

import java.util.List;

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
}