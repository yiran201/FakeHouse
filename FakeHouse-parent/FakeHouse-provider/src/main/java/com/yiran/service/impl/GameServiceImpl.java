package com.yiran.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.GameMapper;
import com.yiran.jdbcTemplate.GameSelectDao;
import com.yiran.entity.GameQueryPageBean;
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

import java.util.*;

@Service(interfaceClass = GameService.class)
@Transactional
public class GameServiceImpl implements GameService{


    // 需要调用其他的service
    @Autowired
    private RecodeService recodeService;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameSelectDao gameSelectDao;


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
     * 升级后的分页查询, 可以通过多个字段控制查询
     * @param queryPageBean 查询条件
     * @return
     */
    @Override
    public PageResult findPageForManage(GameQueryPageBean queryPageBean) {
        // 由于查询条件过于复杂, 并且表的结构也过于复杂, 导致无使用mybatis进行查询
        // 这里也提醒了我, 有时其实没有必要去遵循范式, 现在导致的就是, 查询条件过于复杂
        // 其实在设计表的时候, 不遵循范式的话, 查询方法会变得很简单;
        // 就像此处的 游戏表和游戏详情表的破解者的表, 其实应该合为一个表, 这样的话就可以简单的进行查询
        ArrayList sqlParam = new ArrayList();
        ArrayList countParam =  new ArrayList();

        // 进行sql的拼接
        String orderType = queryPageBean.getOrderType();
        String categoryName = queryPageBean.getCategoryName();
        Integer currentPage = queryPageBean.getCurrentPage();
        String orderColumn = queryPageBean.getOrderColumn();
        Integer pageSize = queryPageBean.getPageSize();
        String queryColumn = queryPageBean.getQueryColumn();
        String queryString = queryPageBean.getQueryString();
        Boolean active = queryPageBean.getActive();
        StringBuilder sql = new StringBuilder();

        // 首先判断分类查询是否为空
        boolean isCategoryNull = false;   // 分类查询是否为空
        if (!StringUtils.isEmpty(categoryName)){
            sql.append(" select tg.* from t_game tg, t_game_detail tgd, t_decoder td, t_category tc, t_game_category tgc ");
        }else{
            isCategoryNull = true;
            sql.append(" select tg.* from t_game tg, t_game_detail tgd, t_decoder td ");
        }
        // 判断查询条件是否为空
        if (!StringUtils.isEmpty(queryString) && !StringUtils.isEmpty(queryColumn)){
            String queryString_s = "%"+queryString+"%";
            // 判断查询字段
            if (queryColumn.equals("name")){
                if (isCategoryNull){
                    sql.append("where (tg.name like ? or tg.chsName like ?) ");
                    sql.append("and tg.detail_id = tgd.id ");
                    sql.append("and tg.decoder_id = td.id ");
                    sqlParam.add(queryString_s);
                    sqlParam.add(queryString_s);
                }else{
                    sql.append("where tc.name = ? ");
                    sql.append("and tc.id = tgc.category_id ");
                    sql.append("and tgc.game_id = tg.id ");
                    sql.append("and (tg.name like ? or tg.chsName like ?) ");
                    sql.append("and tg.detail_id = tgd.id ");
                    sql.append("and tg.decoder_id = td.id ");
                    sqlParam.add(categoryName);
                    sqlParam.add(queryString_s);
                    sqlParam.add(queryString_s);
                }
            }else{
                if (isCategoryNull){
                    sql.append("where ("+queryColumn+" like ? or "+queryColumn+" = ?) ");
                    sql.append("and tg.detail_id = tgd.id ");
                    sql.append("and tg.decoder_id = td.id ");
                    sqlParam.add(queryString_s);
                    sqlParam.add(queryString);
                }else{
                    sql.append("where tc.name = ? ");
                    sql.append("and tc.id = tgc.category_id ");
                    sql.append("and tgc.game_id = tg.id ");
                    sql.append("and ("+queryColumn+" like ? or "+queryColumn+" = ?) ");
                    sql.append("and tg.detail_id = tgd.id ");
                    sql.append("and tg.decoder_id = td.id ");
                    sqlParam.add(categoryName);
                    sqlParam.add(queryString_s);
                    sqlParam.add(queryString);
                }
            }
        }else{
            // 不需要查询字段
            if (isCategoryNull){
                sql.append("where tg.detail_id = tgd.id ");
                sql.append("and tg.decoder_id = td.id ");
            }else{
                sql.append("where tc.name = ? ");
                sql.append("and tc.id = tgc.category_id ");
                sql.append("and tgc.game_id = tg.id ");
                sql.append("and tg.detail_id = tgd.id ");
                sql.append("and tg.decoder_id = td.id ");
                sqlParam.add(categoryName);
            }
        }
        if (active){
            sql.append("and active = false ");
        }

        // 数目查询条件
        String countSql = new String(sql).replace("tg.*", "count(tg.id)");
        // 使用线程安全的方法进行复制
        for (Object o : sqlParam) {
            countParam.add(o);
        }


        // 进行排序
        if (!StringUtils.isEmpty(orderColumn)){
            sql.append("order by "+orderColumn+" ");
        }else{
            sql.append("order by insertTime ");
        }

        // 排序方式
        if (!StringUtils.isEmpty(orderType) && orderType.equalsIgnoreCase("desc")){
            sql.append("desc ");
        }else{
            sql.append("asc ");
        }

        // 分页查询
        sql.append("limit ?,?");
        sqlParam.add((currentPage-1)* pageSize);
        sqlParam.add(pageSize);

        // 测试发现List出现线程不完全的问题, 需要进行处理
        // 测试拼接的sql是否出问题
//        System.out.println(sql);
//        System.out.println(sqlParam);
//        System.out.println(countSql);
//        System.out.println(countParam);

        // 使用mybatis解决不了问题, 使用JdbcTemplate去解决问题

        List<Game> gameList = gameSelectDao.findPage(new String(sql), sqlParam);
//
        Long count = gameSelectDao.findCounts(countSql, countParam);

        // 查询出下载数和查看数
        findCount(gameList);

        return new PageResult(count, gameList);
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
            // 游戏详情表中存在着游戏的详情数据
            // 此处没有进行添加, 对于该页面, 不应该存在能够修改页面详情数据的方式
            // 之后通过别的服务进行实现  游戏详情页面
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
            gameMapper.connectWithCategory(map);
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
//        game.setInsertTime(null);
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
            // 游戏详情表中存在着游戏的详情数据
            // 此处没有进行查询, 对于该页面, 不应该存在能够修改页面详情数据的方式
            // 之后通过别的服务进行实现  游戏详情页面
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
        game_map.put("active", game_map.get("active").toString());
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
     * 修改游戏数据
     * @param game 游戏信息
     * @param detailGame 游戏详情信息
     * @param categoryId 游戏分类id
     * @param decoderName 游戏破解者名称
     */
    @Override
    public void update(Game game, DetailGame detailGame, Integer[] categoryId, String decoderName) throws Exception {

        if (StringUtils.isEmpty(game.getId())){
            throw new Exception("游戏信息不完整!");
        }

        // 修改decoder数据 获取到返回的decoder的id
        Integer decoderId = null;
        if (!StringUtils.isEmpty(decoderName)){
            Decoder decoder = new Decoder();
            decoder.setName(decoderName);
            decoderId = decoderService.add(decoder);
        }


        // 修改游戏详情数据
        // 存在一种特例情况, detailId为空, 因为插入数据是没有关联上detail的信息
        // 于是要进行处理  获取到前端的数据中不会包含id, 所以要进行设置
        String detailId = null;
        if (detailGame != null){
            if (!StringUtils.isEmpty(detailGame.getId())){
                detailGameService.updateById(detailGame);
            }else{
                // 游戏详情表中存在着游戏的详情数据
                // 此处没有进行修改, 对于该页面, 不应该存在能够修改页面详情数据的方式
                // 之后通过别的服务进行实现  游戏详情页面
                detailId = Long.toString(idWorker.nextId());
                detailGame.setId(detailId);
                detailGameService.add(detailGame);
            }
        }

        // 修改游戏数据
        game.setInsertTime(null);
        // 存在detailId为空的情况, 就是detailGame的信息能够正常更新的情况 此时不会进行更新
        // 如果detailId不为空, 说明当时游戏数据插入时没有带上游戏详情数据
        game.setDetailId(detailId);
        game.setDecoderId(decoderId);
        // bug: 无法修改decoderId为空, 因为一旦为空, 就不会进行更新, 从而导致修改失败
        if (!StringUtils.isEmpty(game.getId())){
            gameMapper.updateGameByPrimaryKey(game);
        }

        // 修改游戏与分类的关系
        gameMapper.disconnectWithCategory(game.getId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("gameId", game.getId());
        for (Integer cid : categoryId) {
            map.put("categoryId", cid);
            gameMapper.connectWithCategory(map);
        }

    }


    /**
     * 通过游戏id删除游戏
     * @param id 游戏id
     */
    @Override
    public void deleteById(String id) {

        // 查询出游戏数据
        Game game = gameMapper.selectByPrimaryKey(id);

        // 删除游戏分类表关系
        gameMapper.disconnectWithCategory(id);

        // 将游戏的删除, 并将用户记录表中的记录删除
        gameMapper.deleteByPrimaryKey(id);

        // 要先将用户-游戏表的数据删除
        recodeService.deleteByGameId(id);

        // 删除游戏详情数据
        if (game.getDetailId() != null){
            // 删除游戏详情数据, 要求要将游戏特色数据也删除, 否则会约束导致删除失败
            detailGameService.deleteById(game.getDetailId());
        }


    }


    /**
     * 通过decoder的id查询游戏id
     * @param id decoder 的 id
     * @return
     */
    @Override
    public List<String> findGameIdsByDecoderId(Integer id) {

        return gameMapper.findIdsByDecoderId(id);

    }


    /**
     * 通过decoder的id查询游戏的id数目
     * @param id decoder 的 id
     * @return
     */
    @Override
    public Integer findCountByDecoderId(Integer id) {
        return gameMapper.findCountByDecoderId(id);
    }


    /**
     * 查询游戏id
     * @param detailId 游戏详情id
     * @return
     */
    @Override
    public String findIdByDetailId(String detailId) {

        return gameMapper.findIdByDetailId(detailId);
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
     * 为列表数据填充查看数和下载数
     * @param gameList 列表数据
     */
    private void findCount(List<Game> gameList){
        for (Game game : gameList) {
            findCount(game);
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
