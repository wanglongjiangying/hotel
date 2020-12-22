package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanglong.constant.MessageConstant;
import com.wanglong.dao.MemberDao;
import com.wanglong.dao.OrderDao;
import com.wanglong.dao.OrderSettingDao;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Member;
import com.wanglong.pojo.Order;
import com.wanglong.pojo.OrderSetting;
import com.wanglong.service.OrderService;
import com.wanglong.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 提交预约
     * @param map
     * @return
     */
    @Override
    public Result submit(Map map) throws Exception {
        //先判断预约日期是否是可预约日期
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting= orderSettingDao.findOrderByDate(orderDate);
        if(orderSetting==null){
            //此日期不能进行预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断该日期的预约人数是否已满
        if(orderSetting.getReservations()>=orderSetting.getNumber()){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //根据手机号查询用户信息判断用户是否重复预约

        String telephone = (String) map.get("telephone");
         Member member=memberDao.findMemberByTelephone(telephone);

         if(member!=null){
             //根据用户Id,套餐Id,日期判断是否重复预约
             Integer memberId = member.getId();
             String setmealId = (String)map.get("setmealId");
             Order order=new Order();
             order.setMemberId(memberId);
             //order.setOrderDateStr(orderDate);
             order.setOrderDate(DateUtils.parseString2Date(orderDate));
             order.setSetmealId(Integer.parseInt(setmealId));
             List<Order> orders=orderDao.findOrderByCondition(order);
             if(orders!=null && orders.size()>0){
             return new Result(false,MessageConstant.HAS_ORDERED);
              }
         }else{
             //没有查到用户就将用户信息插入会员表注册会员
              member=new Member();
             member.setIdCard((String)map.get("idCard"));
             member.setRegTime(new Date());
             member.setPhoneNumber(telephone);
             member.setName((String)map.get("name"));
             member.setSex((String)map.get("sex"));
             memberDao.add(member);
         }
        //进行预约，修改预约人数，并且返回用户预约id
          Order order=new Order();
         order.setMemberId(member.getId());
         order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
         order.setOrderDate(DateUtils.parseString2Date(orderDate));
         order.setOrderType((String) map.get("orderType"));
         order.setOrderStatus(Order.ORDERSTATUS_NO);
         orderDao.add(order);

         //改变已经预约的人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);


        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    /**
     *
     * @param orderId
     * @return
     */
    @Override
    public Map findById(Integer orderId)  throws Exception{

       Map order= orderDao.findById(orderId);
        return order;
    }




}
