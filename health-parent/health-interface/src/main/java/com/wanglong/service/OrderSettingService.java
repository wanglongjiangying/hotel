package com.wanglong.service;


import com.wanglong.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings) throws Exception;

    List<Map> getOrderSettingByMonth(String date) throws Exception;

    void editNumberByDate(OrderSetting orderSetting);
}
