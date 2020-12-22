package com.wanglong.dao;

import com.wanglong.pojo.Floor;
import com.wanglong.pojo.RoomType;

import java.util.List;

/**
 * @author wanglong
 * @date 2020/12/13 10:09
 * @desc
 */
public interface RoomTypeDao {
    List<RoomType> findByPage(String queryString);

    void add(RoomType roomType);

    void deleteById(Integer id);

    void updateRoomType(RoomType roomType);

    RoomType findById(Integer roomType_id);

    List<RoomType> findAll();
}
