package com.yiran.jdbcTemplate.impl;

import com.yiran.jdbcTemplate.GameSelectDao;
import com.yiran.pojo.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class GameSelectDaoImpl implements GameSelectDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Game> findPage(String sql, ArrayList sqlParam) {
        // 使用jdbc方式执行sql
        List<Game> gameList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Game>(Game.class), sqlParam.toArray());
        return gameList;
    }

    @Override
    public Long findCounts(String countSql, ArrayList countParam) {

        Long count = jdbcTemplate.queryForObject(countSql, Long.class, countParam.toArray());
        return count;
    }
}
