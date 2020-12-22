package com.wanglong.dao;

import com.wanglong.pojo.Floor;

import java.util.List;

public interface FloorDao {

    List<Floor> findByPage(String value);

    void add(Floor floor);

    void deleteById(Integer id);

    void updateFloor(Floor floor);

    Floor id(Integer floor_id);

    List<Floor> findAll();
}
