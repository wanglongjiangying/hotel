package com.wanglong.controller;

import com.wanglong.constant.MessageConstant;
import com.wanglong.constant.RedisMessageConstant;
import com.wanglong.entity.Result;
import com.wanglong.utils.SMSUtils;
import com.wanglong.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;



    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            //发送短信验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,Integer.toString(validateCode));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL,null);
        }
        //将验证码存储到redis中
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,
                600,validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,null);
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            //发送短信验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,Integer.toString(validateCode));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL,null);
        }
        //将验证码存储到redis中
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,
                600,validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,null);
    }



}
