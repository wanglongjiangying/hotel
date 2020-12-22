package com.wanglong.controller;

import com.wanglong.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/getUsername")
    public Result getUsername(){

        try {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true,"",user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }


    }
}
