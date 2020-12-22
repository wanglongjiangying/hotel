package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanglong.dao.MemberDao;
import com.wanglong.pojo.Member;
import com.wanglong.service.MemberService;
import com.wanglong.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberDao memberDao;


    @Override
    public Member findMemberByTelephone(String telephone) throws Exception{
        return   memberDao.findMemberByTelephone(telephone);
    }

    /**
     * 为密码进行简单的加密
     * @param member
     */
    @Override
    public void add(Member member) {

        //只是通过md5加密并没有加盐
        if(member.getPassword()!=null){
            member.setPassword( MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }
}
