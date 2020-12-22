package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] itemIds) throws Exception;

    PageResult findByPage(QueryPageBean queryPageBean) throws Exception;

     List<Integer> findCheckItemIdsByCheckGroupId(Integer groupId) throws Exception;

    void deleteById(Integer id) throws Exception;

    void edit(CheckGroup checkGroup, Integer[] itemIds) throws Exception;

    List<CheckGroup> findAll() throws Exception;

}
