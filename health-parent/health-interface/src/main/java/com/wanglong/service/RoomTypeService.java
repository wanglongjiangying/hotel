package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.RoomType;

import java.util.List;

public interface RoomTypeService {
    PageResult findByPage(QueryPageBean queryPageBean);

    void add(RoomType roomType);

    void deleteById(Integer id);

    void updateRoomType(RoomType roomType);

    List<RoomType> finaAll();
}
