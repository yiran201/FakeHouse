package com.yiran.dao;

import com.yiran.pojo.Decoder;

public interface DecoderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Decoder record);

    int insertSelective(Decoder record);

    Decoder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Decoder record);

    int updateByPrimaryKey(Decoder record);
}