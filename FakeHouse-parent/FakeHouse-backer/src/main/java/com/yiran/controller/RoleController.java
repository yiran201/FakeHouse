package com.yiran.controller;


import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Role;
import com.yiran.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean)
    {
        try {
            // 校验查询条件
            if (QueryPageBean.checkQueryPageBean(queryPageBean))
            {
                PageResult roles = roleService.findPage(queryPageBean);

                return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,roles);
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }


    /**
     * 添加角色
     * @param role 角色信息
     * @param permissionIds 角色关联的权限
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADD')")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds)
    {

        try {
            // 校验
            if (checkRole(role))
            {
                role.setId(null);
                roleService.add(role,permissionIds, menuIds);
                return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.ADD_ROLE_FAIL);
    }


    /**
     * 通过id查询
     * @return Role
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result findById(Integer id)
    {
        try {
            if (id != null && id > 0)
            {
                Role role = roleService.findById(id);
                return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_ROLE_FAIL);
    }


    /**
     * 查询权限id集合
     * @param id: 角色id
     * @return 权限id集合
     */
    @GetMapping("/getPermissionIds")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result getPermissionIds(Integer id) {

        try {
            if (id != null && id > 0)
            {
                List<Integer> list = roleService.findPermissionIds(id);
                return new Result(true, MessageConstant.FIND_PERMISSION_ID_SUCCESS,list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.FIND_PERMISSION_ID_FAIL);
    }

    /**
     * 查询角色关联的菜单的id
     * @param id 角色id
     * @return 菜单集合
     */
    @GetMapping("/getMenuIds")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result getMenuIds(Integer id){

        try {
            if (id != null && id > 0){
                List<Integer> list = roleService.findMenuIds(id);
                return new Result(true, MessageConstant.FIND_MENU_ID_SUCCESS,list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.FIND_MENU_ID_FAIL);
    }

    /**
     * 查询角色关联的权限id和菜单id
     * @param id 角色id
     * @return
     */
    @GetMapping("/getLinkIds")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result getLinkIds(Integer id){

        try {
            if (id != null && id > 0){
                List<Integer> permissionIds = roleService.findPermissionIds(id);
                List<Integer> menuIds = roleService.findMenuIds(id);
                Map<String, List> result = new HashMap<>(2);
                result.put("permissionIds", permissionIds);
                result.put("menuIds", menuIds);
                return new Result(true, MessageConstant.FIND_LINK_ID_SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.FIND_LINK_ID_FAIL);
    }


    /**
     * 修改角色权限及详情信息
     * @param role 角色信息
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public Result edit(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){

        try {
            // 校验
            if (checkRole(role)&& role.getId()!=null){
                roleService.edit(role,permissionIds, menuIds);
                return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
    }


    /**
     * 删除角色
     * @param id 角色id
     * @return status
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public Result delete(Integer id) {

        try {
            if (id!=null && id >0)
            {
                roleService.delete(id);
                return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
    }


    /**
     * 查询所有角色信息
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ROLE_SELECT')")
    public Result findAll()
    {
        try {
            List<Role> roles = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_ROLE_FAIL);
    }


    /**
     * 校验角色基础信息是否完整
     * @param role 角色
     * @return  状态
     */
    private static boolean checkRole(Role role) {

        if (role != null)
        {
            if(!StringUtils.isEmpty(role.getName()) && !StringUtils.isEmpty(role.getKeyword())) {
                return true;
            }
        }
        return false;
    }



}
