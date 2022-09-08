package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Character;
import com.yiran.pojo.DetailGame;

import java.util.List;

public interface DetailGameService {

    /**
     * 添加游戏详情数据
     * @param detailGame 游戏详情数据
     */
    void add(DetailGame detailGame);


    /**
     * 通过id查询游戏详情信息
     * @param detailId 游戏详情id
     * @return
     */
    DetailGame findById(String detailId);


    /**
     * 通过id删除游戏详情数据
     * @param detailGame 游戏详情
     */
    void updateById(DetailGame detailGame);


    /**
     * 删除游戏详情数据, 要求将游戏关联的特色数据也彻底删除  两者为依存关系
     * @param detailId 游戏详情的id
     */
    boolean deleteById(String detailId);


    /**
     * 分页查询
     * @param queryPageBean 分页和查询条件
     * @param column 查询字段
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean, Integer column);


    /**
     * 通过detailId查询 游戏特色数据
     * @param detailId 游戏详情id
     * @return
     */
    List<Character> findCharas(String detailId);
}
