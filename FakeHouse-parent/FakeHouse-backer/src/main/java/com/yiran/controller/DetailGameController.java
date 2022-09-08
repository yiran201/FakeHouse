package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Character;
import com.yiran.pojo.DetailGame;
import com.yiran.service.DetailGameService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detailGame")
public class DetailGameController {

    // 权限列表
    // GAME_DETAILGAME_SELECT 通过游戏id查询分类信息


    @Reference
    private DetailGameService detailGameService;


    // 不能够提供添加功能, 因为不可能添加成功, 就算添加了也没有意义
    // 需要关联游戏id, 游戏详情数据才有意义, 因为其是游戏表t_game的扩展表, 所以进行添加之后没法进行关联, 没有意义.
//    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('DETAILGAME_ADD')")
    public Result add(@RequestBody DetailGame detailGame){

        // 校验分析信息
        try {
            if (checkDetailGame(detailGame)){
                detailGameService.add(detailGame);
                return new Result(true, MessageConstant.ADD_DETAILGAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_DETAILGAME_FAIL);
    }



    /**
     * 查询分页数据
     * @param queryPageBean 查询条件封装
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('DETAILGAME_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean, Integer column){

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)){
                if (column == null){
                    column = 1;
                }
                PageResult result = detailGameService.findPage(queryPageBean, column);
                return new Result(true, MessageConstant.QUERY_DETAILGAME_SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_DETAILGAME_FAIL);
    }


    /**
     * 通过分类id查询分类数据
     * @param id 分类id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('DETAILGAME_SELECT')")
    public Result findById(String id){

        if (id != null){
            DetailGame detailGame = detailGameService.findById(id);
            if (detailGame != null){
                return new Result(true, MessageConstant.QUERY_DETAILGAME_SUCCESS, detailGame);
            }
        }
        return new Result(false, MessageConstant.QUERY_DETAILGAME_FAIL);
    }


    /**
     * 修改分类数据
     * @param detailGame 分类数据
     * @return
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('DETAILGAME_UPDATE')")
    public Result update(@RequestBody DetailGame detailGame){

        try {
            if (checkDetailGame(detailGame) && detailGame.getId() != null){
                detailGameService.updateById(detailGame);
                return new Result(true, MessageConstant.UPDATE_DETAILGAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_DETAILGAME_FAIL);
    }



    // 本来不该在此处提供删除逻辑, 但是由于不小心就实现了
    // 理论情况下是每一次删除都会报错
    /**
     * 通过id删除
     * @param id 分类id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DETAILGAME_DELETE')")
    public Result delete(String id){

        try {
            if (id != null){
                boolean flag = detailGameService.deleteById(id);
                if (flag) {
                    return new Result(true, MessageConstant.DELETE_DETAILGAME_SUCCESS);
                }else{
                    return new Result(false, MessageConstant.DETAILGAME_CONNECTED);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_DETAILGAME_FAIL);
    }


    /**
     * 通过detailId查询 游戏特色数据
     * @param detailId 游戏详情id
     * @return
     */
    @GetMapping("/findCharas")
    @PreAuthorize("hasAuthority('DETAILGAME_SELECT')")
    public Result findCharas(String detailId){

        try {
            if (detailId != null){
                List<Character> characters = detailGameService.findCharas(detailId);
                return new Result(true, MessageConstant.QUERY_CHARACTER_SUCCESS, characters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHARACTER_FAIL);
    }


    private static boolean checkDetailGame(DetailGame detailGame) {

        if (detailGame != null){
            // 允许详情数据全为空
            return true;
        }
        return false;
    }

}
