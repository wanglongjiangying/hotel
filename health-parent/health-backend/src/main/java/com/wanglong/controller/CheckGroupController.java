package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.CheckGroup;
import com.wanglong.service.CheckGroupService;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] itemIds){
        try {
            checkGroupService.add(checkGroup,itemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL,null);
        }
    }

    @PostMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult= checkGroupService.findByPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer groupId){


        try {
           List<Integer> ids= checkGroupService.findCheckItemIdsByCheckGroupId(groupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL,null);
        }
    }

    @GetMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            checkGroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL,null);
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] itemIds){
        try {
            checkGroupService.edit(checkGroup,itemIds);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL,null);
        }
    }
    @GetMapping("/findAll")
    public Result edfindAll(){
        try {
            List<CheckGroup> groups=checkGroupService.findAll();
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS,groups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL,null);
        }
    }
}
