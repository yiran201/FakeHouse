package com.yiran.service;

import java.util.List;

public interface CharacterService {

    /**
     * 通过id删除
     * @param charaIds id集合
     */
    void deleteByList(List<String> charaIds);
}
