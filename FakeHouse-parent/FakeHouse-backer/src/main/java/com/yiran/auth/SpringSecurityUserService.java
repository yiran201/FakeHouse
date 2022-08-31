package com.yiran.auth;

import com.yiran.pojo.Permission;
import com.yiran.pojo.Role;
import com.yiran.pojo.User;
import com.yiran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private UserService userService;


    // 根据用户名查询数据库获取数据信息
    // 需要通过dubbo远程调用 服务提供者
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByUserName(username);

        if(user == null)
        {
            // 用户名不存在,返回null交由框架处理(403)
            return null;
        }

        // 动态获取用户的权限,用户等等(已经存储在User中了,即已经封装好了在查询sql的时候)
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles != null && roles.size() != 0)
        {
            for (Role role : roles) {
                // 授予角色
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                // 授予角色的每一条权限
                // 现要求权限全部都要封装为角色, 可能会出现权限的重复赋值,不过不重要(应该只是第二次赋值无效而已)
                // 会出现重复赋值的情况, 当两个角色拥有同样的权限的时候, 发生重复赋值
                // 对业务会不会造成影响暂时还不知道, 等待后期测知道
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null && permissions.size() != 0)
                {
                    for (Permission permission : permissions) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        // 封装SpringSecurity中的User
        org.springframework.security.core.userdetails.User securityUser =
                new org.springframework.security.core.userdetails.User(username, user.getPassword(),list);

        return securityUser;
    }


    // org.springframework.security.authentication.BadCredentialsException: Bad credentials
    // 报这个错误代表密码不匹配


}
