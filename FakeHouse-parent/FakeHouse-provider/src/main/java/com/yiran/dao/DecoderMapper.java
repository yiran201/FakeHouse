package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Decoder;

import java.util.List;

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


    /**
     * 通过名称进行分页查询
     * @param s 查询条件
     * @return
     */
    Page<Decoder> findPageByName(String s);


    /**
     * 进行分页查询
     * @return
     */
    Page<Decoder> findPage();


}