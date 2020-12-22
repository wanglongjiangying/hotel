package com.wanglong.dao;

import com.wanglong.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}
