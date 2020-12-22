package com.wanglong.dao;

import com.wanglong.pojo.PreOrder;

import java.util.List;
import java.util.Map;

/**
 * @author wanglong
 * @date 2020/12/20 22:14
 * @desc
 */
public interface PreOrderDao {

    List<PreOrder> findPreOrderByPage(Map params);

}
