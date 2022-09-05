package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Category;
import com.yiran.pojo.DetailGame;
import com.yiran.pojo.Game;

import java.util.List;
import java.util.Map;

public interface GameService {


    /**
     * 查询游戏数据, 携带游戏查看数和下载数
     * @param queryPageBean 分页条件
     * @return
     */
    PageResult findPageWithCount(QueryPageBean queryPageBean);


    /**
     * 查询分类信息
     * @return 分类列表
     */
    List<Category> findCategoryList();


    /**
     * 添加游戏数据
     * @param game 游戏信息
     * @param detailGame 游戏详情信息
     * @param categoryId 游戏分类id
     * @param decoderName 游戏破解者名称
     */
    void add(Game game, DetailGame detailGame, Integer[] categoryId, String decoderName);


    /**
     * 通过id查询游戏数据和游戏详情数据
     * @param id 游戏id
     * @return
     */
    Map<String, Object> findById(String id);


    /**
     * 通过游戏id查询分类id
     * @param id 游戏id
     * @return
     */
    List<Integer> findCategoryIdByGameId(String id);


    /**
     * 修改游戏数据
     * @param game 游戏信息
     * @param detailGame 游戏详情信息
     * @param categoryId 游戏分类id
     * @param decoderName 游戏破解者名称
     */
    void update(Game game, DetailGame detailGame, Integer[] categoryId, String decoderName) throws Exception;
}
