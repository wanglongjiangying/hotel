package com.wanglong.dao;

import com.wanglong.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
