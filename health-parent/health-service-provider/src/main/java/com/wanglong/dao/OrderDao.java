package com.wanglong.dao;

import com.wanglong.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findOrderByCondition(Order order);

    void add(Order order);

    Map findById(Integer orderId);


    Integer todayOrderNumber(String today);

    Integer todayVisitsNumber(String today);

    Integer thisWeekOrderNumber(String zhouyi);

    Integer thisWeekVisitsNumber(String zhouyi);

    Integer thisMonthOrderNumber(String yueFirstDay);

    Integer thisMonthVisitsNumber(String yueFirstDay);
}
