package com.yiran.dao;

import com.yiran.pojo.DetailGame;

import java.util.List;

public interface DetailGameMapper {
    int deleteByPrimaryKey(String id);

    int insert(DetailGame record);

    int insertSelective(DetailGame record);

    DetailGame selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DetailGame record);

    int updateByPrimaryKey(DetailGame record);


    /**
     * 通过游戏详情id查询游戏特色id集合
     * @param detailId 游戏详情id
     * @return
     */
    List<String> findCharaIds(String detailId);


    /**
     * 删除与游戏特色表的关联关系
     * @param detailId 游戏详情id
     */
    void disconnectWithChara(String detailId);
}