package com.yiran.dao;

import com.github.pagehelper.Page;
import com.yiran.pojo.Role;
import com.yiran.pojo.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    /**
     * 通过User表的id查询角色集合
     * @param id User id
     * @return 该用户对应的角色列表
     */
    Set<Role> findByUserId(Integer id);

    int countByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);




    /**
     * 分页查询所有
     * @return Page
     */
    Page<Role> findAll();


    /**
     * 分页模糊查询
     * @param s 查询的字符串
     * @return Page
     */
    Page<Role> findLikeName(String s);


    /**
     * 添加数据到中间表
     * @param map 数据
     */
    void connectwithPermission(Map<String, Integer> map);


    /**
     * 查询权限id集合
     * @param id 角色id
     * @return 权限id集合
     */
    List<Integer> findPermissionIds(Integer id);


    /**
     * 删除与roleId关联的权限
     */
    void disconnectWithPermission(Integer id);


    /**
     * 查询角色的list集合  无条件
     * @return
     */
    List<Role> findList();

    /**
     * 查询角色关联的菜单id
     * @param id 角色id
     */
    List<Integer> findMenuIds(Integer id);


    /**
     * 删除与菜单表的关联关系
     * @param id 角色id
     */
    void disconnectWithMenu(Integer id);

    /**
     * 角色关联菜单
     * @param map 关联id  roleId, menuId
     */
    void connectWithMenu(Map<String, Integer> map);
}
