package com.yiran.service;


import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.User;

import java.io.IOException;
import java.util.List;

/**
 * 用于查询数据库中User表以及权限管理的信息
 */
public interface UserService {

    /**
     * 通过用户名查询用户信息
     *
     * @return
     */
    public User findUserByUserName(String username);



    /**
     * 查询用户分页数据
     * @return data
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 添加角色数据到数据库
     * @param user 用户数据
     * @param roleId 关联的角色id
     */
    void add(User user, Integer[] roleId) throws IOException;


    /**
     * 通过id查询用户信息
     * @param id 用户id
     * @return
     */
    User findById(Integer id);


    /**
     * 通过用户id查询角色id
     * @param id 用户id
     * @return
     */
    List<Integer> findRoleId(Integer id);


    /**
     * 查询用户名重复的次数 保证用户名只能出现一次
     * @param username 用户名
     * @return 出现次数
     */
    int countByUsername(String username);

    /**
     * 删除用户
     * @param user 用户信息
     * @param roleId 关联的角色id
     */
    void updateUser(User user, Integer[] roleId);


    /**
     * 通过用户id删除用户   危险操作
     * @param id 用户id
     */
    void delete(Integer id);


    /**
     * 用户注册
     * @param user 用户信息
     */
    void register(User user);

}
