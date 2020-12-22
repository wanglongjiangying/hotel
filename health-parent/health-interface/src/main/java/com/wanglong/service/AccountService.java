package com.wanglong.service;

import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Account;

/**
 * @author wanglong
 * @date 2020/12/20 15:24
 * @desc
 */
public interface AccountService {
    /**
     *
     * @param queryPageBean
     * @return
     */
    PageResult findAccountByPage(QueryPageBean queryPageBean);


    /**
     *
     * @param account
     */
    void add(Account account);


    /**
     *
     * @param id
     */
    void deleteById(Integer id);


    /**
     *
     * @param account
     */
    void updateAccount(Account account);
}
