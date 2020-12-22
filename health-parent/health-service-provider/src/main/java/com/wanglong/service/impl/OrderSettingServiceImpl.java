package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanglong.dao.OrderSettingDao;
import com.wanglong.pojo.OrderSetting;
import com.wanglong.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量插入
     * @param orderSettings
     */
    @Override
    public void add(List<OrderSetting> orderSettings)throws Exception {

        for (OrderSetting orderSetting : orderSettings) {
            //根据日期查询，先判断是否是今天第一次插入

            System.out.println();
            String format = new SimpleDateFormat("yyyy-MM-dd").format(orderSetting.getOrderDate());

            System.out.println(format);

            Long count= orderSettingDao.findCountByDate(format);
           if(count>0){
               //多次就是更新
               orderSettingDao.update(format,orderSetting.getNumber());
           }else{
               //如果是第一次，就是插入
               orderSettingDao.add(format,orderSetting.getNumber(),orderSetting.getReservations());
           }
        }

    }

    @Override
    public List<Map> getOrderSettingByMonth(String date)throws Exception {
        String begin=date+"-1";
        String end=date+"-31";
        Map<String,String> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);

        List<OrderSetting> orderSettingList=orderSettingDao.getOrderSettingByMonth(map);

        List<Map> list=new ArrayList<>();

        for (OrderSetting orderSetting : orderSettingList) {
            Map<String,Object> map1=new HashMap<>();
            map1.put("date",orderSetting.getOrderDate().getDate());
            map1.put("number",orderSetting.getNumber());
            map1.put("reservations",orderSetting.getReservations());
            list.add(map1);
        }

        return list;
    }


    /**
     * 设置预约人数
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //根据日期查询，先判断是否是今天第一次插入
        String format = new SimpleDateFormat("yyyy-MM-dd").format(orderSetting.getOrderDate());
        Long count= orderSettingDao.findCountByDate(format);
        if(count>0){
            //多次就是更新
            orderSettingDao.update(format,orderSetting.getNumber());
        }else{
            //如果是第一次，就是插入
            orderSettingDao.add(format,orderSetting.getNumber(),orderSetting.getReservations());
        }

    }


}
