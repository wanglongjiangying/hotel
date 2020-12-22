package com.wanglong.dao;

import com.wanglong.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);
}
