package com.wanglong.dao;

import com.wanglong.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    public void add(CheckItem checkItem);

    List<CheckItem> findByPage(String queryString);

    Integer findCountByCheckItemId(Integer id);

    void deleteById(Integer id);

    void updateCheckItem(CheckItem checkItem);

    List<CheckItem> findAll();
    List<CheckItem> findCheckItemByCheckGroupId(Integer id);
}
