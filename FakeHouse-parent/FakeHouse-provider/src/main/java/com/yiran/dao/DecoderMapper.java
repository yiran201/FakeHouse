package com.yiran.dao;

import com.yiran.pojo.Decoder;

public interface DecoderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Decoder record);

    int insertSelective(Decoder record);

    Decoder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Decoder record);

    int updateByPrimaryKey(Decoder record);


    /**
     * 通过名字查询decoder
     * @param name decode的name
     * @return decoder
     */
    Integer findDecoderIdByName(String name);

    /**
     * 插入数据并返回主键
     * @param decoder decoder信息
     * @return 主键id
     */
    void insertAndGetKey(Decoder decoder);
}