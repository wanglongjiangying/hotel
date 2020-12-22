package com.wanglong.service;

import com.wanglong.pojo.Member;

public interface MemberService {
    Member findMemberByTelephone(String telephone) throws Exception;

    void add(Member member);
}
