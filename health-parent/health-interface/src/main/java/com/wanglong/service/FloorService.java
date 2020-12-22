package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Floor;

import java.util.List;

public interface FloorService {
   public PageResult findByPage(QueryPageBean queryPageBean);

    void add(Floor floor);

    void deleteById(Integer id);

    void updateFloor(Floor floor);

    List<Floor> findAll();
}
