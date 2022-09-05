package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.DetailGameMapper;
import com.yiran.pojo.DetailGame;
import com.yiran.service.DetailGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = DetailGameService.class)
@Transactional
public class DetailGameServiceImpl implements DetailGameService {


    @Autowired
    private DetailGameMapper detailGameMapper;

    /**
     * 添加游戏详情数据
     * @param detailGame 游戏详情数据
     */
    @Override
    public void add(DetailGame detailGame) {

        detailGameMapper.insertSelective(detailGame);
    }
}
