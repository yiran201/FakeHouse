package com.yiran.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.RoleDao;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Role;
import com.yiran.service.MenuService;
import com.yiran.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = MenuService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 分页查询
     * @param queryPageBean 查询条件封装对象
     * @return 角色集合
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Role> page = null;
        String name = queryPageBean.getQueryString();
        // 说实话这个判断的逻辑确实是可以设置到sql中去,设置到sql中去其实是比较好的
        // 使用mybatis中的if逻辑即可
        if (!StringUtils.isEmpty(name))
        {
            // 查询条件非空 根据名字分页模糊查询
            page = roleDao.findLikeName("%"+ name + "%");
        }else
        {
            // 查询条件为空
            page = roleDao.findAll();
        }
        // 结果封装并返回

        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * 添加角色
     * @param role 角色信息
     * @param permissionIds 权限的id集合
     * @param menuIds 菜单的id集合
     */
    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {

        // 将角色添加到数据库中,并返回其主键
        // 保证该数据为新数据,在数据库表中为自增的主键
        role.setId(null);
        roleDao.insert(role);

        // 角色关联权限
        Integer id = role.getId();
        Map<String, Integer> map = new HashMap<>(2);
        map.put("roleId", id);
        for (Integer permissionId : permissionIds) {
            map.put("permissionId", permissionId);
            // 有两个参数,使用Map进行封装;当然可以设置一个javabean来进行封装,只是比较麻烦(可能需要多个),还不如直接使用Map对象
            roleDao.connectwithPermission(map);
        }
        // 角色关联菜单
        map.remove("permissionId");
        for (Integer menuId : menuIds) {
            map.put("menuId", menuId);
            roleDao.connectWithMenu(map);
        }
    }


    /**
     * 通过id查询
     * @param id 角色id
     * @return 角色信息
     */
    @Override
    public Role findById(Integer id) {
        return roleDao.selectByPrimaryKey(id);
    }


    /**
     * 查询权限id集合
     * @param id: 角色id
     * @return 权限id集合
     */
    @Override
    public List<Integer> findPermissionIds(Integer id) {

        return roleDao.findPermissionIds(id);

    }



    /**
     * 修改角色权限及信息
     * @param role 角色信息
     * @param permissionIds 权限关系
     * @param menuIds
     */
    @Override
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {


        // 通过角色id修改角色信息
        roleDao.updateByPrimaryKeySelective(role);

        // 删除以前的权限信息
        roleDao.disconnectWithPermission(role.getId());
        roleDao.disconnectWithMenu(role.getId());

        // 添加新的权限关系
        Map<String,Integer> map = new HashMap<>(2);
        map.put("roleId", role.getId());
        for (Integer permissionId : permissionIds) {
            map.put("permissionId", permissionId);
            roleDao.connectwithPermission(map);
        }
        // 关联菜单
        map.remove("permissionId");
        for (Integer menuId : menuIds) {
            map.put("menuId", menuId);
            roleDao.connectWithMenu(map);
        }
    }


    /**
     * 删除角色
     * @param id 角色id
     * @return status
     */
    @Override
    public void delete(Integer id) {

        // 删除与权限表的关联
        roleDao.disconnectWithPermission(id);
        // 删除与菜单表的关联关系
        roleDao.disconnectWithMenu(id);

        // 删除角色信息
        roleDao.deleteByPrimaryKey(id);
    }


    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<Role> findAll() {

        return roleDao.findList();

    }

    @Override
    public List<Integer> findMenuIds(Integer id) {
        return roleDao.findMenuIds(id);
    }
}
