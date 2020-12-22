package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.CheckItem;
import com.wanglong.pojo.Floor;
import com.wanglong.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/floor")
@RestController
public class FloorController {
    @Reference
    private FloorService floorService;

    @PostMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult byPage = floorService.findByPage(queryPageBean);
            return byPage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Floor floor){
        try {
            floorService.add(floor);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_FLOOR_FAIL,null);
        }
        return new Result(true, MessageConstant.ADD_FLOOR_SUCCESS,null);
    }

    @GetMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            floorService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_FLOOR_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_FLOOR_FAIL,null);
        }
    }

    @PostMapping("/updateFloor")
    public Result updateCheckItem(@RequestBody Floor floor){
        try {
            floorService.updateFloor(floor);
            return new Result(true,MessageConstant.EDIT_FLOOR_SUCCESS,null);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_FLOOR_FAIL,null);
        }
    }

    @GetMapping("/findAll")
    public Result findAll(){
        try {
            List<Floor> all = floorService.findAll();
            return new Result(true,MessageConstant.EDIT_FLOOR_SUCCESS,all);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_FLOOR_FAIL,null);
        }
    }
}
