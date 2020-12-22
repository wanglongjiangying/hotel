package com.wanglong.dao;

import com.wanglong.pojo.Room;

import java.util.List;

/**
 * @author wanglong
 * @date 2020/12/13 14:39
 * @desc
 */
public interface RoomDao {

    void deleteById(Integer id);


    /**
     * 添加房间
     * @param room
     */
    void add(Room room);



    /**
     * 根据id修改房间的信息，包括房间的图片信息
     * @param room
     */
    void updateRoom(Room room);



    /**
     * 分页查询房间信息
     * @param value
     * @return
     */
    List<Room> findByPage(String value);

}
