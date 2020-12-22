package com.wanglong.service;

import com.wanglong.entity.Result;
import com.wanglong.pojo.Member;
import com.wanglong.pojo.Order;

import java.util.Map;

public interface OrderService {
    Result submit(Map map) throws Exception;

    Map findById(Integer orderId) throws Exception;

}

