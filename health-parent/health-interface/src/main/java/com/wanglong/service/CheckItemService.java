package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.myException.CheckItemException;
import com.wanglong.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);

    PageResult findByPage(QueryPageBean queryPageBean);

    void deleteById(Integer id) throws CheckItemException;

    void updateCheckItem(CheckItem checkItem) throws Exception;

    List<CheckItem> findAll() throws Exception;

}
