package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Menu;
import com.yiran.pojo.MenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {


    /**
     * 通过用户名查询出用户的全部父菜单
     * @param username 用户名
     */
    List<Menu> findTopMenuByUsername(String username);

    /**
     * 通过父菜单的id查询子菜单
     * map 两个键  username&id  username代表用户名, id代表父标签id
     * @return
     */
    List<Menu> findChildrenByMenuId(Map map);

    int countByExample(MenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);


    /**
     * 无条件分页查询
     * @return
     */
    Page<Menu> findPageWithoutCondition();

    /**
     * 根据名字进行模糊查询
     * @param s 处理好的字符串
     * @return
     */
    Page<Menu> findPageLikeName(String s);
}
