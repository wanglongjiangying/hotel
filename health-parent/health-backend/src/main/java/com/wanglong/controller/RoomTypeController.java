package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Floor;
import com.wanglong.pojo.RoomType;
import com.wanglong.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roomType")
public class RoomTypeController {

    @Reference
    RoomTypeService roomTypeService;


    @PostMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult byPage = roomTypeService.findByPage(queryPageBean);
            return byPage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody RoomType roomType){
        try {
            roomType.setAvilableNum(roomType.getRoomNum());
            roomTypeService.add(roomType);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_FLOOR_FAIL,null);
        }
        return new Result(true, MessageConstant.ADD_FLOOR_SUCCESS,null);
    }

    @GetMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            roomTypeService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_FLOOR_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_FLOOR_FAIL,null);
        }
    }

    @PostMapping("/updateRoomType")
    public Result updateCheckItem(@RequestBody RoomType roomType){
        try {
            //可预约数量等于：总数-预定数
            roomType.setAvilableNum(roomType.getRoomNum()-roomType.getBookNum());
            roomTypeService.updateRoomType(roomType);
            return new Result(true,MessageConstant.EDIT_FLOOR_SUCCESS,null);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_FLOOR_FAIL,null);
        }
    }

    @GetMapping("/findAll")
    public Result findAll(){
        try {
            //可预约数量等于：总数-预定数
            List<RoomType> roomTypes = roomTypeService.finaAll();
            return new Result(true,MessageConstant.EDIT_FLOOR_SUCCESS,roomTypes);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_FLOOR_FAIL,null);
        }
    }

}
