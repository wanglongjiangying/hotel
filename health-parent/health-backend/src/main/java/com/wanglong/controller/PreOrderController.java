package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.service.PreOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanglong
 * @date 2020/12/20 22:12
 * @desc
 */
@RequestMapping("/preOrder")
@RestController
public class PreOrderController {

    @Reference
    private PreOrderService preOrderService;


    @PostMapping("/findPreOrderByPage")
    public PageResult findPreOrderByPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult byPage = preOrderService.findPreOrderByPage(queryPageBean);
            return byPage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
