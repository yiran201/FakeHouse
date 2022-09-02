package com.yiran.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.GameMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Game;
import com.yiran.service.GameService;
import com.yiran.service.RecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service(interfaceClass = GameService.class)
@Transactional
public class GameServiceImpl implements GameService{


    // 需要调用其他的service
    @Autowired
    private RecodeService recodeService;

    @Autowired
    private GameMapper gameMapper;

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
            // 查询观看数
            int watchCount = recodeService.findWatchCountByGameId(game.getId());
            game.setWatchCount(watchCount);
            // 查询下载数
            int downloadCount = recodeService.findDownloadCountByGameId(game.getId());
            game.setDownloadCount(downloadCount);
        }
    }

}
