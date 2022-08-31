package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Permission;
import com.yiran.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;


    /**
     * 分页查询
     * @param pageBean 查询条件封装
     * @return Result
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('PERMISSION_SELECT')")
    public Result findPage(@RequestBody QueryPageBean pageBean)
    {
        PageResult data = null;
        try {
            data = permissionService.findPage(pageBean);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }


    /**
     * 添加权限
     * @param permission 权限
     * @return Result
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PERMISSION_ADD')")
    public Result add(@RequestBody Permission permission)
    {
        try {
            // 对数据进行校验,如果数据包含违规信息,不给予执行
            if (checkPermission(permission)) {
                // 数据校验通过
                permission.setId(null);
                permissionService.add(permission);
                return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
    }

    /**
     * 删除权限数据
     * @return 状态信息
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public Result delete(Integer id)
    {
        try {
            if (id != null){
                permissionService.delete(id);
                return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
    }



    /**
     * 通过id查询
     * @param id 权限id
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('PERMISSION_SELECT')")
    public Result findById(Integer id)
    {
        try {
            if (id != null){
                Permission permission = permissionService.findById(id);
                return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    /**
     * 修改权限内容
     * @param permission 权限
     * @return 状态
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public Result edit(@RequestBody Permission permission)
    {
        try {
            // 校验信息
            if (checkPermission(permission)) {
                permissionService.update(permission);
                return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
    }


    /**
     * 判断添加的权限数据是否合格
     * @param permission 权限信息
     * @return true:符合 false:不符合
     */
    private static boolean checkPermission(Permission permission) {

        if (permission != null)
        {
            String keyword = permission.getKeyword();
            String name = permission.getName();
            if (!StringUtils.isEmpty(keyword) && !StringUtils.isEmpty(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 查询所有
     * @return 所有的权限数据
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('PERMISSION_SELECT')")
    public Result findAll()
    {
        try {
            List<Permission> permissions = permissionService.findAll();
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_PERMISSION_FAIL);
    }







}
