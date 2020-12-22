package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] groupIds) throws Exception;

    PageResult findByPage(QueryPageBean queryPageBean) throws Exception;

    void deleteByImg(String img,Integer id) throws Exception;

    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId) throws Exception;

    void edit(Integer[] checkgroupIds, String imgName, Setmeal setmeal) throws Exception;

    List<Setmeal> findAll() throws Exception;

    Setmeal findById(Integer id) throws Exception;

    List<Map<String, Object>> findSetmealCount() throws Exception;
}
