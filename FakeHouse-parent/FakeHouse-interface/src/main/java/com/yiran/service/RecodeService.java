package com.yiran.service;

public interface RecodeService {

    /**
     * 查询游戏的查看数
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


    /**
     * 通过游戏id删除记录数据
     * @param id 游戏id
     */
    void deleteByGameId(String id);
}
