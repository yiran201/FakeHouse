package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.PermissionDao;
import com.yiran.dao.RoleDao;
import com.yiran.dao.UserDao;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Permission;
import com.yiran.pojo.Role;
import com.yiran.pojo.User;
import com.yiran.pojo.UserExample;
import com.yiran.service.MenuService;
import com.yiran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = MenuService.class)
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 通过用户名查询用户信息以及关联的角色权限信息,并查角色关联的权限信息
     * @return
     */
    @Override
    public User findUserByUserName(String username) {

        // 可以使用复合sql查询,也可以使用 分布查询
        // 其实在一般场合下,应该是符合sql进行查询比较好(速度会比较快)
        // 但是如果数据库进行了分库处理的话,就只能使用分布查询
        // 分布查询与mybatis的sql原理是一样的    与直接写sql相比较慢
        User user = userDao.findUserByUserName(username);
        if (user == null)
        {
            return null;
        }

        // 查询角色信息
        Set<Role> roles = roleDao.findByUserId(user.getId());

        for (Role role : roles) {
            // 给角色封装权限信息
            Integer id = role.getId();
            // 通过角色id查询角色权限
            Set<Permission> permissions = permissionDao.findByRoleId(id);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);

        return user;
    }



    /**
     * 查询用户分页数据
     * @return data
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<User> users = null;

        // 通过用户名查询用户列表
        // 如果查询字符串为空,则查询所有;否则按照查询字符串进行模糊查询
        String queryString = queryPageBean.getQueryString();
        if (queryString==null || queryString.equals("")) {

            users = userDao.findPageWithoutCondition();

        }else{
            users = userDao.findPage("%"+queryString+"%");
        }

        PageResult result = new PageResult(users.getTotal(), users.getResult());

        User user = null;
        // 对用户数据进行处理,防止密码泄露
        for (Object row : result.getRows()) {
            user = (User) row;
            user.setPassword(null);
        }

        return result;
    }



    /**
     * 添加角色数据到数据库
     * @param user 用户数据
     * @param roleId 关联的角色id
     */
    @Override
    public void add(User user, Integer[] roleId) throws IOException {


        // 由于有默认的密码, 所以用户没有填写密码信息也可以进行注册
        // 默认密码为  user-123456

        // 密码要进行加密
        String password = user.getPassword();
        if (!StringUtils.isEmpty(password)){
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }

        // 要求返回id
        userDao.insert(user);

        // 添加角色
        Map<String,Object> map = null;
        if (roleId != null && roleId.length > 0) {
            for (Integer id : roleId) {
                map = new HashMap<>(2);
                map.put("userId", user.getId());
                map.put("roleId", id);
                userDao.connectWithRole(map);
            }
        }
    }


    /**
     * 通过id查询用户信息
     * @param id 用户id
     * @return
     */
    @Override
    public User findById(Integer id) {

        return userDao.selectByPrimaryKey(id);
    }


    /**
     * 通过用户id查询角色id
     * @param id 用户id
     * @return
     */
    @Override
    public List<Integer> findRoleId(Integer id) {
        return userDao.findRoleId(id);
    }


    /**
     * 查询用户名重复的次数 保证用户名只能出现一次
     * @param username 用户名
     * @return 出现次数
     */
    @Override
    public int countByUsername(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        return userDao.countByExample(userExample);
    }


    /**
     * 删除用户
     * @param user 用户信息
     * @param roleId 关联的角色id
     */
    @Override
    public void updateUser(User user, Integer[]roleId) {
        // 避免修改用户名
        user.setUsername(null);
        userDao.updateByPrimaryKeySelective(user);

        // 修改表关联关系
        userDao.disconnectWithRole(user.getId());
        if (roleId != null && roleId.length > 0){
            for (Integer id : roleId) {
                Map<String,Object> map = new HashMap<>(2);
                map.put("userId", user.getId());
                map.put("roleId",id);
                userDao.connectWithRole(map);
            }
        }

    }

    /**
     * 通过用户id删除用户   危险操作
     * @param id 用户id
     */
    @Override
    public void delete(Integer id) {
        // 删除关联关系
        userDao.disconnectWithRole(id);
        userDao.deleteByPrimaryKey(id);
    }


    /**
     * 用户注册, 需要先让系统管理员授权并修改station之后用户才能进行登录
     * @param user 用户信息
     */
    @Override
    public void register(User user) {
        user.setStation(false);
        userDao.insertSelective(user);
    }


}
