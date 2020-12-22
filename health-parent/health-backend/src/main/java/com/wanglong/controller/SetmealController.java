package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.constant.RedisConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Setmeal;
import com.wanglong.service.SetmealService;
import com.wanglong.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传方法
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String houzui = originalFilename.substring(lastIndexOf - 1);
        //生成uuid
        String name = UUID.randomUUID().toString();
        String fileName=name+houzui;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
             jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL,null);
        }
    }

    /**
     * 图片上传方法
     * @param setmeal
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] groupIds){
        try {

            setmealService.add(setmeal,groupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL,null);
        }
    }

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try {

           PageResult pageResult= setmealService.findByPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/deleteByImg")
    public Result deleteByImg(@RequestParam("img") String img,@RequestParam("id")Integer id){

        try {
            setmealService.deleteByImg(img,id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL,null);
        }
    }

    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(@RequestParam("setmealId")Integer setmealId){
        try {
            List<Integer> ids = setmealService.findCheckGroupIdsBySetmealId(setmealId);
            return new Result(true,"查询复选框成功",ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询复选框失败",null);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestParam("checkgroupIds") Integer[] checkgroupIds,@RequestParam("img")String imgName
            ,@RequestBody Setmeal setmeal){

        try {
            setmealService.edit(checkgroupIds,imgName,setmeal);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL,null);
        }
    }
}
