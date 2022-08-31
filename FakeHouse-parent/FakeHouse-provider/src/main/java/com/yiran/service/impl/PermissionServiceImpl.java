package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.PermissionDao;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Permission;
import com.yiran.service.MenuService;
import com.yiran.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = MenuService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 分页查询权限数据
     * @param pageBean 查询条件
     * @return 分页数据
     */
    @Override
    public PageResult findPage(QueryPageBean pageBean) {

        // 进行分页
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());

        // 如果 queryString为空,则不用进行条件查询
        Page<Permission> page = null;
        // 现在对queryString进行判断
        String queryString = pageBean.getQueryString();
        if (queryString != null && queryString.length()>0)
        {
            page = permissionDao.queryByString("%"+queryString+"%");
        }else{
            page = permissionDao.queryAll();
        }

        // 封装结果对象
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    // 话说新增权限的逻辑应该是没有用的,因为业务发布的时候,就算新增权限,业务逻辑也是不可改变的,因为代码已经写好了
    // 又话说回来,修改这是不是可以用来以后对其他系统的权限系统的管理,如果权限的设置是一样的话
    // 好像是可以,之后要进行编辑,分出来一个系统进行, 权限表的维护工作,前提是要创建数据库的sql是一样的

    /**
     * 添加权限数据
     * @param permission 权限数据
     */
    @Override
    public void add(Permission permission) {
        // 测试异常是否会通过dubbo告诉消费者
//        int a = 1/0;
        permissionDao.insert(permission);
    }


    // 删除权限,删除权限属于危险操作
    /**
     * 删除权限
     * @param id 权限id
     */
    @Override
    public void delete(Integer id) {

        permissionDao.deleteById(id);

    }


    /**
     * 通过id查询
     * @param id 权限id
     * @return 权限信息
     */
    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }


    /**
     * 更新权限信息
     * @param permission 权限
     */
    @Override
    public void update(Permission permission) {
        permissionDao.updateById(permission);
    }


    /**
     * 查询所有
     * @return 权限数据集合
     */
    @Override
    public List<Permission> findAll() {

        return permissionDao.findAll();
    }


}
