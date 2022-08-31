package com.yiran.controller;

import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.User;
import com.yiran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * 获取当前登录用户的用户名
     * @return
     */
    @RequestMapping(value = "/getUsername",method = RequestMethod.GET)
    public Result getUsername() {

        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }



    /**
     * 查询用户分页数据
     * @return data
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('USER_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)){
                PageResult page = userService.findPage(queryPageBean);
                return new Result(true, MessageConstant.QUERY_USER_SUCCESS,page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }


    /**
     * 添加用户
     * @param roleId 关联的角色id
     * @param user 用户信息
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public Result add(Integer[] roleId, @RequestBody User user) {
        try {
            if (checkUser(user)) {
                // 首先需要判断是否存在用户名重复的角色
                String username = user.getUsername();
                int count = userService.countByUsername(username);
                if (count > 0)
                {
                    return new Result(false, MessageConstant.USERNAME_ERROR);
                }
                user.setId(null);
                userService.add(user, roleId);

                return new Result(true, MessageConstant.ADD_USER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_USER_FAIL);
    }


    /**
     * 通过id查询用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('USER_SELECT')")
    public Result findById(Integer id) {
        try {
            if (id != null && id>0)
            {
                User user = userService.findById(id);

                return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }


    /**
     * 通过用户id查询角色id
     * @param id 用户id
     * @return
     */
    @GetMapping("/getRoleId")
    @PreAuthorize("hasAuthority('USER_SELECT')")
    public Result getRoleId(Integer id) {
        try {
            if (id != null)
            {
                List<Integer> roleId = userService.findRoleId(id);
                return new Result(true, MessageConstant.QUERY_USER_SUCCESS, roleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }


    /**
     * 修改用户信息/关联关系
     * @param roleId 角色id  用户不一定要关联角色
     * @param user 用户信息
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public Result edit(Integer[] roleId, @RequestBody User user) {
        try {
            // 允许roleId为空, 因为用户可以不进行角色的关联
            if (checkUser(user))
            {
                userService.updateUser(user,roleId);
            }
            return new Result(true, MessageConstant.UPDATE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_USER_FAIL);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public Result delete(Integer id) {

        try {
            if (id != null && id > 0)
            {
                userService.delete(id);
                return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_USER_FAIL);
    }


    /**
     * 校验用户信息是否完整
     * @param user 用户
     * @return true 校验成功
     */
    private static boolean checkUser(User user) {

        String username = user.getUsername();
        String telephone = user.getTelephone();
        if (!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(telephone)){
            return true;
        }
        return false;
    }



}
