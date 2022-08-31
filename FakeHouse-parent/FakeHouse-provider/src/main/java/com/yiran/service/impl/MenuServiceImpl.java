package com.yiran.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.MenuDao;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Menu;
import com.yiran.pojo.MenuExample;
import com.yiran.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {


    @Autowired
    private MenuDao menuDao;


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
    @Override
    public List<Menu> findMenuByUsername(String username) {


        // 查询出用户的角色后,查询用户的头菜单
        List<Menu> menus = menuDao.findTopMenuByUsername(username);

        List<Menu> children = null;
        for (Menu menu : menus) {
            // 通过父菜单查询子菜单, 并要求与username进行了关联
            // 用户的子菜单
            Map<String, Object> map = new HashMap<>(2);
            map.put("parentMenuId", menu.getId());
            map.put("username", username);
            children = menuDao.findChildrenByMenuId(map);
            menu.setChildren(children);
        }

        return menus;
    }

    /**
     * 分页查询菜单数据
     * @param queryPageBean 分页条件
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();
        Page<Menu> menes = null;
        // 查询, 情况1: queryString不为空, 情况2: 为空
        if (StringUtils.isEmpty(queryString)) {
            // 查询条件为空, 可以直接无条件查询
            menes = menuDao.findPageWithoutCondition();
        }else{
            // 查询字符串非空
            menes = menuDao.findPageLikeName("%"+queryString+"%");
        }
        return new PageResult(menes.getTotal(), menes.getResult());
    }

    /**
     * 添加菜单数据
     * @param menu 菜单的数据
     */
    @Override
    public void add(Menu menu) {
        menuDao.insertSelective(menu);
    }

    /**
     * 根据菜单id查询菜单
     * @param id 菜单id
     * @return
     */
    @Override
    public Menu findById(Integer id) {

        return menuDao.selectByPrimaryKey(id);
    }


    /**
     * 修改菜单信息
     * @param menu 菜单信息
     */
    @Override
    public void update(Menu menu) {

        menuDao.updateByPrimaryKeySelective(menu);

    }

    /**
     * 通过id删除菜单
     * @param id 菜单id
     */
    @Override
    public void delete(Integer id) {
        menuDao.deleteByPrimaryKey(id);
    }


    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Menu> findAll() {

        return menuDao.selectByExample(new MenuExample());
    }


}
