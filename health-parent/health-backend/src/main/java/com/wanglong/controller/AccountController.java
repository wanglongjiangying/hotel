package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.entity.Result;
import com.wanglong.pojo.Account;
import com.wanglong.service.AccountService;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglong
 * @date 2020/12/20 15:21
 * @desc
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Reference
    private AccountService accountService;

    @PostMapping("/findAccountByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult byPage = accountService.findAccountByPage(queryPageBean);
            return byPage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Account account){
        try {
            accountService.add(account);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ACCOUNT_FAIL,null);
        }
        return new Result(true, MessageConstant.ADD_ACCOUNT_SUCCESS,null);
    }

    /**
     * 这个删除是一个逻辑删除，只是把他的状态
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            accountService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ACCOUNT_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ACCOUNT_FAIL,null);
        }
    }

    @PostMapping("/updateAccount")
    public Result updateAccount(@RequestBody Account account){
        try {
            accountService.updateAccount(account);
            return new Result(true,MessageConstant.EDIT_ACCOUNT_SUCCESS,null);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_ACCOUNT_FAIL,null);
        }
    }


}
