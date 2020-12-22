package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.myException.CheckItemException;
import com.wanglong.pojo.CheckItem;
import com.wanglong.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService  checkItemService;

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL,null);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,null);
    }


    @PostMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try {
          PageResult pageResult=checkItemService.findByPage(queryPageBean);

            System.out.println(pageResult);
          return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/deleteById")
   /* @PreAuthorize("hasAuthority('CHECKITEMDELETE')")*/

    @PreAuthorize("hasAuthority('CHECK')")
    public Result deleteById(Integer id){
        try {
            checkItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof CheckItemException){
                return new Result(false, "必须先删除检查项才能删除检查组！",null);
            }
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL,null);
        }

    }

    @PostMapping("/updateCheckItem")
    @PreAuthorize("hasAuthority('CHECKITEMEDIT')")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){
        try {
            checkItemService.updateCheckItem(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,null);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL,null);
        }
    }

    @GetMapping("/findAll")
    public Result findAll(){
        try {
          List<CheckItem> list=checkItemService.findAll();
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL,null);
        }
    }

}
