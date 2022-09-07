package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
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


    /**
     * 分页查询decoder数据
     * @param queryPageBean 分页和查询条件
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);


    /**
     * 添加decoder, 并进行校验
     * @param decoder decoder数据
     * @return
     */
    boolean addCheck(Decoder decoder);


    /**
     * 通过id删除decoder
     * @param id decoder的id
     * @return
     */
    boolean deleteById(Integer id);


    /**
     * 通过id更新decoder
     * @param decoder 数据和decoder的id
     */
    boolean updateById(Decoder decoder);
}
