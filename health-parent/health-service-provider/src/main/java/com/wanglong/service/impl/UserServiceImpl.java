package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanglong.dao.PermissionDao;
import com.wanglong.dao.RoleDao;
import com.wanglong.dao.UserDao;
import com.wanglong.pojo.Permission;
import com.wanglong.pojo.Role;
import com.wanglong.pojo.User;
import com.wanglong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;
    @Override
    public User findUserByUsername(String username) {

       User user=userDao.findUserByUsername(username);

        Integer userId = user.getId();

       Set<Role> roles=roleDao.findRolesByUserId(userId);

        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissionSet=permissionDao.findPermissionsByRoleId(roleId);
            role.setPermissions(permissionSet);
        }

        user.setRoles(roles);
        return user;
    }
}
