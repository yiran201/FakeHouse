package com.yiran.controller;


import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Menu;
import com.yiran.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    /**
     * 查询所有
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('MENU_SELECT')")
    public Result findAll(){

        try {
            List<Menu> menus = menuService.findAll();
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    public Result delete(Integer id){
        try {
            if (id != null){
                // 通过id删除数据
                menuService.delete(id);
                return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_MENU_FAIL);
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('MENU_UPDATE')")
    public Result update(@RequestBody Menu menu){

        try {
            if (checkMenu(menu)){
                menuService.update(menu);
                return new Result(true, MessageConstant.UPDATE_MENU_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_MENU_FAIL);
    }


    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('MENU_SELECT')")
    public Result findById(Integer id){

        try {
            if (id != null){
                // 查询菜单的主要信息
                Menu menu = menuService.findById(id);
                return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }


    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('MENU_ADD')")
    public Result add(@RequestBody Menu menu){

        try {
            if (checkMenu(menu)){
                menu.setId(null);
                menuService.add(menu);
                return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_MENU_FAIL);
    }



    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('MENU_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)) {
                // 分页数据校验成功
                PageResult pageResult = menuService.findPage(queryPageBean);

                return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, pageResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }


    // 要求数据形式
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
     * 获取当前登录用户的菜单信息
     * 默认在配置文件中配置了要认证通过才能进行访问, 所以不用进行处理
     */
    @RequestMapping(value = "/getMenu",method = RequestMethod.GET)
    public Result getMenu()
    {
        try {
            // 首先知道用户的角色  通过用户名进行获取
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = user.getUsername();

            // 通过用户名查询出其对应的角色,之后可以通过角色信息查询出菜单信息
            List<Menu> list_menu = menuService.findMenuByUsername(username);

            List<Map> data = covertMenusToList(list_menu);

            return new Result(true, MessageConstant.GET_MENU_SUCCESS,data);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_MENU_FAIL);
    }


    /**
     * 将查询到的列表菜单数据转换为 List数据
     * @param list_menu 菜单数据
     * @return List数据
     */
    private static List<Map> covertMenusToList( List<Menu> list_menu)
    {
        // 将获取到的数据处理为结果的类型
        List<Map> data = new ArrayList<>();
        // 添加第一个节点
        Map<String, Object> one_menu = null;
        List<Map> children_list = null;
        Map<String, Object> menu_child = null;
        for (Menu menu : list_menu) {

            one_menu = new HashMap<>();
            one_menu.put("path", menu.getPath());
            one_menu.put("title", menu.getName());
            one_menu.put("icon", menu.getIcon());
            List<Menu> children = menu.getChildren();

            if (children != null && children.size() > 0) {
                children_list = new ArrayList<>();
                for (Menu child : children) {
                    menu_child = new HashMap<>();
                    menu_child.put("path", child.getPath());
                    menu_child.put("title", child.getName());
                    menu_child.put("icon", child.getIcon());
                    menu_child.put("linkUrl", child.getLinkUrl());
                    menu_child.put("children", new ArrayList<String>(0));
                    children_list.add(menu_child);
                }
                one_menu.put("children", children_list);

            } else {
                one_menu.put("children", new ArrayList<String>(0));
            }
            data.add(one_menu);
        }

        return data;
    }


    /**
     * 校验菜单
     * @param menu 菜单
     * @return
     */
    private static boolean checkMenu(Menu menu){
        if (menu != null){
            String name = menu.getName();
            String path = menu.getPath();
            Integer level = menu.getLevel();
            Integer priority = menu.getPriority();
            // 在前端进行校验后在后端再次校验  判断是否满足条件
            if(priority!=null&&(level==1 || level==2) && !StringUtils.isEmpty(path)
                    && !StringUtils.isEmpty(name)){
                return true;
            }
        }
        return false;
    }
}
