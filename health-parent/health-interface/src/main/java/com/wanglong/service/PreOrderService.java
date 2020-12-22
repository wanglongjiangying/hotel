package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;

/**
 * @author wanglong
 * @date 2020/12/20 22:13
 * @desc
 */
public interface PreOrderService {
    PageResult findPreOrderByPage(QueryPageBean queryPageBean) throws Exception;
}
