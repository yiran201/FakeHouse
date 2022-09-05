package com.yiran.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.GameMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Category;
import com.yiran.pojo.Decoder;
import com.yiran.pojo.DetailGame;
import com.yiran.pojo.Game;
import com.yiran.service.*;
import com.yiran.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = GameService.class)
@Transactional
public class GameServiceImpl implements GameService{


    // 需要调用其他的service
    @Autowired
    private RecodeService recodeService;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private DetailGameService detailGameService;

    @Autowired
    private DecoderService decoderService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询游戏数据, 携带游戏查看数和下载数
     * @param queryPageBean 分页条件
     * @return
     */
    @Override
    public PageResult findPageWithCount(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        // 默认通过游戏名称进行查询
        String queryString = queryPageBean.getQueryString();

        Page<Game> result = null;
        if (StringUtils.isEmpty(queryString)){
            // 查询条件为空, 全部查询
            result = gameMapper.findAll();
        }else{
            result = gameMapper.findByName("%"+queryString+"%");
        }

        if (result != null){
            fillGameWithCount(result);
            return new PageResult(result.getTotal(), result.getResult());
        }

        // 没有查询到数据, 返回空
        return null;
    }


    /**
     * 查询分类信息
     * @return 分类列表
     */
    @Override
    public List<Category> findCategoryList() {
        // 调用其他的微服务, 查询所有分类, 其实此处业务有点不合理, 不应该查询所有分类的, 如果分类太多的话, 会很麻烦
        return categoryService.findAll();
    }



    /**
     * 添加游戏数据
     * @param game 游戏信息
     * @param detailGame 游戏详情信息
     * @param categoryId 游戏分类id
     * @param decoderName 游戏破解者名称
     */
    @Override
    public void add(Game game, DetailGame detailGame, Integer[] categoryId, String decoderName) {

        // 首先应该添加破解者的信息
        // 如果破解者存在则直接返回id即可
        Integer decoderId = null;
        if (!StringUtils.isEmpty(decoderName)){
            Decoder decoder = new Decoder();
            decoder.setName(decoderName);
            decoderId = decoderService.add(decoder);
        }

        // 之后添加游戏详情信息
        // idWorker生成id  要转换为 string类型, 并设置到detailGame中
        String detailGameId = Long.toString(idWorker.nextId());
        if (!StringUtils.isEmpty(detailGameId)){
            detailGame.setId(detailGameId);
            detailGameService.add(detailGame);
        }

        // 之后添加游戏信息, 需要用到前面两者的信息
        String gameId = Long.toString(idWorker.nextId());
        game.setId(gameId);
        game.setDecoderId(decoderId);
        game.setDetailId(detailGameId);
        game.setUploadTime(new Date());
        if (!StringUtils.isEmpty(gameId)){
            gameMapper.insertSelective(game);
        }

        // 添加游戏分类信息
        Map<String, Object> map = new HashMap<>(2);
        map.put("gameId", gameId);
        for (Integer category : categoryId) {
            map.put("categoryId", category);
            categoryService.connectWithGame(map);
        }

    }

    /**
     * 通过id查询游戏数据和游戏详情数据
     * @param id 游戏id
     * @return
     */
    @Override
    public Map<String, Object> findById(String id) {

        // 查询游戏数据
        Game game = gameMapper.selectByPrimaryKey(id);
        // 处理敏感数据
        game.setInsertTime(null);
        // 填充查看数和下载数数据
//        findCount(game);

        // 查询游戏decoder
        Decoder decoder = null;
        Integer decoderId = game.getDecoderId();
        if (decoderId != null){
            decoder = decoderService.findById(decoderId);
        }

        // 查询游戏详情数据
        DetailGame detailGame = null;
        String detailId = game.getDetailId();
        if (!StringUtils.isEmpty(detailId)){
            detailGame = detailGameService.findById(detailId);
        }

        // 封装数据后返回
        Map<String, Object> map = new HashMap<>(2);

        Map<String, Object> game_map = JSON.parseObject(JSON.toJSON(game).toString(), Map.class);
        if (decoder != null){
            game_map.put("decoderName", decoder.getName());
        }else{
            game_map.put("decoderName", "");
        }
        map.put("game", game_map);

        if (detailGame != null){
            map.put("detailGame", JSON.parseObject(JSON.toJSON(detailGame).toString(), Map.class));
        }else{
            // 空对象  相当于JSON中的 {}
            map.put("detailGame", new Object());
        }

        return map;

    }

    /**
     * 通过游戏id查询分类id
     * @param id 游戏id
     * @return
     */
    @Override
    public List<Integer> findCategoryIdByGameId(String id) {

        return categoryService.findIdByGameId(id);
    }

    /**
     * 给游戏填充count数据
     * 该反方法废除, 因为不好用, 对资源消耗大
     * 调用了其他微服务两次, 直接使用复杂的sql进行一次查询比较快, 但是sql会比较复杂
     * 后来进行尝试时发现, 竟然实现不了, 由于表结构及表关系的原因无法进行查询, 或者存在我不知道的方法可以实现
     * 所以方法依然要使用, 不能淘汰
     */
    private void fillGameWithCount(Page<Game> result){
        // 该逻辑的查询效率差, 但是没有办法, 不存在能够一次查询出两个字段的sql语句
        // 没有简单的sql语句能够实现, 但是复杂的可以
        for (Game game : result) {
            // 处理敏感数据
//            game.setInsertTime(null);

            // 查询观看数
            int watchCount = recodeService.findWatchCountByGameId(game.getId());
            game.setWatchCount(watchCount);
            // 查询下载数
            int downloadCount = recodeService.findDownloadCountByGameId(game.getId());
            game.setDownloadCount(downloadCount);
        }
    }

    /**
     * 查询查看数据
     * @param game 游戏数据
     */
    private void findCount(Game game){
        // 查询观看数
        int watchCount = recodeService.findWatchCountByGameId(game.getId());
        game.setWatchCount(watchCount);
        // 查询下载数
        int downloadCount = recodeService.findDownloadCountByGameId(game.getId());
        game.setDownloadCount(downloadCount);
    }
}
