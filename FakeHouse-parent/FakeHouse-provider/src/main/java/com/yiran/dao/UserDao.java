package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.User;
import com.yiran.pojo.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    int countByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return 用户对象
     */
    User findUserByUserName(String username);


    /**
     * 通过用户名查询用户列表
     * 如果查询字符串为空,则查询所有;否则按照查询字符串进行模糊查询
     * @param queryString 查询字符串
     * @return 用户列表
     */
    Page<User> findPage(String queryString);

    /**
     * 无条件查询
     * @return
     */
    Page<User> findPageWithoutCondition();

    /**
     * 添加外键关系
     * @param map 用户id与角色id
     */
    void connectWithRole(Map<String, Object> map);

    /**
     * 通过用户id查询角色id
     * @param id 用户id
     * @return
     */
    List<Integer> findRoleId(Integer id);


    /**
     * 取消与角色表的关联关系
     * @param id 用户id
     */
    void disconnectWithRole(Integer id);
}