package com.wanglong.dao;

import com.github.pagehelper.Page;
import com.wanglong.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndGroup(Map<String, Integer> map);

    Page findByPage(String queryString);

    void deleteByImg(String img);

    void deleteSetmealAndGroup(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    void edit(Setmeal setmeal);

    List<Setmeal> findAll();

    Setmeal findbyId(Integer id);

    List<Map<String, Object>> findSetmealCount();

    List<Map<String, Object>> hotSetmeal();
}

