package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {


    /**
     * 通过id查询
     * @param id 角色id
     * @return 权限对象集合
     */
    Set<Permission> findByRoleId(Integer id);

    /**
     * 通过关键字进行查询
     * @param queryString 查询字符串
     * @return 分页结果对象
     */
    Page<Permission> queryByString(String queryString);


    /**
     * 无条件分页查询
     * @return 分页结果
     */
    Page<Permission> queryAll();


    /**
     * 插入数据
     * @param permission 权限数据
     */
    void insert(Permission permission);


    /**
     * 通过id删除权限
     * @param id 权限id
     */
    void deleteById(Integer id);



    /**
     * 通过id查询
     * @param id 权限id
     * @return 权限信息
     */
    Permission findById(Integer id);


    /**
     * 通过id修改权限
     * @param permission 权限内容
     */
    void updateById(Permission permission);

    /**
     * 查询所有
     * @return 权限数据集合
     */
    List<Permission> findAll();

}
