package com.wanglong.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.pojo.Permission;
import com.wanglong.pojo.Role;
import com.wanglong.pojo.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sun.security.util.Password;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户查询用户
       User user=userService.findUserByUsername(username);

       if(user==null){
        return null;
       }

        //封装用户具有的role和authority
        List<SimpleGrantedAuthority> roleAndAuthority=new ArrayList<>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            roleAndAuthority.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                roleAndAuthority.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User userDetail=
                new org.springframework.security.core.userdetails.User(username, user.getPassword(),roleAndAuthority);

        return userDetail;
    }
}
