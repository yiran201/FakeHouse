package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.RecodeMapper;
import com.yiran.service.RecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service(interfaceClass = RecodeService.class)
@Transactional
public class RecodeServiceImpl implements RecodeService {


    @Autowired
    private RecodeMapper recodeMapper;

    /**
     * 通过游戏id查询查询游戏查看数
     * @param game_id: 游戏id
     * @return
     */
    @Override
    public int findWatchCountByGameId(String game_id) {

        return recodeMapper.findWatchCountByGameId(game_id);
    }


    /**
     * 查询游戏的下载数
     * @param game_id: 游戏id
     * @return
     */
    @Override
    public int findDownloadCountByGameId(String game_id) {
        return recodeMapper.findDownloadCountByGameId(game_id);
    }


    /**
     * 通过游戏id删除记录数据
     * @param id 游戏id
     */
    @Override
    public void deleteByGameId(String id) {

        if (!StringUtils.isEmpty(id)){
            recodeMapper.deleteByGameId(id);
        }


    }
}
