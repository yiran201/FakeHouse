package com.yiran.dao;

import com.yiran.pojo.Recode;

public interface RecodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Recode record);

    int insertSelective(Recode record);

    Recode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Recode record);

    int updateByPrimaryKey(Recode record);
}