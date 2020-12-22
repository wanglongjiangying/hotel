package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.Result;
import com.wanglong.pojo.OrderSetting;
import com.wanglong.service.OrderSettingService;
import com.wanglong.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        if(excelFile!=null){
            try {
                List<String[]> strings = POIUtils.readExcel(excelFile);
                //把String数组转换为List<orderSetting>
                List<OrderSetting> orderSettings=new ArrayList<>();
                for (String[] string : strings) {
                    OrderSetting orderSetting=new OrderSetting();
                    //
                    String dateString = string[0];
                    String number = string[1];
                    orderSetting.setOrderDate(new Date(dateString));
                    orderSetting.setNumber(Integer.parseInt(number));
                     orderSettings.add(orderSetting);
                }
                orderSettingService.add(orderSettings);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS,null);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL,null);
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("date")String date){

        try {
            List<Map> maps= orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_ORDERSETTING_FAIL,null);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ORDERSETTING_FAIL,null);
        }
    }

}
