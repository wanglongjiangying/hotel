package com.wanglong.dao;

import com.wanglong.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    Long findCountByDate(String orderDate);

    void update(@Param("orderDate") String orderDate,@Param("number") Integer number);

    void add(@Param("format") String format,@Param("number") Integer number,@Param("reservations") Integer reservations);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    OrderSetting findOrderByDate(String orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
