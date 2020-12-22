package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.RoomTypeDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Floor;
import com.wanglong.pojo.RoomType;
import com.wanglong.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wanglong
 * @date 2020/12/13 10:08
 * @desc
 */
@Service
public class RoomTypeServiceImpl implements RoomTypeService {


    @Autowired
    RoomTypeDao roomTypeDao;
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<RoomType> byPage = roomTypeDao.findByPage(queryPageBean.getQueryString());
        PageInfo pageInfo=new PageInfo(byPage);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(RoomType roomType) {
        roomTypeDao.add(roomType);
    }

    @Override
    public void deleteById(Integer id) {
        roomTypeDao.deleteById(id);
    }

    @Override
    public void updateRoomType(RoomType roomType) {
        roomTypeDao.updateRoomType(roomType);
    }

    @Override
    public List<RoomType> finaAll() {
       return roomTypeDao.findAll();
    }
}
