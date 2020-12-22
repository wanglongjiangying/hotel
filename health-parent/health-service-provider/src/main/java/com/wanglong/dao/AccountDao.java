package com.wanglong.dao;

import com.wanglong.pojo.Account;
import com.wanglong.pojo.Floor;

import java.util.List;
import java.util.Map;

/**
 * @author wanglong
 * @date 2020/12/20 15:25
 * @desc
 */
public interface AccountDao {

    void add(Account account);

    void deleteById(Integer id);

    void updateAccount(Account account);

    List<Account> findAccountByPage(Map<String, Object> params);

    Account findById (Integer account_id);


}
