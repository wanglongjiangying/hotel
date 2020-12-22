package com.wanglong.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.CheckItemDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.myException.CheckItemException;
import com.wanglong.pojo.CheckItem;
import com.wanglong.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
       checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<CheckItem> checkItems=  checkItemDao.findByPage(queryPageBean.getQueryString());
        PageInfo pageInfo=new PageInfo(checkItems);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void deleteById(Integer id) throws CheckItemException {
        //先检查这条数据有没有主外键约束
       Integer count=checkItemDao.findCountByCheckItemId(id);
       if(count>0){
           throw new CheckItemException();
       }

       checkItemDao.deleteById(id);

    }

    @Override
    public void updateCheckItem(CheckItem checkItem) throws Exception {
        checkItemDao.updateCheckItem(checkItem);
    }

    @Override
    public List<CheckItem> findAll()throws Exception {
        return checkItemDao.findAll();
    }
}
