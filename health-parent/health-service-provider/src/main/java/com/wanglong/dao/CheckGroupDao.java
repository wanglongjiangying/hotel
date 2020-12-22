package com.wanglong.dao;

import com.github.pagehelper.Page;
import com.wanglong.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void addCheckGroup(CheckGroup checkGroup);

    void addZhongJianBiao(Map<String,Integer> map);

    Page findByPage(String queryString);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer groupId);

    void deleteById(Integer id);

    void deleteZhongJianBiao(Integer groupId);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupBySetmealId(Integer id);
}
