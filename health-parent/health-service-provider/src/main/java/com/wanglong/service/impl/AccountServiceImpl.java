package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglong.dao.AccountDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Account;
import com.wanglong.pojo.Floor;
import com.wanglong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanglong
 * @date 2020/12/20 15:24
 * @desc
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public PageResult findAccountByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Map<String,Object> params = new HashMap<>();
        params.put("queryString",queryPageBean.getQueryString());
        params.put("status",queryPageBean.getStatus());
        List<Account> byPage = accountDao.findAccountByPage(params);
        PageInfo pageInfo=new PageInfo(byPage);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(Account account) {
        accountDao.add(account);
    }

    @Override
    public void deleteById(Integer id) {
        accountDao.deleteById(id);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }
}
