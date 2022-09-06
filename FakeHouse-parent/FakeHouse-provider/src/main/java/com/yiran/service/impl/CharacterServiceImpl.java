package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.CharacterMapper;
import com.yiran.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CharacterService.class)
@Transactional
public class CharacterServiceImpl implements CharacterService {


    @Autowired
    private CharacterMapper characterMapper;

    /**
     * 通过id删除
     * @param charaIds id集合
     */
    @Override
    public void deleteByList(List<String> charaIds) {

        characterMapper.deleteByList(charaIds);

    }
}
