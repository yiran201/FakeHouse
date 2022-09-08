package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Game;

import java.util.List;
import java.util.Map;

public interface GameMapper {
    int deleteByPrimaryKey(String id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);


    /**
     *  查询游戏数据, 携带游戏查看数和下载数
     * @return
     */
    Page<Game> findAll();

    /**
     * 通过游戏名称查询游戏数据, 携带游戏查看数和下载数
     * @param queryString  游戏名称
     * @return
     */
    Page<Game> findByName(String queryString);


    /**
     * 添加游戏与分类的关联关系
     * @param map 游戏id和分类id
     */
    void connectWithCategory(Map<String, Object> map);


    /**
     * 去除游戏与分类的关联关系
     * @param id 游戏id
     */
    void disconnectWithCategory(String id);


    /**
     * 自定义方法进行游戏数据的修改, 为防止关键数据的修改, 需要进行处理
     * 对于其他字段数据, 如果为空不修改, 对于 decoderId字段数据, 如果为空, 需要进行修改
     * @param game 游戏数据
     */
    void updateGameByPrimaryKey(Game game);


    /**
     * 通过decoder的id查询游戏id
     * @param id decoder 的 id
     * @return
     */
    List<String> findIdsByDecoderId(Integer id);


    /**
     * 通过decoder的id查询游戏的id数目
     * @param id decoder 的 id
     * @return
     */
    Integer findCountByDecoderId(Integer id);



    /**
     * 查询游戏id
     * @param detailId 游戏详情id
     * @return
     */
    String findIdByDetailId(String detailId);
}