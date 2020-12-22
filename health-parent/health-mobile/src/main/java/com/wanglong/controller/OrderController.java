package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wanglong.constant.MessageConstant;
import com.wanglong.constant.RedisMessageConstant;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Member;
import com.wanglong.pojo.Order;
import com.wanglong.service.MemberService;
import com.wanglong.service.OrderService;
import org.apache.poi.ss.formula.ptg.MemErrPtg;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @Reference
    private MemberService memberService;




    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer orderId){

        try {
            Map order = orderService.findById(orderId);
           return new Result(true,"",order);
        }catch (Exception e){
            return  new Result(false,"");
        }



    }

    @PostMapping("/submit")
    public Result submit(@RequestBody Map map){

        try {
            String validateCode = (String)map.get("validateCode");
            String telephone =(String) map.get("telephone");
            String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            if(validateCode!=null && redisValidateCode!=null && redisValidateCode.equals(validateCode)){
                //设置预约类型
                map.put("orderType", Order.ORDERTYPE_WEIXIN);

                Result result= orderService.submit(map);
              /*
               if(result.isFlag()){
                   //预约成功发送短信通知用户
               }
               */
                return result;
            }else{
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL, null);
        }


    }

    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map){

        try {
            String validateCode = (String)map.get("validateCode");
            String telephone =(String) map.get("telephone");
            String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
            if(validateCode!=null && redisValidateCode!=null && redisValidateCode.equals(validateCode)){

                //判断用户是否是会员
              Member member=  memberService.findMemberByTelephone(telephone);
              if(member==null){
                  //用户还不是会员，自动把用户注册为会员
                  member=new Member();
                  member.setPhoneNumber(telephone);
                  member.setRegTime(new Date());
                  memberService.add(member);
              }

              //将用户的手机号信息存储到cookie中
                Cookie cookie=new Cookie("user_telephone_cookie",telephone);
                cookie.setPath("/"); //设置cookie路径
                cookie.setMaxAge(60*60*24*3);//最小单位为分钟
                response.addCookie(cookie);

                //将用户信息保存到redis中。有效期为30分钟
                   //将用户信息先转化为JSON
                String memberJson =  JSON.toJSON(member).toString();
                jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,60*30,memberJson);



              return new Result(true,MessageConstant.LOGIN_SUCCESS);



            }else{
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL, null);
        }


    }
}
