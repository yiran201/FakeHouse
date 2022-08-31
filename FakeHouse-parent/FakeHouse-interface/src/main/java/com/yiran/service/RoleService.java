package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Role;

import java.util.List;

public interface RoleService {


    /**
     * 分页查询
     * @param queryPageBean 查询条件封装对象
     * @return 角色集合
     */
    PageResult findPage(QueryPageBean queryPageBean);


    /**
     * 添加角色
     * @param role 角色信息
     * @param permissionIds 权限的id集合
     * @param menuIds
     */
    void add(Role role, Integer[] permissionIds, Integer[] menuIds);


    /**
     * 通过id查询
     * @param id 角色id
     * @return 角色信息
     */
    Role findById(Integer id);


    /**
     * 查询权限id集合
     * @param id: 角色id
     * @return 权限id集合
     */
    List<Integer> findPermissionIds(Integer id);


    /**
     * 修改角色权限及信息
     * @param role 角色信息
     * @param permissionIds 权限关系
     * @param menuIds
     */
    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);


    /**
     * 删除角色
     * @param id 角色id
     * @return status
     */
    void delete(Integer id);



    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();


    /**
     * 查询角色关联的菜单id
     * @param id 角色id
     * @return
     */
    List<Integer> findMenuIds(Integer id);
}
