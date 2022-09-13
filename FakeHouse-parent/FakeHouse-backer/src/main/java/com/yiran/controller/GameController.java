package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.GameQueryPageBean;
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
     * 由于上面的查询方式无法满足复杂的情形, 所以需要进行等复杂查询方法的设计
     *  查询游戏数据, 携带游戏查看数和下载数
     * @param queryPageBean  分页条件
     * @return
     */
    @PostMapping("/findPageForManage")
    @PreAuthorize("hasAuthority('GAME_SELECT')")
    public Result findPageForManage(@RequestBody GameQueryPageBean queryPageBean){

//        System.out.println(queryPageBean);
        try {
            if (GameQueryPageBean.checkGameQueryPageBean(queryPageBean)){
//                System.out.println(queryPageBean);
                PageResult result = gameService.findPageForManage(queryPageBean);
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
     * 通过id查询游戏数据和游戏详情数据
     * @param id 游戏id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('GAME_SELECT')")
    public Result findById(String id){

        try {
            if (!StringUtils.isEmpty(id)){
                Map<String, Object> data = gameService.findById(id);
                return new Result(true, MessageConstant.QUERY_GAME_SUCCESS, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_GAME_FAIL);
    }

    /**
     * 查询分类id
     * @param id: 游戏id
     * @return
     */
    @GetMapping("/findCategoryId")
    @PreAuthorize("hasAuthority('GAME_SELECT')")
    public Result findCategoryId(String id){

        try {
            if (id != null){
                List<Integer> categoryId = gameService.findCategoryIdByGameId(id);
                return new Result(true, MessageConstant.QUERY_CATEGORY_ID_SUCCESS, categoryId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.QUERY_CATEGORY_ID_FAIL);
    }



    /**
     * 查询游戏集合
     * 是否需要添加分页和查询条件
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
    public Result add(@RequestBody Map<String, Object> gameData, @RequestParam(required = false) Integer[] categoryId){


        try {
            // 请求参数列表中 最好只用一个 @RequestBody
            // 使用map进行接收后 使用JSON进行转换
//        System.out.println(gameData);
            Map<String, Object> game_map = (Map<String, Object>) gameData.get("game");
            Map<String, Object> detailGame_map = (Map<String, Object>) gameData.get("detailGame");

            String decoderName = (String) game_map.get("decoderName");
            Game game = JSON.parseObject(JSON.toJSON(game_map).toString(), Game.class);
            DetailGame detailGame = JSON.parseObject(JSON.toJSON(detailGame_map).toString(), DetailGame.class);

            if (checkGame(game) && checkDetailGame(detailGame)){
                gameService.add(game, detailGame, categoryId, decoderName);
                return new Result(true, MessageConstant.ADD_GAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_GAME_FAIL);
    }


    /**
     * 添加游戏数据
     * @param gameData 游戏信息
     * @param categoryId 游戏分类id
     * @return 状态
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('GAME_UPDATE')")
    public Result update(@RequestBody Map<String, Object> gameData, @RequestParam(required = false) Integer[] categoryId) {

        try {
            Map<String, Object> game_map = (Map<String, Object>) gameData.get("game");
            Map<String, Object> detailGame_map = (Map<String, Object>) gameData.get("detailGame");


            String decoderName = null;
            if (game_map.containsKey("decoderName")){
                decoderName = (String) game_map.get("decoderName");
            }
            Game game = JSON.parseObject(JSON.toJSON(game_map).toString(), Game.class);
            DetailGame detailGame = JSON.parseObject(JSON.toJSON(detailGame_map).toString(), DetailGame.class);

            if (checkGame(game) && checkDetailGame(detailGame)){
                gameService.update(game, detailGame, categoryId, decoderName);
                return new Result(true, MessageConstant.UPDATE_GAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_GAME_FAIL);


    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('GAME_DELETE')")
    public Result deleteById(String id){

        try {
            if (!StringUtils.isEmpty(id)){
                gameService.deleteById(id);
                return new Result(true, MessageConstant.DELETE_GAME_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_GAME_FAIL);
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
            if (!StringUtils.isEmpty(capacity) && !StringUtils.isEmpty(name)
                    &&!StringUtils.isEmpty(originUrl) && size != null){
                return true;
            }
        }
        return false;
    }



}
