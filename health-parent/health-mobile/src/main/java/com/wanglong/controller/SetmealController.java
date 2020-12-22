package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Setmeal;
import com.wanglong.service.SetmealService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @PostMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
           List<Setmeal> setmealList= setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL,null);

        }
    }

    @PostMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal= setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL,null);

        }
    }
}
