package com.yiran.jdbcTemplate;

import com.yiran.pojo.Game;

import java.util.ArrayList;
import java.util.List;

public interface GameSelectDao {


    /**
     * 执行分页查询语句
     * @param sql sql语句
     * @param sqlParam 参数
     * @return
     */
    List<Game> findPage(String sql, ArrayList sqlParam);

    /**
     * 查询数目
     * @param countSql sql语句
     * @param countParam 参数
     * @return
     */
    Long findCounts(String countSql, ArrayList countParam);
}
