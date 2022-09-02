package com.yiran.dao;

import com.yiran.pojo.DetailGame;

public interface DetailGameMapper {
    int deleteByPrimaryKey(String id);

    int insert(DetailGame record);

    int insertSelective(DetailGame record);

    DetailGame selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DetailGame record);

    int updateByPrimaryKey(DetailGame record);
}