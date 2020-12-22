package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Floor;
import com.wanglong.pojo.Room;
import com.wanglong.pojo.RoomType;
import com.wanglong.service.RoomService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @author wanglong
 * @date 2020/12/13 14:36
 * @desc
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Reference
    private RoomService roomService;

    /**
     * 分页查询房间信息
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult byPage = roomService.findByPage(queryPageBean);
            return byPage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加房间
     * @param room
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Room room,Integer[] floorId,Long[] typeId){
        try {
            if(floorId.length>1){
                return new Result(false, MessageConstant.ONLY_SELECTONE_FLOOR,null);
            }

            if(typeId.length>1){
                return new Result(false, MessageConstant.ONLY_SELECTONE_ROOMTYPE,null);
            }
            Floor floor = new Floor();
            floor.setId(floorId[0]);
            RoomType roomType = new RoomType();
            roomType.setId(typeId[0]);
            room.setFloor(floor);
            room.setRoomType(roomType);
            roomService.add(room);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROOM_FAIL,null);
        }
        return new Result(true, MessageConstant.ADD_ROOM_SUCCESS,null);
    }

    /**
     * 根据Id删除房间并且删除照片，
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public Result deleteById(String photo,Integer id){
        try {
            //TODO 这里应该加一个事务，删除照片
            roomService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ROOM_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROOM_FAIL,null);
        }
    }

    /**
     * 更新房间的基本信息
     * @param room
     * @return
     */
    @PostMapping("/updateRoom")
    public Result updateCheckItem(@RequestBody Room room,Integer[] floorId,Long[] typeId){
        try {
            if(floorId.length>1){
                return new Result(false, MessageConstant.ONLY_SELECTONE_FLOOR,null);
            }

            if(typeId.length>1){
                return new Result(false, MessageConstant.ONLY_SELECTONE_ROOMTYPE,null);
            }

            Floor floor = new Floor();
            floor.setId(floorId[0]);
            RoomType roomType = new RoomType();
            roomType.setId(typeId[0]);
            room.setFloor(floor);
            room.setRoomType(roomType);
            roomService.updateRoom(room);
            return new Result(true,MessageConstant.EDIT_ROOM_SUCCESS,null);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_ROOM_FAIL,null);
        }
    }

    /**
     * 上传房间图片
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result uploadImage(HttpServletRequest request, @RequestParam("imgFile") MultipartFile imgFile){
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/uploads");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdir();
            }
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String houzui = originalFilename.substring(lastIndexOf - 1);
            //生成uuid
            String name = UUID.randomUUID().toString();
            String fileName=name+houzui;
            imgFile.transferTo(new File(file,fileName));
            return new Result(true,MessageConstant.EDIT_FLOOR_SUCCESS,fileName);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_FLOOR_FAIL,null);
        }
    }


}
