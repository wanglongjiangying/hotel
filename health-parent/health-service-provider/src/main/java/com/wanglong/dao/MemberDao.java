package com.wanglong.dao;

import com.wanglong.pojo.Member;

public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void add(Member member);

    Integer todayNewMember(String today);

    Integer totalMember(String today);

    Integer thisWeekNewMember(String zhouyi);

    Integer thisMonthNewMember(String yueFirstDay);
}
