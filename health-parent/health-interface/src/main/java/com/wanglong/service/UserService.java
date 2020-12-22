package com.wanglong.service;

import com.wanglong.pojo.User;

public interface UserService {
    User findUserByUsername(String username);
}
