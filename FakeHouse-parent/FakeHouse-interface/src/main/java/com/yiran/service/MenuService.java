package com.yiran.service;

import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Menu;

import java.util.List;

public interface MenuService {

     /*
     * menuList:[
                {
                    "path": "1",
                    "title": "工作台",
                    "icon":"fa-dashboard",
                    "children": []
                },
                {
                    "path": "2",
                    "title": "会员管理",
                    "icon":"fa-user-md",
                    "children": [
                        {
                            "path": "/2-1",
                            "title": "会员档案",
                            "linkUrl":"member.html",
                            "children":[]
                        },
                        {
                            "path": "/2-2",
                            "title": "体检上传",
                            "children":[]
                        },
                        {
                            "path": "/2-3",
                            "title": "会员统计",
                            "linkUrl":"all-item-list.html",
                            "children":[]
                        },
                    ]
                }
            ]
     */
    /**
     * 查询出的数据要符合上诉格式
     * @param username 用户名,用于查询出用户角色
     * @return List Menu
     */
    List<Menu> findMenuByUsername(String username);


    /**
     * 分页查询菜单数据
     * @param queryPageBean 分页条件
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 添加菜单数据
     * @param menu 菜单的数据
     */
    void add(Menu menu);

    /**
     * 根据菜单id查询菜单
     * @param id 菜单id
     * @return
     */
    Menu findById(Integer id);


    /**
     * 修改菜单信息, 通过主键
     * @param menu 菜单信息
     */
    void update(Menu menu);


    /**
     * 通过id删除菜单
     * @param id 菜单id
     */
    void delete(Integer id);


    /**
     * 查询所有
     * @return
     */
    List<Menu> findAll();


}
