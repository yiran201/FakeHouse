package com.yiran.service;

import com.yiran.pojo.Decoder;

public interface DecoderService {


    /**
     * 插入数据到decoder表
     * @param decoder decoder信息
     */
    Integer add(Decoder decoder);


    /**
     * 通过id查询decoder信息
     * @param decoderId decoder的id
     * @return
     */
    Decoder findById(Integer decoderId);
}
