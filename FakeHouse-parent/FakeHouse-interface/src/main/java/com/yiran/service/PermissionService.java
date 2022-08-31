package com.yiran.service;


import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 分页查询权限数据
     * @param pageBean 查询条件
     * @return 分页数据
     */
    PageResult findPage(QueryPageBean pageBean);

    /**
     * 添加permissionn数据
     * @param permission 权限数据
     */
    void add(Permission permission);

    /**
     * 删除权限
     * @param id 权限id
     */
    void delete(Integer id);


    /**
     * 通过id查询
     * @param id 权限id
     * @return 权限信息
     */
    Permission findById(Integer id);


    /**
     * 更新权限信息
     * @param permission 权限
     */
    void update(Permission permission);


    /**
     * 查询所有
     * @return 权限数据集合
     */
    List<Permission> findAll();


}
