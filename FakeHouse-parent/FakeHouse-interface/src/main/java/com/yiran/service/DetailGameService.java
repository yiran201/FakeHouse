package com.yiran.service;

import com.yiran.pojo.Decoder;
import com.yiran.pojo.DetailGame;

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
}
