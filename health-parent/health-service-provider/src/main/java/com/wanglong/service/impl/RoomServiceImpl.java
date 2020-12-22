package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.RoomDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Floor;
import com.wanglong.pojo.Room;
import com.wanglong.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wanglong
 * @date 2020/12/13 14:38
 * @desc
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;


    @Override
    public void deleteById(Integer id) {
        roomDao.deleteById(id);
    }

    @Override
    public void add(Room room) {
        roomDao.add(room);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<Room> byPage = roomDao.findByPage(queryPageBean.getQueryString());
        PageInfo pageInfo=new PageInfo(byPage);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }


    @Override
    public void updateRoom(Room room) {
        roomDao.updateRoom(room);
    }
}
