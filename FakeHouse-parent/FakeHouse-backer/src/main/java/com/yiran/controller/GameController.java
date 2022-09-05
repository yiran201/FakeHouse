package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Category;
import com.yiran.pojo.DetailGame;
import com.yiran.pojo.Game;
import com.yiran.service.GameService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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


    /**
     * 查询游戏集合
     * @return
     */
    @GetMapping("/findCategoryList")
    @PreAuthorize("hasAuthority('GAME_SELECT')")
    public Result findCategoryList(){

        try {
            List<Category> categoryList = gameService.findCategoryList();
            if (categoryList != null && categoryList.size() > 0){
                return new Result(true, MessageConstant.QUERY_CATEGORY_SUCCESS, categoryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.QUERY_CATEGORY_FAIL);
    }


    /**
     * 添加游戏数据
     * @param gameData 游戏信息
     * @param categoryId 游戏分类id
     * @return 状态
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('GAME_ADD')")
    public Result add(@RequestBody Map<String, Object> gameData, @RequestParam(required = false) Integer[] categoryId,
                      HttpServletRequest request){

        // 请求参数列表中 最好只用一个 @RequestBody
        // 使用map进行接收后 使用JSON进行转换
//        System.out.println(gameData);
        Map<String, Object> game_map = (Map<String, Object>) gameData.get("game");
        Map<String, Object> detailGame_map = (Map<String, Object>) gameData.get("detailGame");


        String decoderName = (String) game_map.get("decoderName");
        Game game = JSON.parseObject(JSON.toJSON(game_map).toString(), Game.class);
        DetailGame detailGame = JSON.parseObject(JSON.toJSON(detailGame_map).toString(), DetailGame.class);

        try {
            if (checkGame(game) && checkDetailGame(detailGame)){
                gameService.add(game, detailGame, categoryId, decoderName);
                return new Result(true, MessageConstant.ADD_GAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_GAME_FAIL);
    }

    // 检验游戏详情详情信息是否完整
    private static boolean checkDetailGame(DetailGame detailGame) {

        if (detailGame != null){
            // 由于游戏的详情信息时可以为空的, 所以信息可以为空
            return true;
        }
        return false;
    }

    // 检验Game信息是否完整
    private static boolean checkGame(Game game) {

        if (game != null){
            String capacity = game.getCapacity();
            String name = game.getName();
            String originUrl = game.getOriginUrl();
            Long size = game.getSize();
            String downloadUrl = game.getDownloadUrl();
            if (!StringUtils.isEmpty(capacity) && !StringUtils.isEmpty(name)
                    &&!StringUtils.isEmpty(originUrl) && !StringUtils.isEmpty(downloadUrl) && size != null){
                return true;
            }
        }
        return false;
    }



}
