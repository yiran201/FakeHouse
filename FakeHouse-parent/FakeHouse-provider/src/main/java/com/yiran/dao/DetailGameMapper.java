package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Category;
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

    /**
     * 分页查询
     * @return
     */
    Page<Category> findPage();


    /**
     * 通过id进行分页查询, 虽然必然的只有一条或者没有数据, 但是由于使用了Page进行结果封装, 所以也要进行该方式的查询
     * @param id 游戏详情id
     * @return
     */
    Page<Category> findPageById(String id);


    /**
     * 通过游戏id进行查询
     * @param gameId
     * @return
     */
    Page<Category> findPageByGameId(String gameId);
}