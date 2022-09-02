package com.yiran.dao;

import com.yiran.pojo.Recode;

public interface RecodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Recode record);

    int insertSelective(Recode record);

    Recode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Recode record);

    int updateByPrimaryKey(Recode record);

    /**
     * 通过游戏id查询查询游戏查看数
     * @param game_id: 游戏id
     * @return
     */
    int findWatchCountByGameId(String game_id);

    /**
     * 查询游戏的下载数
     * @param game_id: 游戏id
     * @return
     */
    int findDownloadCountByGameId(String game_id);
}