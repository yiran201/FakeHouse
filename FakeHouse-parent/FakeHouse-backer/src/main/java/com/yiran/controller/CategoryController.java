package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.Result;
import com.yiran.pojo.Category;
import com.yiran.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    // 权限列表
    // GAME_CATEGORY_SELECT 通过游戏id查询分类信息


    @Reference
    private CategoryService categoryService;


    @GetMapping("/findByGameId")
    @PreAuthorize("hasAuthority('CATEGORY_SELECT')")
    private Result findByGameId(String gameId){

        try {
            if (gameId != null){
                List<Category> categoryList = categoryService.findByGameId(gameId);

                if (categoryList != null && categoryList.size() > 0){
                    return new Result(true, MessageConstant.QUERY_CATEGORY_BY_GAMEID_SUCCESS, categoryList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CATEGORY_BY_GAMEID_FAIL);
    }





}
