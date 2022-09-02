package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.service.GameService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {


    @Reference
    private GameService gameService;


    /**
     *  查询游戏数据, 携带游戏查看数和下载数
     * @param queryPageBean  分页条件
     * @return
     */
    @PostMapping("/findPageWithCount")
    @PreAuthorize("hasAuthority('GAME_SELECT')")
    public Result findPageWithCount(@RequestBody QueryPageBean queryPageBean){

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)){
                // 查询游戏数据  携带游戏下载数与查看数
                PageResult result = gameService.findPageWithCount(queryPageBean);
                if (result != null){
                    return new Result(true, MessageConstant.QUERY_GAME_SUCCESS, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_GAME_FAIL);
    }







}
