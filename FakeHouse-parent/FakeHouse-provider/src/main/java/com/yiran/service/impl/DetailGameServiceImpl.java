package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.DetailGameMapper;
import com.yiran.pojo.DetailGame;
import com.yiran.service.CharacterService;
import com.yiran.service.DetailGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service(interfaceClass = DetailGameService.class)
@Transactional
public class DetailGameServiceImpl implements DetailGameService {

    // 游戏特色数据服务
    @Autowired
    private CharacterService characterService;

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


    /**
     * 通过id查询游戏详情信息
     * @param detailId 游戏详情id
     * @return
     */
    @Override
    public DetailGame findById(String detailId) {

        return detailGameMapper.selectByPrimaryKey(detailId);

    }

    /**
     * 通过id删除游戏详情数据
     * @param detailGame 游戏详情
     */
    @Override
    public void updateById(DetailGame detailGame) {

        if (detailGame != null && !StringUtils.isEmpty(detailGame.getId())){
            detailGameMapper.updateByPrimaryKeySelective(detailGame);
        }

    }



    /**
     * 删除游戏详情数据, 要求将游戏关联的特色数据也彻底删除  两者为依存关系
     * @param detailId 游戏详情的id
     */
    @Override
    public void deleteById(String detailId) {

        if (!StringUtils.isEmpty(detailId)){

            // 当时愚蠢了没有想到, 可以一起删除
            // 通过detailGame的id删除其关联的所有 character数据
            characterService.deleteByDetailGameId(detailId);

//            // 查询出游戏的详情数据
//            List<String> charaIds = detailGameMapper.findCharaIds(detailId);
//
//            // 通过游戏详情id将游戏的特色数据删除
//            // 此处游戏详情表与游戏特色表是一对多的关系, 所以不用担心出现游戏特色数据被其他游戏详情数据关联的情况出现
//            if (charaIds != null && charaIds.size() > 0){
//                // 删除关联关系
//                detailGameMapper.disconnectWithChara(detailId);
//                // 删除游戏特色数据 此处事宜使用循环一条一条数据删除, 所以使用列表数据进行删除比较合适
//                characterService.deleteByList(charaIds);
//            }

            // 删除游戏详情数据
            detailGameMapper.deleteByPrimaryKey(detailId);
        }
    }


}
