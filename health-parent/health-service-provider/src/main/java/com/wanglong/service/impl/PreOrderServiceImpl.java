package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.PreOrderDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.PreOrder;
import com.wanglong.service.PreOrderService;
import com.wanglong.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wanglong
 * @date 2020/12/20 22:14
 * @desc
 */
@Service
public class PreOrderServiceImpl implements PreOrderService {

    @Autowired
    private PreOrderDao preOrderDao;

    @Override
    public PageResult findPreOrderByPage(QueryPageBean queryPageBean) throws Exception {
        Date start = new Date();
        Date end = new Date();
        List<String> timeRange = queryPageBean.getTimeRange();
        if(timeRange != null && timeRange.size()==2){
            String startTime = timeRange.get(0);
            String endTime = timeRange.get(1);
            start = DateUtils.parseString2Date(startTime);
            end = DateUtils.parseString2Date(endTime);
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Map<String,Object> params = new HashMap<>();
        params.put("startTime",start);
        params.put("endTime",end);
        params.put("status",queryPageBean.getStatus());
        params.put("queryString",queryPageBean.getQueryString());
        List<PreOrder> preOrderList = preOrderDao.findPreOrderByPage(params);
        PageInfo pageInfo=new PageInfo(preOrderList);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
