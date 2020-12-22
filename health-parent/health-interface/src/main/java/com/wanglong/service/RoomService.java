package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Room;

/**
 * @author wanglong
 * @date 2020/12/13 14:38
 * @desc
 */
public interface RoomService {
    void deleteById(Integer id);

    /**
     * 添加房间
     * @param room
     */
    void add(Room room);


    /**
     * 分页查询房间信息
     * @param queryPageBean
     * @return
     */
    PageResult findByPage(QueryPageBean queryPageBean);


    /**
     * 根据id修改房间的信息，包括房间的图片信息
     * @param room
     */
    void updateRoom(Room room);
}
