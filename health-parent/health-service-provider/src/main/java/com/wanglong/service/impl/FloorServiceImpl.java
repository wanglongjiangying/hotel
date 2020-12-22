package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.FloorDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Floor;
import com.wanglong.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = FloorService.class)
@Transactional
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<Floor> byPage = floorDao.findByPage(queryPageBean.getQueryString());
        PageInfo pageInfo=new PageInfo(byPage);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(Floor floor) {
        floorDao.add(floor);
    }

    @Override
    public void deleteById(Integer id) {
        floorDao.deleteById(id);
    }

    @Override
    public void updateFloor(Floor floor) {
        floorDao.updateFloor(floor);
    }

    @Override
    public List<Floor> findAll() {
       return floorDao.findAll();
    }
}
